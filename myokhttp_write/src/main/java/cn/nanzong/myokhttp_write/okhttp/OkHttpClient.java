package cn.nanzong.myokhttp_write.okhttp;

/**
 * FileName: OkHttpClient
 * Author: nanzong
 * Date: 2023/3/29 12:05
 * Description: OkHttp客户端对象
 * History:
 */
public class OkHttpClient {

    Dispatcher dispatcher;

    public OkHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    public Call newCall(Request request) {
        return RealCall.newCall(request, this);
    }

    public static class Builder {
        Dispatcher dispatcher;

        public Builder() {
            dispatcher = new Dispatcher();
        }

        public OkHttpClient builder() {
            return new OkHttpClient(this);
        }
    }
}
