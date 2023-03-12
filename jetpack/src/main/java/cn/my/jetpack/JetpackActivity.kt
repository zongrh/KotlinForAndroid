package cn.my.jetpack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import cn.my.mylibrary.base.BaseActivity

/**
 * jatpack、kotlin相关
 */
class JetpackActivity : BaseActivity(R.layout.activity_jetpack) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JetpackActivity::class.java)
        }
    }

    override fun setupViews() {

        findViewById<Button>(R.id.btn_coroutines).setOnClickListener {
            startActivity(CoroutinesActivity.newIntent(this))
        }

        findViewById<Button>(R.id.btn_jetpack_lifecycle).setOnClickListener {
            startActivity(JetpackLifecycleActivity.newIntent(this))
        }

    }
}