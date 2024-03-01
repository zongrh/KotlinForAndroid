package cn.my.mvvm.http

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import cn.my.mvvm.R
import cn.my.mvvm.data.bean.ApiResponse
import cn.my.mvvm.data.bean.Banner
import cn.my.mvvm.utils.AppUtils
import cn.my.mvvm.utils.LogUtil
import cn.my.mvvm.utils.showToast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart

/**
 * @author : LeeZhaoXing
 * @date   : 2021/4/14
 * @desc   : 网络相关
 */
const val CODE_INVALID = 0


/**
 * 简化获取flow获取liveData的流程
 */
inline fun <T> fetchData(
    flow: Flow<ApiResponse<T>>,
    crossinline block: (T) -> Unit,
): LiveData<T?> = liveData {
    flow.catch {
        uploadExceptionLog(it)
        R.string.network_error.showToast()
    }.collectLatest {
        if (it.errorCode== CODE_INVALID) {
            block(it.data)
            emit(it.data)
        } else {
            if (it.errorCode != CODE_INVALID) {
                it.errorMsg?.showToast()
            }
        }
    }
}



fun uploadExceptionLog(throwable: Throwable) {
    val msg = AppUtils.getExceptionMsg(throwable)
    if (msg.contains("BusinessLogUtil")) {
        return
    }
    LogUtil.e("uploadExceptionLog: "+msg)
}

