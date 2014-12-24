package org.hope6537.utils;

import com.squareup.okhttp.*;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Zhaopeng-Rabook on 14-12-24.
 */
public class OkHttpTest {

    @Test
    public void testGet() throws IOException {

       /* String str = postCCU();
        boolean isBlocked = str.contains("服务器压力过大");
        int index = 0;
        while (isBlocked && index < 100) {
            index++;
            System.out.println("第" + index + "次提交");
            str = postCCU();
            isBlocked = str.contains("服务器压力过大");
        }*/
        System.out.println(post());

    }

    private String get(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String post() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:3030/JiChuang_OA/page/login.hopedo")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String cookie = response.header("Set-Cookie").split(";")[0] + ";";
        Headers l = response.headers();
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", "hope6537@qq.com")
                .add("password", "4236537")
                .build();
        Request postRequest = new Request.Builder()
                .url("http://localhost:3030/JiChuang_OA/member/login.hopedo")
                .addHeader("Cookie", cookie)
                .post(formBody)
                .build();
        Response postResponse = okHttpClient.newCall(postRequest).execute();

        Request getRequest = new Request.Builder()
                .addHeader("Cookie", cookie)
                .url("http://localhost:3030/JiChuang_OA/member/1/toUpdate.hopedo")
                .build();
        Response getResponse = okHttpClient.newCall(getRequest).execute();


        return getResponse.body().string();
    }

    private String postCCU() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.cdjwc.com/jiaowu/Login.aspx")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        String cookie = response.header("Set-Cookie").split(";")[0];
        RequestBody formBody = new FormEncodingBuilder()
                .add("__VIEWSTATE", "/wEPDwUKLTY2NzUxNzg0OQ9kFgICAw9kFgYCAg8PFgIeBFRleHRlZGQCBg8PFgIfAGVkZAIHDw8WAh8ABQQxMDAwZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFB0Noa1VzZXKzlXfYqx3xhzgKo8zX5QljGmZkqA==")
                .add("__VIEWSTATEGENERATOR", "22875D65")
                .add("__EVENTVALIDATION", "/wEWBQLik+//BwKvo8HwCwKG85bvBgLAiqigBwKZwO3DDTq5AhcAYmGEkCvRGdkpaMJiQtSF")
                .add("Account", "041240826")
                .add("PWD", "041240826")
                .add("cmdok", "")
                .build();
        Request postRequest = new Request.Builder()
                .url("http://www.cdjwc.com/jiaowu/Login.aspx")
                .addHeader("Cookie", cookie)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Cache_Control", "max-age=0")
                .addHeader("Origin", "http://www.cdjwc.com")
                .addHeader("Referer", "http://www.cdjwc.com/jiaowu/Login.aspx")
                .addHeader("Accept_Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4")
                .post(formBody)
                .build();
        Response postResponse = okHttpClient.newCall(postRequest).execute();

        return postResponse.body().string();
    }


}
