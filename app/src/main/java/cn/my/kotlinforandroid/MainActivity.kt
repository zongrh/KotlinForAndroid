package cn.my.kotlinforandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.my.kotlinforandroid.databinding.ActivityMainBinding
import cn.my.mylibrary.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupViews() {
        mBinding.apply {
            // Activity
            btnActivity.setOnClickListener {
//                AboutActivity.launch(this@MainActivity)
            }

            // Handler
            btnHandler.setOnClickListener {
//                startActivity(HandlerActivity.newIntent(this@MainActivity))
            }

            // 事件分发
            btnEvent.setOnClickListener {
//                startActivity(MainViewsActivity.newIntent(this@MainActivity))
            }

            // IPC通信
            btnIpc.setOnClickListener {
//                startActivity(IPCActivity.newIntent(this@MainActivity))
            }

            // MVC、MVP、MVVM
            btnMvcMvpMvvm.setOnClickListener {
//                startActivity(StructureActivity.newIntent(this@MainActivity) )

                // Arouter（此处暂时不兼容AndroidX）
                //  ARouter.getInstance().build("/MvcMvpMvvm/mvc").navigation()
            }

            // Kotlin & 协程
            btnKotlinJetpack.setOnClickListener {
//                startActivity(JetpackActivity.newIntent(this@MainActivity))
            }

            // EventBus
            btnEventBus.setOnClickListener {
//                EventBusActivity.launch(this@MainActivity)
            }
        }
    }
}