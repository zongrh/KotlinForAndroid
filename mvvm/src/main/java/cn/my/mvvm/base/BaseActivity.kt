package com.zeekrlife.snc.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {

    /**
     * 视图与模型数据
     */
    lateinit var mViewModel: T
    var mPath: String? = null

    val TAG: String = this.javaClass.simpleName

    abstract fun getLayoutView(): View?
    abstract fun initViewModel(): T
    abstract fun initView()
    abstract fun bindData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())
        mViewModel = initViewModel()
        initData()
        initView()
        bindData()
    }

    private fun initData() {
        mPath = intent.getStringExtra("path")
    }

}