package cn.my.mvvm.ui

import androidx.lifecycle.MutableLiveData
import cn.my.mvvm.data.bean.CollectData
import cn.my.mvvm.data.bean.User
import cn.my.mvvm.base.BaseViewModel

/**
 * App全局ViewModel可直接替代EventBus
 *
 * @author LTP  2022/4/13
 */
class AppViewModel : BaseViewModel() {
    override fun start() {}

    /** 全局用户 */
    val userEvent = MutableLiveData<User?>()

    /** 分享添加文章 */
    val shareArticleEvent = MutableLiveData<Boolean>()

    /** 文章收藏 */
    val collectEvent = MutableLiveData<CollectData>()
}