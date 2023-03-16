package cn.zong.app_aidlpool2_server;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * FileName: AnimalBinder
 * Author: nanzong
 * Date: 2023/3/17 02:00
 * Description:
 * History:
 */
public class AnimalBinder extends IAnimal.Stub {

    public static final int ANIMAL_CODE_BIRD = 1;
    public static final int ANIMAL_CODE_FISH = 2;
    public static final int ANIMAL_CODE_MONKEY = 3;

    @Override
    public IBinder queryAnimal(int animalCode) throws RemoteException {
        IBinder mBinder = null;
        switch (animalCode) {
            case ANIMAL_CODE_BIRD:
                mBinder = new BirdBinder();
                break;
            case ANIMAL_CODE_FISH:
                mBinder = new FishBinder();
                break;
            case ANIMAL_CODE_MONKEY:
                mBinder = new MonkeyBinder();
                break;
        }
        return mBinder;
    }
}