package cn.nanzong.myokhttp_write.okhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * FileName: Response
 * Author: nanzong
 * Date: 2023/3/29 12:59
 * Description: 响应,通过inputStream解析服务器返回来的数据为String
 * History:
 */
public class Response {
    private final InputStream inputStream;// Skin

    public Response(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String string() {
        return convertStreamToString(inputStream);
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
