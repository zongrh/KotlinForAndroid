package cn.my.mylibrary.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.my.mylibrary.utils.StatusBarUtil

/**
 *
 * FileName: BaseActivity
 * Author: nanzong
 * Date: 2022/8/25 2:04 上午
 * Description: 普通的Actvity基类
 * History:
 *
 */
abstract class BaseActivity(private val contentViewResId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewResId)
        StatusBarUtil.setImmersionStatus(this)
        setupViews()
    }

    /**
     * Views相关初始化设置
     */
    abstract fun setupViews()
}
