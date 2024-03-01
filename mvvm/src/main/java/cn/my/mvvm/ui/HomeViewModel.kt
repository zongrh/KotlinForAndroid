package cn.my.mvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.my.mvvm.base.BaseViewModel
import cn.my.mvvm.data.DataRepository
import cn.my.mvvm.data.HomeRepositoryImpl
import cn.my.mvvm.data.IHomeRepository
import cn.my.mvvm.data.bean.Article
import cn.my.mvvm.data.bean.Banner
import cn.my.mvvm.ext.handleRequest
import cn.my.mvvm.ext.launch
import cn.my.mvvm.http.fetchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * FileName: HomeViewModel
 * Author: nanzong
 * Date: 2023/3/23 01:45
 * Description:
 * History:
 *
 */
class HomeViewModel : BaseViewModel() {

    override fun start() {

    }

    val bannerListLiveData = MutableLiveData<List<Banner>?>()


    fun fetchBanners() {
        // ViewModel 被清除时 viewModelScope 会被取消。**
        viewModelScope.launch(Dispatchers.IO) {

        }
        launch({
            handleRequest(DataRepository.getBanner(), {
                bannerListLiveData.value = it.data
            })
        })
    }

    val articleTopList = MutableLiveData<List<Article>?>()
    fun getArticleTopList() {
        launch({
            handleRequest(DataRepository.getArticleTopList(), {
                articleTopList.value = it.data
            })
        })
    }

//    val repository:IHomeRepository by lazy { HomeRepositoryImpl() }

//    fun getBanners2() = fetchData(repository.getBanners()) {}


}