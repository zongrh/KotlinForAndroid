package cn.my.mylibrary.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType
import cn.my.mylibrary.BR

/**
 *
 * FileName: BaseVMBActivity
 * Author: nanzong
 * Date: 2022/8/25 2:47 上午
 * Description:集成ViewModel和DataBinding的基类Activity
 * History:
 *
 */
abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewDataBinding>(private val contentViewResId: Int) :
    BaseActivity(contentViewResId) {

    /** viewModel基类 */
    protected lateinit var mViewModel: VM

    /** DataBinding基类 */
    protected lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupDataBinding()
        setupVMBViews()
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupViewModel() {
        // 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
        createObserve()
    }

    /** DataBinding相关初始化设置 */
    private fun setupDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, contentViewResId)
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.viewModel, mViewModel)
    }

    /** 提供编写LiveData监听逻辑的方法 */
    open fun createObserve() {}

    /** 这里直接重写掉父类的，用这个方法会导致setupViews会在mViewModel初始化前执行而报错
     * 继承BaseVMActivity请调用setupVMViews */
    override fun setupViews() {}

    /** 重新提供一个初始化View的方法 */
    abstract fun setupVMBViews()
}