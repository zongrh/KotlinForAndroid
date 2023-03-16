package cn.zong.android_aidlpool_server;

import android.os.RemoteException;
import android.util.Log;

/**
 * FileName: IOperationImpl
 * Author: nanzong
 * Date: 2023/3/17 00:35
 * Description:
 * History:
 */
public class IOperationImpl extends IOperation.Stub {
    @Override
    public int add(int parameter1, int parameter2) throws RemoteException {
        Log.e("nanzongBinderPool", "两数相加之和为：" + (parameter1 + parameter2));
        return parameter1 + parameter2;
    }
}