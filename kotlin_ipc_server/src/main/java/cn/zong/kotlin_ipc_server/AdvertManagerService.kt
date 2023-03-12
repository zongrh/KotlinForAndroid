package cn.zong.kotlin_ipc_server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

/**
 *
 * FileName: AdvertManagerService
 * Author: nanzong
 * Date: 2023/3/6 14:57
 * Description:
 * History:
 *
 */
class AdvertManagerService() : Service() {
    private var mAdvertList = mutableListOf<Advert>()

    val mIAdvertManager = object : IAdvertManager.Stub() {
        override fun getAdvertList(): MutableList<Advert> {
            return mAdvertList
        }

        override fun addAdvert(ad: Advert) {
            mAdvertList.add(ad)
        }

        override fun removeFristAdvert() {
            if (mAdvertList.size == 0) {
                Toast.makeText(this@AdvertManagerService, "岗位列表已经为null了", Toast.LENGTH_SHORT).show()
            } else {
                mAdvertList.removeAt(0)
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder {
        return mIAdvertManager
    }

    override fun onCreate() {
        super.onCreate()
        mAdvertList.add(Advert("Android开发", 10, "app开发"))
        mAdvertList.add(Advert("IOS开发", 3, "app开发"))
    }

}