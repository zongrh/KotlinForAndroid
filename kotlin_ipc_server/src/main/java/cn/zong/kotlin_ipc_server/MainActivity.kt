package cn.zong.kotlin_ipc_server

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_show = findViewById<Button>(R.id.btn_show_server)
        val tv_return = findViewById<TextView>(R.id.tv_return)
        btn_show.setOnClickListener {
            val running = isRunning(this, "cn.zong.kotlin_ipc_server.AdvertManagerService")

            tv_return.text = "AdvertManagerService 是否存活：$running"
            Log.e("MyAIDLService", "AdvertManagerService 是否存活：$running")

        }
        findViewById<Button>(R.id.btn_show_restart).setOnClickListener {
            restartApp(this)
        }

        runHelperService()
    }

    /**
     * 重启app
     */
    private fun restartApp(context: Context) {
        Looper.myLooper()?.let {
            Handler(it).postDelayed(
                {
                    val launchIntent =
                        context.packageManager.getLaunchIntentForPackage(context.packageName)
                    launchIntent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(launchIntent)
                    Process.killProcess(Process.myPid())
                    exitProcess(10)
                }, 1000L
            )
        }
    }
    private fun runHelperService() {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn = ComponentName("cn.zong.kotlin_ipc_server", "AdvertManagerService")
        val cnr = am.getRunningServiceControlPanel(cn)
        if (cnr == null) {
            Log.e("MyAIDLService", "AdvertManagerService 是否存活：cnr == null")
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            Log.e("MyAIDLService", "AdvertManagerService 是否存活：cnr != null")
        }
    }

    fun isRunning(c: Context, serviceName: String?): Boolean {
        val myAM: ActivityManager = c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices: ArrayList<ActivityManager.RunningServiceInfo> =
            myAM.getRunningServices(40) as ArrayList<ActivityManager.RunningServiceInfo>
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