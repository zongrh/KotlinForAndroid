package cn.my.mylibrary.base

import androidx.lifecycle.ViewModel

/**
 *
 * FileName: BaseViewModel
 * Author: nanzong
 * Date: 2022/8/25 2:39 上午
 * Description: ViewModel基类
 * History:
 *
 */
abstract class BaseViewModel : ViewModel() {
    /** 界面启动时要进行的初始化逻辑，如网络请求,数据初始化等 */
    abstract fun start()
}