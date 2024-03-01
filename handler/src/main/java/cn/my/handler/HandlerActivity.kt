package cn.my.handler

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import cn.my.mylibrary.base.BaseActivity
import cn.my.mylibrary.utils.LogUtil
import cn.my.mylibrary.widgets.TitleLayout
import java.lang.ref.WeakReference

class HandlerActivity : BaseActivity(R.layout.activity_handler) {
    //非空属性在定义的时候初始化，kotlin提供了一种可以延迟初始化的方案，
    //使用 lateinit 关键字描述属性：
    private lateinit var myLooper: Looper
    private lateinit var mHandler: Handler

    //-----伴生对象
    //类内部的对象声明可以用 companion 关键字标记，这样它就与外部类关联在一起，
    //我们就可以直接通过外部类访问到对象的内部元素。
    companion object {
        // 判断是否是作为Application的首页
        private var isIndexPage = true

        fun newIntent(context: Context): Intent {
            isIndexPage = false
            return Intent(context, HandlerActivity::class.java)
        }
    }

    override fun setupViews() {
        findViewById<TitleLayout>(R.id.titleLayout).setBackVisible(!isIndexPage)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
//            startWork()
//            MyThread().start()
            MyThread2().start()
//            SystemClock.sleep(50000)
        }

        findViewById<Button>(R.id.btn_quit).setOnClickListener {
            myLooper.quit()
        }
        findViewById<Button>(R.id.btn_new_thread_handler).setOnClickListener {
            MyThread3().start()
        }
    }

    class MyHandler(private val activityReference: WeakReference<Activity>, looper: Looper) :
        Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            LogUtil.d("当前线程：${Thread.currentThread().name}")
            // 这里的线程是否是由主线程取决于handler绑定的looper所在线程
            when (msg.what) {
                1 -> {
                    activityReference.get()?.findViewById<TextView>(R.id.tv_time)?.text =
                        msg.obj.toString()
                }
                2 -> {
                    LogUtil.d(msg.obj.toString())
                }
                // 这里由于是向子线程的looper发消息，所以当前线程是子线程无法更新UI
                3 -> LogUtil.d(msg.obj.toString())
            }
        }
    }

    private fun startWork() {
        val myHandler = MyHandler(WeakReference(this@HandlerActivity), looper = mainLooper)
        Thread {
            val message = Message.obtain().apply {
                what = 1
                obj = "主线程中的Handler向主线程中的looper(即MainLooper) 发消息"
            }
            val message2=mHandler.obtainMessage()

            myHandler.sendMessage(message)
        }.start()
    }

    inner class MyThread : Thread() {
        override fun run() {
            super.run()
            val mainLooper = Looper.getMainLooper()
            val myHandler = MyHandler(WeakReference(this@HandlerActivity), mainLooper)
            val message = myHandler.obtainMessage(2, "子线程中的handler向主线程中的Looger (即MainLooper)发消息")
            myHandler.handleMessage(message)
        }
    }

    inner class MyThread2 : Thread() {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun run() {
            super.run()
            Looper.prepare()
            //----- NULL检查机制
            //Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式：
            //1.字段后加!! 像java一样抛出空异常
            //2.字段后加? 可不做处理返回值为null 或者配合 ?: 做空判断处理
            myLooper = Looper.myLooper()!!
            myLooper.queue.addIdleHandler {
                return@addIdleHandler true
            }
            val threadHandler = MyHandler(WeakReference(this@HandlerActivity), myLooper)
            val obtainMessage = threadHandler.obtainMessage(3, "子线程中的handler向子线程的Looper发消息")
            threadHandler.sendMessage(obtainMessage)
            Looper.loop()
            LogUtil.d("MyThread2执行完毕")
        }
    }

    inner class MyThread3 : Thread() {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun run() {
            super.run()
            Looper.prepare()
            //----- NULL检查机制
            //Kotlin的空安全设计对于声明可为空的参数，在使用时要进行空判断处理，有两种处理方式：
            //1.字段后加!! 像java一样抛出空异常
            //2.字段后加? 可不做处理返回值为null 或者配合 ?: 做空判断处理
            myLooper = Looper.myLooper()!!
            myLooper.queue.addIdleHandler {
                return@addIdleHandler true
            }
            val threadHandler = MyHandler(WeakReference(this@HandlerActivity), myLooper)
            val obtainMessage = threadHandler.obtainMessage(3, "子线程中的handler向子线程的Looper发消息")
            threadHandler.sendMessage(obtainMessage)
            Looper.loop()
            LogUtil.d("MyThread2执行完毕")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 这里也可以使用removeCallbacksAndMessages取消handler对message的监听（message.recycleUnchecked）防止内存泄漏
        // mHandler.removeCallbacksAndMessages(null)
    }

}