package cn.zong.app_aidlpool2_server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * FileName: MyService
 * Author: nanzong
 * Date: 2023/3/17 02:11
 * Description:
 * History:
 */
public class MyService extends Service {
    private BinderPool.AnimalBinder mAnimalBinder = new BinderPool.AnimalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAnimalBinder;
    }
}