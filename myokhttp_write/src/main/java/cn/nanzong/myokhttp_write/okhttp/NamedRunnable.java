package cn.nanzong.myokhttp_write.okhttp;

/**
 * FileName: NamedRunnable
 * Author: nanzong
 * Date: 2023/3/29 19:59
 * Description:
 * History:
 */
public abstract class NamedRunnable implements Runnable {

    @Override
    public void run() {
        execute();
    }

    protected abstract void execute();
}