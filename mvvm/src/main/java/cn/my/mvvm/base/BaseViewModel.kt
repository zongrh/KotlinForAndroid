package cn.my.mvvm.base

import androidx.lifecycle.*
import cn.my.mvvm.data.bean.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

abstract class BaseViewModel:ViewModel() {
    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    val errorResponse = MutableLiveData<ApiResponse<*>?>()

    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    abstract fun start()

    fun <T> asLiveDataNonCancel(flow: Flow<T>): LiveData<T> {
        return flow.asLiveData(viewModelScope.coroutineContext, Long.MAX_VALUE)
    }

}