// BookController.aidl
package cn.zong.android_ipc_server;
import cn.zong.android_ipc_server.Book;
// Declare any non-default types here with import statements

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

    void removeFirstBook();
}