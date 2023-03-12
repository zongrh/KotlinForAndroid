package cn.my.mylibrary.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.my.mylibrary.utils.StatusBarUtil

/**
 *
 * FileName: BaseViewModel
 * Author: nanzong
 * Date: 2022/8/25 2:39 上午
 * Description: 集成DataBinding的Activity基类
 * History:
 *
 */
abstract class BaseBindingActivity<B : ViewDataBinding>(private val contentViewResId: Int) :
    AppCompatActivity() {

    /** DataBinding基类 */
    protected lateinit var mBinding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewResId)
        StatusBarUtil.setImmersionStatus(this)
        setupDataBinding()
        initData()
        setupViews()
    }

    /**
     * 初始化数据相关
     */
    abstract fun initData()

    /** DataBinding相关初始化设置 */
    private fun setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.lifecycleOwner = this
    }

    /** Views相关初始化设置 */
    abstract fun setupViews()
}