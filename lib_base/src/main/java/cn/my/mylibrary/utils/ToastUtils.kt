package cn.my.mylibrary.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 *
 * FileName: ToastUtils
 * Author: nanzong
 * Date: 2022/8/30 9:00 下午
 * Description: Toast封装工具类
 * History:
 *
 */
object ToastUtils {
    /**
     * 显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShort(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示长时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLong(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShortInCenter(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).apply {
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
        Toast.makeText(context, msg, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }


}