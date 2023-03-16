package cn.zong.app_aidlpool2_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import cn.zong.app_aidlpool2_server.BinderPool;
import cn.zong.app_aidlpool2_server.IBird;
import cn.zong.app_aidlpool2_server.IFish;
import cn.zong.app_aidlpool2_server.IMonkey;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "nanzong";
    private BinderPool mBinderPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBinderPool = BinderPool.getInstance(MainActivity.this);
                IBinder birdBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_BIRD);
                IBinder fishBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_FISH);
                IBinder monkeyBinder = mBinderPool.queryAnimal(BinderPool.ANIMAL_CODE_MONKEY);

                IBird bird = IBird.Stub.asInterface(birdBinder);
                IFish fish = IFish.Stub.asInterface(fishBinder);
                IMonkey monkey = IMonkey.Stub.asInterface(monkeyBinder);

                try {
                    bird.fly();
                    fish.swim();
                    monkey.climbTree();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}