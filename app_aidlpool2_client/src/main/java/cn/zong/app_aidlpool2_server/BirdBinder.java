package cn.zong.app_aidlpool2_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * FileName: BirdBinder
 * Author: nanzong
 * Date: 2023/3/17 02:02
 * Description:
 * History:
 */
public class BirdBinder extends IBird.Stub{
    private static final String TAG = "nanzong";
    @Override
    public void fly() throws RemoteException {
        Log.e(TAG, "I'm bird, I can fly.");
    }
}