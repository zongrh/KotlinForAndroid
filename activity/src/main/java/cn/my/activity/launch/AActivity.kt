package cn.my.activity.launch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.my.activity.R
import cn.my.activity.databinding.ActivityABinding
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.LogUtil

class AActivity : BaseBindingActivity<ActivityABinding>(R.layout.activity_a) {
    companion object {
        /**
         * 启动Activity
         *
         * @param context Context
         */
        fun launch(context: Context) {
            context.startActivity(Intent(context, AActivity::class.java))
        }

    }

    override fun setupViews() {
        mBinding.btnLaunch2ActivityB.setOnClickListener {
            BActivity.launch(this)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d("A onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d("A onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("A onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d("A onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d("A onStop")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d("A onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("A onDestroy")
    }

}