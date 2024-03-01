package cn.nanzong.myokhttp_write.okhttp.interceptor;

import java.io.IOException;

import cn.nanzong.myokhttp_write.okhttp.Request;
import cn.nanzong.myokhttp_write.okhttp.Response;

/**
 * FileName: Interceptor
 * Author: nanzong
 * Date: 2023/3/29 20:02
 * Description:
 * History:
 */
public interface Interceptor {
    Response intercept(Chain chain) throws IOException;


    interface Chain {
        Request request();

        Response proceed(Request request) throws IOException;
    }

}
