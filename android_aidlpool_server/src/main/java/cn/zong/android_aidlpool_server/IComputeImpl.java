package cn.zong.android_aidlpool_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * FileName: IComputeImpl
 * Author: nanzong
 * Date: 2023/3/17 00:36
 * Description:
 * History:
 */
public class IComputeImpl extends ICompute.Stub {
    @Override
    public int subtraction(int parameter1, int parameter2) throws RemoteException {
        Log.e("nanzongBinderPool", "两数相加之差为：" + (parameter1 - parameter2));
        return parameter1 - parameter2;
    }
}