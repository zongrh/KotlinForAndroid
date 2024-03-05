package cn.zong.android_ipc_server

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_show = findViewById<Button>(R.id.btn_show_server)
        val tv_return = findViewById<TextView>(R.id.tv_return)
        btn_show.setOnClickListener {
            val running = isRunning(this, "cn.zong.android_ipc_server.AIDLService")
            tv_return.text = "AIDLService 是否存活：$running"
            Log.e("MyAIDLService", "AIDLService 是否存活：$running")
        }
    }


    fun isRunning(c: Context, serviceName: String): Boolean {
        val myAM: ActivityManager = c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices: ArrayList<ActivityManager.RunningServiceInfo> = myAM.getRunningServices(40) as ArrayList<ActivityManager.RunningServiceInfo>
        //获取最多40个当前正在运行的服务，放进ArrList里,以现在手机的处理能力，要是超过40个服务，估计已经卡死，所以不用考虑超过40个该怎么办
        for (i in 0 until runningServices.size)  //循环枚举对比
        {
            if (runningServices[i].service.getClassName().toString().equals(serviceName)) {
                return true
            }
        }
        return false
    }
}