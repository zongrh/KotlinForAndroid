package cn.zong.app_aidlpool2_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * FileName: FishBinder
 * Author: nanzong
 * Date: 2023/3/17 02:03
 * Description:
 * History:
 */
public class FishBinder extends IFish.Stub{
    private static final String TAG = "nanzong";

    @Override
    public void swim() throws RemoteException {
        Log.e(TAG, "I'm fish, I can swim.");
    }
}