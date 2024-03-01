package cn.nanzong.myokhttp_write.okhttp;

import java.io.IOException;

/**
 * FileName: Callback
 * Author: nanzong
 * Date: 2023/3/29 12:57
 * Description: 网络请求后的回调接口
 * History:
 */
public interface Callback {
    /**
     *  失败
     * @param call
     * @param e
     */
    void onFailure(Call call, IOException e);

    /**
     * 正常返回
     * @param call
     * @param response
     */
    void onResponse(Call call, Response response);

} 