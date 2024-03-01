package cn.zong.android_ipc_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.zong.android_ipc_server.Book;
import cn.zong.android_ipc_server.BookController;
import cn.zong.android_ipc_server.ICallback;

public class LpcActivity extends AppCompatActivity {
    private final String TAG = "MyAIDLService";

    private BookController bookController;

    private boolean connected;

    private List<Book> bookList = new ArrayList<>();

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookController = BookController.Stub.asInterface(service);
            connected = true;
            registerCallback();
            Log.e(TAG, "onServiceConnected: " + connected);
            try {
                Log.e(TAG, "getBookList.toString: " + bookController.getBookList().toString());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if (bookController != null) {
                try {
                    Toast.makeText(LpcActivity.this, "链接成功", Toast.LENGTH_SHORT).show();
                    bookController.asBinder().linkToDeath(mDeathRecipient, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
            Log.e(TAG, "onServiceDisconnected: " + connected);
        }

        /**
         * 给binder设置死亡代理
         */
        private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
            @Override
            public void binderDied() {
                if (bookController == null) {
                    return;
                }
                // 在创建ServiceConnection的匿名类中的onServiceConnected方法中
                // 设置死亡代理
                bookController.asBinder().unlinkToDeath(mDeathRecipient, 0);
                bookController = null;
                // 重新绑定服务
                bindService();
            }
        };
    };

    private int i = 0;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_getBookList:
                    Log.e(TAG, "btn_getBookList: " + connected);
                    if (connected) {
                        try {
                            bookList = bookController.getBookList();
                            String books = "";
                            for (int j = 0; j < bookList.size(); j++) {
                                books = books + bookList.get(j) + "\n";
                            }
                            mTv_show_return.setText("我是展示数据：" + "\n" + books);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    logBookString();
                    break;
                case R.id.btn_addBook:
                    i++;
                    Log.e(TAG, "btn_addBook: " + connected);
                    if (connected) {
                        Book book = new Book("这是一本新书 btn_addBook  " + i);
                        try {
                            bookController.addBookInOut(book);

                            bookList = bookController.getBookList();
                            String books = "";
                            for (int j = 0; j < bookList.size(); j++) {
                                books = books + bookList.get(j) + "\n";
                            }
                            mTv_show_return.setText("我是展示数据：" + "\n" + books);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case R.id.btn_removeFirstBook:
                    Log.e(TAG, "btn_removeFirstBook: " + connected);

                    if (connected) {
                        try {
                            bookController.removeFirstBook();

                            bookList = bookController.getBookList();
                            String books = "";
                            for (int j = 0; j < bookList.size(); j++) {
                                books = books + bookList.get(j) + "\n";
                            }
                            mTv_show_return.setText("我是展示数据：" + "\n" + books);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case R.id.btn_download_images:
                    startActivity(new Intent(LpcActivity.this, DownloadImagesActivity.class));
                    break;
                case R.id.btn_register:
                    registerCallback();
                    break;
                case R.id.btn_unregister:
                    unregisterCallback();
                    break;
                case R.id.btn_call_server:
                    indexClientSend++;
                    callServer("我是客户端发送给服务端的第"+indexClientSend+"条消息");
                    break;
            }
        }
    };
    private int indexClientSend = 0;
    private void callServer(String msg) {
        if (bookController != null) {
            try {
                Log.e(TAG, "callServer  msg:" + msg);
                bookController.callServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.e(TAG, " null remoteServer");
        }
    }

    public void registerCallback() {
        if (bookController != null) {
            try {
                bookController.register(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, " null remoteServer");
        }
    }

    public void unregisterCallback() {
        if (bookController != null) {
            try {
                bookController.unregister(callback);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.e(TAG, " null remoteServer");
        }
    }

    private ICallback callback = new ICallback.Stub() {
        @Override
        public void onReceived(String msg) throws RemoteException {
            Log.d(TAG, "received msg: "  + " . from server pid=" + Binder.getCallingPid());
            mTv_show_return.post(new Runnable() {
                @Override
                public void run() {
                    mTv_show_return.setText("服务端回调数据： "+indexClientSend);
                }
            });
        }
    };

    private TextView mTv_show_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lpc);
        findViewById(R.id.btn_getBookList).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_addBook).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_removeFirstBook).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_download_images).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_register).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_unregister).setOnClickListener(mOnClickListener);
        mTv_show_return = findViewById(R.id.tv_return);
        bindService();
    }

    /**
     * 下载一张图片 看日志
     *
     * @param view
     */
    public void downloadImage(View view) {
        Intent intent = new Intent(LpcActivity.this, DownloadService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("cn.zong.android_ipc_server");
        intent.setAction("cn.zong.android_ipc_server.action");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(mServiceConnection);
        }
    }

    @Override
    protected void onResume() {
        if (connected) {
            registerCallback();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (connected) {
            unregisterCallback();
        }
        super.onPause();
    }

    private void logBookString() {
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                Log.e(TAG, "logBookString: " + book.toString());
            }
        } else {
            mTv_show_return.setText("没有数据，请检查服务是否开启");
        }
    }
}