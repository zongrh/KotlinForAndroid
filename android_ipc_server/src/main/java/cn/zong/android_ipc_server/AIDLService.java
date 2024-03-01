package cn.zong.android_ipc_server;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteCallbackList;
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

    public List<Book> bookList = new ArrayList<>();

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public AIDLService() {
    }

    private boolean serverRunning = false;
    private int count = 0;

    @Override
    public void onCreate() {
        // service 创建的时候，开一个线程去向注册了的客户端发送消息
        serverRunning = true;
        Log.e(TAG, "AIDLService  onCreate ");
        super.onCreate();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "callback test server destroy");
        serverRunning = false;
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
            Log.e(TAG, "接收到了一个book : " + book.toString());

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

        @Override
        public void register(ICallback callback) throws RemoteException {
            Log.d(TAG, "register callback from pid=" + Binder.getCallingPid());
            clients.register(callback);
        }

        @Override
        public void unregister(ICallback callback) throws RemoteException {
            Log.d(TAG, "unregister callback from pid=" + Binder.getCallingPid());
            clients.unregister(callback);
        }

        @Override
        public void callServer() throws RemoteException {
            Log.d(TAG, "我是服务端，我收到客户端的消息是:  " + "我服务端所在的进程是：  pid=" + Binder.getCallingPid());
              noteClients("我是服务端 ");
        }

        /**
         * 这里就是 RemoteCallbackList 的关键用法了
         * beginBroadcast 和 finishBroadcast 需要配套使用
         * beginBroadcast 会返回注册了的客户端数量，然后开一个循环依次取出客户端注册的回调，并调用回调内的
         * onReceived 函数。这个函数需要客户端实现 ICallbackTestCallback.Stub 之后，注册给服务端
         */
        private void noteClients(String msg) {
            int cb = clients.beginBroadcast();
            for (int i = 0; i < cb; i++) {
                try {
                    clients.getBroadcastItem(i).onReceived(msg);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            clients.finishBroadcast();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final RemoteCallbackList<ICallback> clients = new RemoteCallbackList<>();


}