package cn.zong.android_ipc_server;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AIDLService
 * Author: nanzong
 * Date: 2023/3/6 04:19
 * Description:
 * History:
 */
public class AIDLService extends Service {

    private final String TAG = "MyAIDLService";

    private List<Book> bookList;

    public AIDLService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();
        Log.e(TAG, "AIDLService  onCreate ");
    }

    private void initData() {
        Book book1 = new Book("世界因你而不同");
        Book book2 = new Book("android开发");
        Book book3 = new Book("临江仙");
        Book book4 = new Book("https://github.com");
        Book book5 = new Book("http://www.jianshu.com");
        Book book6 = new Book("http://blog.csdn.net");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
        Log.e(TAG, "bookList.size:" + bookList.size());
        Log.e(TAG, "bookList.toString:  " + bookList.toString());
    }

    private final IBinder binder = new BookController.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            Log.e(TAG, "接收到了一个book : "+book.toString());

            if (book != null) {
                bookList.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 InOut");
            }
        }

        @Override
        public void removeFirstBook() throws RemoteException {
            if (bookList.size() == 0) {
                Toast.makeText(AIDLService.this, "书籍列表已经为null了", Toast.LENGTH_SHORT).show();
            } else {
                bookList.remove(0);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}