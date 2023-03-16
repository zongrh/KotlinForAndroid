package cn.zong.android_aidlpool_client;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.zong.android_aidlpool_server.IBinderPool;
import cn.zong.android_aidlpool_server.IOperation;

public class MainActivity extends AppCompatActivity {
    private IOperation mIOperation;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("nanzongBinderPool", "链接成功：");
            Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
            try {
                IBinderPool mBinderPool = IBinderPool.Stub.asInterface(service);
                mIOperation = IOperation.Stub.asInterface(mBinderPool.queryBinder(100));

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("nanzongBinderPool", "链接失败：");
            Toast.makeText(MainActivity.this,"链接失败",Toast.LENGTH_SHORT).show();
            mIOperation = null;
            bindService();
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("cn.zong.android_aidlpool_server.action");
        intent.setPackage("cn.zong.android_aidlpool_server");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    public void onclickOperation(View view) {
        if (mIOperation != null) {
            try {
                mIOperation.add(1, 2);
                Log.e("nanzongBinderPool", "链接成功  add：");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(MainActivity.this,"链接失败onclickOperation",Toast.LENGTH_SHORT).show();
            Log.e("nanzongBinderPool", "链接失败onclickOperation：");
            bindService();
        }
    }

    public void onclickComputeActivity(View view) {
        if (mIOperation == null) {
            Toast.makeText(MainActivity.this,"链接失败onclickCompute",Toast.LENGTH_SHORT).show();
            Log.e("nanzongBinderPool", "链接失败onclickCompute：");
            bindService();
        } else {
            startActivity(new Intent(MainActivity.this, ComputeActivity.class));
        }
    }
}