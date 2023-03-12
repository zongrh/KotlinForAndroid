package cn.my.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.my.activity.databinding.ActivityAboutBinding
import cn.my.activity.launch.AActivity
import cn.my.activity.orientation.OrientationActivity
import cn.my.mylibrary.base.BaseBindingActivity
import cn.my.mylibrary.utils.ToastUtils

class AboutActivity : BaseBindingActivity<ActivityAboutBinding>(R.layout.activity_about) {
    companion object{
        /**
         * 启动Activity
         * @param context Context
         */
        fun launch(context: Context){
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override fun initData() {

    }

    override fun setupViews() {
        mBinding.apply {
            btnOrientation.setOnClickListener {
                ToastUtils.showLong(this@AboutActivity, "启动Activity")
                OrientationActivity.launch(this@AboutActivity)

            }
            btnLaunch.setOnClickListener {
                ToastUtils.showLong(this@AboutActivity, "启动Activity")
                AActivity.launch(this@AboutActivity)
            }
        }
    }


}