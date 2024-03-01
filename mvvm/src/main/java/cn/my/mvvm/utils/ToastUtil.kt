package cn.my.mvvm.utils

import android.content.Context
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import cn.my.mvvm.base.BaseApp

/**
 * Toast封装工具类
 * 注：不知咋回事，设置Toast为静态LeakCanary就报内存泄漏，即使设置成context.applicationContext
 *
 * @author LTP  2018/3/26
 */
object ToastUtil {

    /**
     * 显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShort(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示长时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLong(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShortInCenter(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLongInCenter(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}


private var toast: Toast? = null


/**
 * @sample "用户名或密码不完整".showToast()
 */
fun String?.showToast(duration: Int = Toast.LENGTH_SHORT) {
    this?.let {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            initToastWithString(it, duration)
        }
    }
}

/**
 * @sample "用户名或密码不完整".showToast()
 */
fun CharSequence?.showToast(duration: Int = Toast.LENGTH_SHORT) {
    this?.let {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            initToastWithString(it, duration)
        }
    }
}

private fun initToastWithString(content: CharSequence?, duration: Int) {
    content?.let {
        if (toast == null) {
            toast = Toast.makeText(BaseApp.appContext, it, duration)
        }
        toast?.setText(it)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.show()
    }
}

private fun initToastWithInt(content: Int?, duration: Int) {
    content?.let {
        if (toast == null) {
            toast = Toast.makeText(BaseApp.appContext, it, duration)
        }
        toast?.setText(it)
        toast?.setGravity(Gravity.CENTER, 0, 0)
        toast?.show()
    }
}

fun Int?.showToast(duration: Int = Toast.LENGTH_SHORT) {
    this?.let {
        if (this == 0) {
            return@let
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            initToastWithInt(this, duration)
        }
    }
}
