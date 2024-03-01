package cn.my.activity.orientation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.TextView
import cn.my.activity.R
import cn.my.mylibrary.base.BaseActivity
import cn.my.mylibrary.utils.LogUtil
import cn.my.mylibrary.widgets.TitleLayout

/**
 * Manifests中设Activity置android:configChanges="orientation"查看生命周期的变化
 *    网上答案千奇百怪，亲测Android11是以下结果：
 * 1、不设置Activity的android:configChanges时或者设置android:configChanges="orientation"
 *    或android:configChanges=orientation|keyboardHidden（**Android4.0以后无效**）都跟不设置一样，
 *    切屏都会重新重新创建Activity进而重新回调各个生命周期
 * 2、设置Activity的android:configChanges="orientation|screenSize"时，切屏不会重新调用各个生命周期，
 *    只会执行onConfigurationChanged方法
 *
 */
class OrientationActivity : BaseActivity(R.layout.activity_orientation) {
    companion object {
        // 判断是否作为 Application 的首页
        private var isIndexPage = true

        /**
         * 启动activity
         * @param context Context
         */
        fun launch(context: Context) {
            isIndexPage = false
            context.startActivity(Intent(context, OrientationActivity::class.java))
        }
    }

    override fun setupViews() {
        findViewById<TitleLayout>(R.id.titleLayout).setBackVisible(isVisible = true)
        findViewById<TitleLayout>(R.id.titleLayout).setTitleText("我是横竖屏切换的标题")
        LogUtil.d("onCreate")
        findViewById<TextView>(R.id.tv_helloworld).text = "德玛西亚万岁"

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        LogUtil.d("onConfigurationChanged")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("onDestroy")
    }


}