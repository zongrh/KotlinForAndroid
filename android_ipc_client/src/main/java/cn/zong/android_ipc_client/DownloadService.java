package cn.zong.android_ipc_client;

/**
 * FileName: DownloadService
 * Author: nanzong
 * Date: 2023/3/12 23:23
 * Description:
 * History:
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Monkey.C on 2016/5/14.
 */
public class DownloadService extends IntentService {

    private final String TAG = "DownloadService";

    // 此构造方法必须要有，且需要调用 super(String name) 方法
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpURLConnection conn = null;
        InputStream is = null;
        File file = Environment.getExternalStoragePublicDirectory("Download");
        // 指定存放图片的路径及图片的名称
        File newFile = new File(file, "download.jpg");
        try {
            URL url = new URL("https://img-blog.csdnimg.cn/img_convert/aebe89ff3e7989445924cf66991e317b.png");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(8000);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                byte[] buff = new byte[100];
                int len;
                // 将下载的图片写入到 SD 卡中
                FileOutputStream fos = new FileOutputStream(newFile);
                while ((len = is.read(buff)) != -1) {
                    fos.write(buff, 0, len);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null || conn != null) {
                try {
                    conn.disconnect();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // 当执行完成所有子线程之后，IntentService 会自己停止服务
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "图片下载完成！");
        Log.i(TAG, "onDestroy");
    }

}