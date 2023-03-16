package cn.zong.android_ipc_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImagesActivity extends AppCompatActivity  implements ImagesIntentService.UpdateUI {
    private static ImageView imageView;
    /**
     * 图片地址集合
     */
    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871",
            "https://img-blog.csdnimg.cn/img_convert/aebe89ff3e7989445924cf66991e317b.png"
};


    private static final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("DownloadImagesActivityLog","mUIHandler Bitmap：  "+ msg.obj.toString());
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    //必须通过Handler去更新，该方法为异步方法，不可更新UI
    @Override
    public void updateUI(Message message) {

        mUIHandler.sendMessageDelayed(message, message.what * 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_images_service);
        imageView = (ImageView) findViewById(R.id.image);

        Intent intent = new Intent(this, ImagesIntentService.class);
        for (int i = 0; i < 7; i++) {//循环启动任务
            intent.putExtra(ImagesIntentService.DOWNLOAD_URL, url[i]);
            intent.putExtra(ImagesIntentService.INDEX_FLAG, i);
            startService(intent);
        }
        ImagesIntentService.setUpdateUI(this);
    }
}