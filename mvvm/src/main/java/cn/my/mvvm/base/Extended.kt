package com.zeekrlife.snc.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 独立作用域
 */
inline fun <reified T : ViewModel> Fragment.getSelfVm(): T {
    return ViewModelProvider(this).get(T::class.java)
}


inline fun <reified T : ViewModel> FragmentActivity.getVm(): T {
    return ViewModelProvider(this).get(T::class.java)
}

/**
 * 共享Activity
 */
inline fun <reified T : ViewModel> Fragment.getVm(): T {
    return ViewModelProvider(this.activity as FragmentActivity).get(T::class.java)
}