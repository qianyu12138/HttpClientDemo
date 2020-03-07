package com.example.httpclient.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public static String httpGet(String url, Map<String, String> header, Map<String, String> params) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建一个GET对象
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (String s : params.keySet()) {
                uriBuilder.addParameter(s, params.get(s));
            }
        }
        HttpGet get = new HttpGet(uriBuilder.build());

        if (header != null) {
            for (String s : header.keySet()) {
                get.addHeader(s, header.get(s));
            }
        }

        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);

        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();

        System.out.println(statusCode);

        HttpEntity entity = response.getEntity();

        String string = EntityUtils.toString(entity, "utf-8");

        log.info("Http Get : url-{} header-{} params-{}", url, header, params);
        log.info("response code-{},body-{}", statusCode, string);

        //关闭httpclient
        response.close();
        httpClient.close();

        return string;
    }

    public static String httpGet(String url) throws IOException, URISyntaxException {
        return httpGet(url, null, null);
    }

    public static String httpPost(String url, Map<String, String> header, Map<String, String> params) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        //执行post请求

        if (params != null) {
            List<NameValuePair> paramsList = new ArrayList<>();
            for (String key : params.keySet()) {
                paramsList.add(new BasicNameValuePair(key, params.get(key)));
            }

            //包装成一个Entity对象
            StringEntity entity = new UrlEncodedFormEntity(paramsList, "utf-8");

            //设置请求的内容
            post.setEntity(entity);
        }

        if (header != null) {
            for (String key : header.keySet()) {
                post.addHeader(key, header.get(key));
            }
        }

        CloseableHttpResponse response = httpClient.execute(post);

        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        String string = EntityUtils.toString(response.getEntity());

        log.info("Http Post : url-{} header-{} params-{}",url,header,params);
        log.info("response code-{},body-{}",statusCode,string);
        response.close();

        httpClient.close();

        return string;
    }

    public static String httpPost(String url,Map<String,String> params) throws IOException {
        return httpPost(url,null,params);
    }
}
