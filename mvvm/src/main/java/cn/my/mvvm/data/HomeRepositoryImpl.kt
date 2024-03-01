package cn.my.mvvm.data

import cn.my.mvvm.data.bean.ApiResponse
import cn.my.mvvm.data.bean.Banner
import cn.my.mvvm.http.Api
import com.btpj.lib_base.http.RetrofitManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 *
 * FileName: HomeRepositoryIml
 * Author: nanzong
 * Date: 2023/3/23 03:46
 * Description:
 * History:
 *
 */
class HomeRepositoryImpl:IHomeRepository {
    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override fun getBanners(): Flow<ApiResponse<List<Banner>?>?> {
        return flow {
            service.getBanner()
        }
    }

}