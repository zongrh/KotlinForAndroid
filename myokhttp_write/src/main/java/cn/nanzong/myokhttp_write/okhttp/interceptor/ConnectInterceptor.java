package cn.nanzong.myokhttp_write.okhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import cn.nanzong.myokhttp_write.okhttp.Request;
import cn.nanzong.myokhttp_write.okhttp.Response;

/**
 * FileName: ConnectInterceptor
 * Author: nanzong
 * Date: 2023/3/29 20:26
 * Description: findHealthyConnection()找一个连接,首先判断有没有健康的,没有就创建(建立Socket,握手连接),连接缓存
 * okHttp是基于原生的 Socket + okio (原生IO的封装)
 * 封装 HttpCodec 里面封装了okio的 Source(输入) 和 Sink(输出),我们通过 HttpCodec 就可以操作 Socket的输入输出,我们就可以向服务器写数据和读取返回数据
 * History:
 */
public class ConnectInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e("TAG","ConnectInterceptor");
        Request request = chain.request();
        return chain.proceed(request);
    }
}