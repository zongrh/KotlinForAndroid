package cn.nanzong.myokhttp_write.okhttp;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.nanzong.myokhttp_write.okhttp.interceptor.BridgeInterceptor;
import cn.nanzong.myokhttp_write.okhttp.interceptor.CacheInterceptor;
import cn.nanzong.myokhttp_write.okhttp.interceptor.CallServerInterceptor;
import cn.nanzong.myokhttp_write.okhttp.interceptor.ConnectInterceptor;
import cn.nanzong.myokhttp_write.okhttp.interceptor.Interceptor;
import cn.nanzong.myokhttp_write.okhttp.interceptor.RealInterceptorChain;
import cn.nanzong.myokhttp_write.okhttp.interceptor.RetryAndFollowUpInterceptor;

/**
 * FileName: RealCall
 * Author: nanzong
 * Date: 2023/3/29 12:54
 * Description:
 * History:
 */
public class RealCall implements Call {

    private OkHttpClient client;
    private Request originalRequest;

    public RealCall(Request originalRequest,OkHttpClient client) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    public static Call newCall(Request request, OkHttpClient okHttpClient) {
        return new RealCall(request,okHttpClient);
    }

    //异步请求
    @Override
    public void enqueue(Callback callback) {
        //异步交给线程池
        AsyncCall asyncCall = new AsyncCall(callback);
        client.dispatcher.enqueue(asyncCall);
    }

    //同步请求
    @Override
    public Response execute() {
        return null;
    }

    final class AsyncCall extends NamedRunnable{

        Callback callback;

        public AsyncCall(Callback callback){
            this.callback=callback;
        }

        @Override
        protected void execute() {
            // 来这里，开始访问网络 Request -> Response
            Log.e("TAG","execute");
            // 基于 HttpUrlConnection , OkHttp = Socket + okio(IO)
            final Request request = originalRequest;
            try {
                List<Interceptor> interceptors = new ArrayList<>();
                interceptors.add(new RetryAndFollowUpInterceptor());// 重试
                interceptors.add(new BridgeInterceptor());// 基础 请求信息
                interceptors.add(new CacheInterceptor());// 缓存
                interceptors.add(new ConnectInterceptor());// 建立连接
                interceptors.add(new CallServerInterceptor());// 写数据

                Interceptor.Chain chain = new RealInterceptorChain(interceptors,0,originalRequest);
                Response response = chain.proceed(request);
                callback.onResponse(RealCall.this,response);
            } catch (IOException e) {
                callback.onFailure(RealCall.this,e);
            }
        }
    }
}
