package com.zeekrlife.snc.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<T : ViewModel>:Fragment() {

    val TAG: String = this.javaClass.simpleName

    /**
     * 视图与模型数据
     */
    lateinit var mViewModel: T
    abstract fun initViewModel(): T
    abstract fun getLayoutView(): View?
    abstract fun initView()
    abstract fun initData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getLayoutView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = initViewModel()
        initView()
        initData()
    }
}