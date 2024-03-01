// ICallback.aidl
package cn.zong.android_ipc_server;

// Declare any non-default types here with import statements

interface ICallback {
   /**
    * 服务端调用客户端的回调
    **/
   void onReceived(String msg);
}