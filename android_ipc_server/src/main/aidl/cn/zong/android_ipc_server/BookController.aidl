// BookController.aidl
package cn.zong.android_ipc_server;
import cn.zong.android_ipc_server.Book;
import cn.zong.android_ipc_server.ICallback;
// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

    void removeFirstBook();

    // 向服务端发送消息
    void callServer();

    // 向服务端注册客户端回调
    void register(ICallback callback);

    // 向服务端注销客户端回调
    void unregister(ICallback callback);

}