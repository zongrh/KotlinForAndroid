package cn.zong.android_aidlpool_client;

import androidx.appcompat.app.AppCompatActivity;

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
import cn.zong.android_aidlpool_server.ICompute;

public class ComputeActivity extends AppCompatActivity {
    private ICompute mICompute;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("nanzongBinderPool", "链接成功：" );
            Toast.makeText(ComputeActivity.this,"链接成功",Toast.LENGTH_SHORT).show();
            try {
                IBinderPool mBinderPool = IBinderPool.Stub.asInterface(service);
                mICompute = ICompute.Stub.asInterface(mBinderPool.queryBinder(200));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("nanzongBinderPool", "链接失败：");
            Toast.makeText(ComputeActivity.this,"链接失败",Toast.LENGTH_SHORT).show();
            mICompute = null;
            bindService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        bindService();

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setPackage("cn.zong.android_aidlpool_server");
        intent.setAction("cn.zong.android_aidlpool_server.action");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    public void onclickCompute(View view) {
        if (mICompute == null) {
            Toast.makeText(ComputeActivity.this,"链接失败onclickCompute",Toast.LENGTH_SHORT).show();
            Log.e("nanzongBinderPool", "链接失败onclickCompute：");
            bindService();
        } else {
            try {
                mICompute.subtraction(20, 1);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}