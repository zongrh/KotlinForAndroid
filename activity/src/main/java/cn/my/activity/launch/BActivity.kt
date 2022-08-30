package cn.my.activity.launch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.my.activity.R
import cn.my.activity.databinding.ActivityBBinding
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.LogUtil

class BActivity : BaseBindingActivity<ActivityBBinding>(R.layout.activity_b) {
    companion object {
        /**
         * 启动Activity
         *
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, BActivity::class.java))
        }
    }

    override fun setupViews() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d("B onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d("B onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("B onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d("B onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d("B onStop")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d("B onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("B onDestroy")
    }
}