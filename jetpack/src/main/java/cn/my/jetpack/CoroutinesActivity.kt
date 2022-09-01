package cn.my.jetpack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import cn.my.jetpack.databinding.ActivityCoroutinesBinding
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Kotlin协程的取消
 */
class CoroutinesActivity :
    BaseBindingActivity<ActivityCoroutinesBinding>(R.layout.activity_coroutines) {

    private lateinit var mJob: Job

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoroutinesActivity::class.java)
        }
    }

    override fun setupViews() {
        mBinding.apply {
            btnStart.setOnClickListener {
                mJob = lifecycleScope.launch(Dispatchers.Main) {
                    LogUtil.d("AAAAAAAA")
                    repeat(100) {
                        LogUtil.d("第${it + 1}次运行协程")
                        delay(1000)
                    }
                }
                LogUtil.d("BBBBBBBBBB")
            }
            btnCancel.setOnClickListener {
                mJob.cancel()
            }
        }

    }

}