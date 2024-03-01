package cn.my.kotlinforandroid

import cn.my.activity.AboutActivity
import cn.my.handler.HandlerActivity
import cn.my.jetpack.CoroutinesActivity
import cn.my.jetpack.JetpackActivity
import cn.my.kotlinforandroid.databinding.ActivityMainBinding
import cn.my.mvvm.MvvmActivity
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.LogUtil
import cn.my.mylibrary.utils.ToastUtils

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initData() {
    }

    override fun setupViews() {
        mBinding.apply {
            // Activity
            btnActivity.setOnClickListener {
                LogUtil.d("btnActivity")
                ToastUtils.showLong(this@MainActivity, "btnActivity")
                AboutActivity.launch(this@MainActivity)
            }

            // Handler
            btnHandler.setOnClickListener {
                startActivity(HandlerActivity.newIntent(this@MainActivity))
                ToastUtils.showLong(this@MainActivity, "Handler Toast")
            }

            // 事件分发
            btnEvent.setOnClickListener {
//                startActivity(MainViewsActivity.newIntent(this@MainActivity))
            }

            // MVC、MVP、MVVM
            btnMvcMvpMvvm.setOnClickListener {
                startActivity(MvvmActivity.newIntent(this@MainActivity) )

                // Arouter（此处暂时不兼容AndroidX）
                //  ARouter.getInstance().build("/MvcMvpMvvm/mvc").navigation()
            }

            // Kotlin & 协程 & jetpack
            btnKotlinJetpack.setOnClickListener {
                startActivity(JetpackActivity.newIntent(this@MainActivity))
            }

            // android coroutines
            btnCoroutines.setOnClickListener {
                startActivity(CoroutinesActivity.newIntent(this@MainActivity))
            }


            // EventBus
            btnEventBus.setOnClickListener {
//                EventBusActivity.launch(this@MainActivity)
            }
        }
    }
}