package cn.nanzong.myokhttp_write.okhttp.interceptor;

import android.util.Log;

import java.io.IOException;

import cn.nanzong.myokhttp_write.okhttp.Request;
import cn.nanzong.myokhttp_write.okhttp.Response;

/**
 * FileName: CacheInterceptor
 * Author: nanzong
 * Date: 2023/3/29 20:09
 * Description: 在缓存可用的情况下，读取本地的缓存数据，如果没有直接去服务器，
 * 如果有判断有没有缓存策略，然后判断有没有过期，如果没过期直接拿缓存，
 * 如果过期了需要添加一些之前的头部信息如:If-Modified-Since,
 * 这个时候后台有可能给你返回304代表你是可以拿本地缓存，每次读取到新的响应做一次缓存
 * History:
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e("TAG","CacheInterceptor");
        Request request = chain.request();
        // 本地有没有缓存，如果有没过期
        /*if(true){
            return new Response(new );
        }*/

        return chain.proceed(request);
    }
}
