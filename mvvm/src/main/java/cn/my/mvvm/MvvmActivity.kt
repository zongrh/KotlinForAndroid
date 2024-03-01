package cn.my.mvvm

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.lifecycleScope
import cn.my.mvvm.databinding.ActivityMvvmBinding
import cn.my.mvvm.ui.HomeViewModel
import cn.my.mvvm.utils.LogUtil
import cn.my.mvvm.view.SoundWaveView.OnEvent
import com.zeekrlife.snc.base.BaseActivity
import com.zeekrlife.snc.base.getVm
import kotlinx.coroutines.*


class MvvmActivity : BaseActivity<HomeViewModel>() {
    private val mBinding: ActivityMvvmBinding by lazy {
        ActivityMvvmBinding.inflate(LayoutInflater.from(this))
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MvvmActivity::class.java)
        }
    }

    override fun getLayoutView(): View? {
        return mBinding.root
    }

    override fun initViewModel(): HomeViewModel {
        return getVm()
    }

    override fun initView() {
        // lifecycleScope只能在Activity、Fragment中使用，会绑定Activity和Fragment的生命周期
        lifecycleScope.launch(Dispatchers.IO) {

        }
        //在UI线程中启动协程
        MainScope().launch(Dispatchers.IO) {

        }
        // GlobalScope是生命周期是process级别的，即使Activity或Fragment已经被销毁，
        // 协程仍然在执行。所以需要绑定生命周期。
        GlobalScope.launch(Dispatchers.Main) {

        }
        // 调试阶段使用的协程
        runBlocking {

        }

    }

    override fun bindData() {


        mViewModel.fetchBanners()
        mViewModel.bannerListLiveData.observe(this) {
//            mBinding.
            if (it != null) {
                for (value in it) {
                    LogUtil.e("我是for循环的数据: $value  '\\ n' ")
                }
            }
            mBinding.btnGetCode.text = "我是返回数据：" + it.toString()
        }
        mViewModel.apply {
            fetchBanners()
            getArticleTopList()
        }

        mViewModel.articleTopList.observe(this) {
            if (it != null) {
                for (value in it) {
                    LogUtil.e("我是返回数据 ${it?.size}：" + it.toString() + "\n")
                }
            }
//            mBinding.btnGetCode.text="我是返回数据 ${it?.size}："+it.toString()+"\n"
        }
    }

}