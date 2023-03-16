package cn.zong.android_ipc_client;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * FileName: MyIntentService
 * Author: nanzong
 * Date: 2023/3/12 23:10
 * Description:
 * History:
 */
public class ImagesIntentService extends IntentService {
    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";
    public static UpdateUI updateUI;
    final String TAG = "MyIntentServiceTAG";

    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI=updateUIInterface;
    }

    /**
     * @deprecated
     *
     *在构造函数中传入线程名字
     *
     */
    public ImagesIntentService(){
        super("MyIntentService");
    }

    /**
     * 实现异步任务的方法
     * @param intent Activity传递过来的Intent,数据封装在intent中
     *
     *  复写onHandleIntent()方法
     *  根据 Intent实现 耗时任务 操作
     *
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        //在子线程中进行网络请求
        Bitmap bitmap=downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message msg1 = new Message();
        msg1.what = intent.getIntExtra(INDEX_FLAG,0);
        msg1.obj =bitmap;
        //通知主线程去更新UI
        if(updateUI!=null){
            updateUI.updateUI(msg1);
        }
        //mUIHandler.sendMessageDelayed(msg1,1000);

        Log.e(TAG,"onHandleIntent");
    }
    //----------------------重写一下方法仅为测试------------------------------------------
    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG,"onStart");
    }
    /**
     * 复写onStartCommand()方法
     * 默认实现 = 将请求的Intent添加到工作队列里
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return super.onBind(intent);
    }

    public interface UpdateUI{
        void updateUI(Message message);
    }

    private Bitmap downloadUrlBitmap(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        Bitmap bitmap=null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            bitmap= BitmapFactory.decodeStream(in);
            Log.e(TAG,"in信息："+in.toString());
            Log.e(TAG,"bitmap信息："+bitmap.toString());
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

}