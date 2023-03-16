package cn.zong.android_aidlpool_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

/**
 * FileName: BinderPoolService
 * Author: nanzong
 * Date: 2023/3/17 00:36
 * Description:
 * History:
 */
public class BinderPoolService extends Service {

    private class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case 100: {
                    return new IOperationImpl();
                }
                case 200: {
                    return new IComputeImpl();
                }
            }
            return null;
        }
    }

    private Binder mBinderPool;

    public BinderPoolService() {
        mBinderPool = new BinderPoolImpl();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}