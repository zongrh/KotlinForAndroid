package cn.zong.app_aidlpool2_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * FileName: MonkeyBinder
 * Author: nanzong
 * Date: 2023/3/17 02:03
 * Description:
 * History:
 */
public class MonkeyBinder extends IMonkey.Stub {
    private static final String TAG = "nanzong";
    @Override
    public void climbTree() throws RemoteException {
        Log.e(TAG, "I'm monkey, I can climb the tree.");
    }
}