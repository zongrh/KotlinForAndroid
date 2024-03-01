package cn.nanzong.myokhttp_write.okhttp;

/**
 * FileName: Call
 * Author: nanzong
 * Date: 2023/3/29 12:55
 * Description: 请求的Call顶层接口
 * History:
 */
public interface Call {
    /**
     * 发起异步请求
     * @param callback
     */
    void enqueue(Callback callback);

    /**
     * 发起同步请求
     * @return
     */
    Response execute();

} 