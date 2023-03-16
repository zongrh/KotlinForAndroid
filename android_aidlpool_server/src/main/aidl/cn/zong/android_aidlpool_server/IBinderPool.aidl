// IBinderPool.aidl
package cn.zong.android_aidlpool_server;

// Declare any non-default types here with import statements

interface IBinderPool {
   IBinder queryBinder(int binderCode);
}