package cn.my.mvvm.data

import cn.my.mvvm.data.bean.ApiResponse
import cn.my.mvvm.data.bean.Article
import cn.my.mvvm.data.bean.Banner
import cn.my.mvvm.data.bean.PageResponse
import cn.my.mvvm.http.Api
import com.btpj.lib_base.http.BaseRepository
import com.btpj.lib_base.http.RetrofitManager
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 数据仓库
 *
 * @author LTP  2022/3/23
 */
object DataRepository : BaseRepository(), Api {

    private val service by lazy { RetrofitManager.getService(Api::class.java) }

    override suspend fun getBanner(): ApiResponse<List<Banner>> {
        return apiCall { service.getBanner() }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return apiCall { service.getArticleTopList() }
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall {
            service.getArticlePageList(pageNo,pageSize)
        }
    }

}