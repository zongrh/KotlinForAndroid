package cn.zong.app_aidlpool2_server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

/**
 * FileName: BinderPool
 * Author: nanzong
 * Date: 2023/3/17 02:06
 * Description:
 * History:
 */
public class BinderPool {
    private static final String TAG = "nanzong";
    public static final int NO_ANIMAL = 0;
    public static final int ANIMAL_CODE_BIRD = 1;
    public static final int ANIMAL_CODE_FISH = 2;
    public static final int ANIMAL_CODE_MONKEY = 3;

    private Context mContext;
    @SuppressWarnings("all")
    private static BinderPool sInstance;
    private CountDownLatch mCountDownLatch;
    private IAnimal mAnimalPool;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {

        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent();
        intent.setPackage("cn.zong.app_aidlpool2_server");
        intent.setAction("cn.zong.app_aidlpool2_server.action");

        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAnimalPool = IAnimal.Stub.asInterface(service);
            mCountDownLatch.countDown();
            Toast.makeText(mContext,"链接成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            Toast.makeText(mContext,"链接失败",Toast.LENGTH_SHORT).show();
        }
    };

    public IBinder queryAnimal(int animalCode) {
        IBinder binder = null;
        try {
            if (mAnimalPool != null) {
                binder = mAnimalPool.queryAnimal(animalCode);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return binder;
    }

    public static class AnimalBinder extends IAnimal.Stub {
        @Override
        public IBinder queryAnimal(int animalCode) throws RemoteException {
            IBinder binder = null;
            switch (animalCode) {
                case ANIMAL_CODE_BIRD:
                    binder = new BirdBinder();
                    break;
                case ANIMAL_CODE_FISH:
                    binder = new FishBinder();
                    break;
                case ANIMAL_CODE_MONKEY:
                    binder = new MonkeyBinder();
                    break;
            }
            return binder;
        }
    }
} 