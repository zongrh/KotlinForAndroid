package cn.nanzong.myokhttp_write.okhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import cn.nanzong.myokhttp_write.okhttp.Request;
import cn.nanzong.myokhttp_write.okhttp.RequestBody;
import cn.nanzong.myokhttp_write.okhttp.Response;

/**
 * FileName: BridgeInterceptor
 * Author: nanzong
 * Date: 2023/3/29 20:04
 * Description: 做一个简单的处理，设置一些通用的请求头Content-Type,Connection,Content-Length,Cookie
 * 做一些返回的处理，如果返回数据被压缩了采用 ZipSource，保存Cookie
 * History:
 */
public class BridgeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e("TAG", "BridgeInterceptor");
        Request request = chain.request();
        //添加一些请求头
        request.header("Connection", "keep-alive");
        if (request.requestBody() != null) {
            RequestBody requestBody = request.requestBody();
            //文件的类型
            request.header("Content-Type", requestBody.getContentType());
            //要塞给对方多少东西
            request.header("Content-Length", Long.toString(requestBody.getContentLength()));
        }
        return chain.proceed(request);
    }
}
