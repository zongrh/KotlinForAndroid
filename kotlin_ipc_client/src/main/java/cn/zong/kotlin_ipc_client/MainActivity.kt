package cn.zong.kotlin_ipc_client

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.RemoteException
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.zong.kotlin_ipc_server.Advert
import cn.zong.kotlin_ipc_server.IAdvertManager

class MainActivity : AppCompatActivity() {


    private val TAG = "MyAIDLService"

    private var mIAdvertManager: IAdvertManager? = null

    private var connected = false
    private var mIAdvertList = mutableListOf<Advert>()
    private var mtv_return: TextView? = null
    private var mbt_intentService: Button? = null
    private var iv_image: ImageView? = null
    private var i = 0


    //创建一个ServiceConnection回调，通过IBinder进行交互
    private var mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            connected = true
            mIAdvertManager = IAdvertManager.Stub.asInterface(iBinder)

            try {
                mIAdvertManager?.asBinder()?.linkToDeath(mDeathRecipient, 0)
            } catch (e: RemoteException) {
                throw RuntimeException(e)
            }

            Log.e(TAG, "onServiceConnected: $connected")
            if (mIAdvertManager != null) {
                Toast.makeText(this@MainActivity, "与服务端链接成功", Toast.LENGTH_LONG).show()
                Log.e(TAG, "getBookList.toString: " + mIAdvertManager!!.advertList.toString())

            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            connected = false
            Log.e(TAG, "onServiceDisconnected: $connected")
        }

        /**
         * 给binder设置死亡代理
         */
        private var mDeathRecipient: IBinder.DeathRecipient = object : IBinder.DeathRecipient {
            override fun binderDied() {
                if (mIAdvertManager == null) {
                    return
                }
                // 在创建ServiceConnection的匿名类中的onServiceConnected方法中
                // 设置死亡代理
                mIAdvertManager!!.asBinder().unlinkToDeath(this, 0)
                mIAdvertManager = null
                // 重新绑定服务
                bindService()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindService()
        initView()
    }


    private fun initView() {
        mtv_return = findViewById(R.id.tv_return)
        mbt_intentService = findViewById(R.id.btn_getIntentService)
        iv_image = findViewById(R.id.iv_image)
        findViewById<Button>(R.id.btn_getAdvertList).setOnClickListener {

            Log.e(TAG, "btn_getAdvertList: $connected")
            if (connected) {
                try {
                    mIAdvertList = mIAdvertManager?.advertList as MutableList<Advert>
                    var advert = ""
                    for (j in mIAdvertList.indices) {
                        advert = advert + mIAdvertList.get(j) + "\n"
                    }
                    mtv_return?.text = "我是展示数据：\n $advert"
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else {
                mtv_return?.setText("没有数据，请检查服务是否开启")
                Toast.makeText(this, "没有数据，请检查服务是否开启", Toast.LENGTH_LONG).show()
                bindService()
            }
            logAdvertString()
        }

        findViewById<Button>(R.id.btn_addAdvert).setOnClickListener {
            Log.e(TAG, "btn_getAdvertList: $connected")
            if (connected) {
                try {
                    i++
                    val advert = Advert("flutter $i", 22, "跨平台开发岗位 $i")
                    mIAdvertManager?.addAdvert(advert)

                    mIAdvertList = mIAdvertManager?.advertList as MutableList<Advert>
                    var adverts = ""
                    for (j in mIAdvertList.indices) {
                        adverts = adverts + mIAdvertList.get(j) + "\n"
                    }
                    mtv_return?.text = "我是展示数据：\n $adverts"
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else {
                mtv_return?.setText("没有数据，请检查服务是否开启")
                Toast.makeText(this, "没有数据，请检查服务是否开启", Toast.LENGTH_LONG).show()
                bindService()
            }
        }

        findViewById<Button>(R.id.btn_removeFirstAdvert).setOnClickListener {
            Log.e(TAG, "btn_getAdvertList: $connected")
            if (connected) {
                try {
                    mIAdvertManager?.removeFristAdvert()

                    mIAdvertList = mIAdvertManager?.advertList as MutableList<Advert>
                    var advert = ""
                    for (j in mIAdvertList.indices) {
                        advert = advert + mIAdvertList.get(j) + "\n"
                    }
                    mtv_return?.text = "我是展示数据：\n $advert"
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else {
                mtv_return?.setText("没有数据，请检查服务是否开启")
                Toast.makeText(this, "没有数据，请检查服务是否开启", Toast.LENGTH_LONG).show()
                bindService()
            }
        }

        findViewById<Button>(R.id.btn_getIntentService).setOnClickListener {

        }
    }

    private fun bindService() {
        val intent = Intent()
        intent.setPackage("cn.zong.kotlin_ipc_server")
        intent.action = "cn.zong.kotlin_ipc_server.action"
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (connected) {
            unbindService(mServiceConnection)
        }
    }

    private fun logAdvertString() {
        if (!mIAdvertList.isEmpty()) {
            for (book in mIAdvertList) {
                Log.e(TAG, "logBookString: " + book.toString())
            }
        } else {
            mtv_return?.setText("没有数据，请检查服务是否开启")
        }
    }

}