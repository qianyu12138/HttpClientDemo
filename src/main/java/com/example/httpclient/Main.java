package com.example.httpclient;

import com.example.httpclient.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("param","中国");

        Map<String,String> header = new HashMap<String, String>();
        header.put("content-type","json/text");
        try {
            String s = HttpUtils.httpPost("http://localhost:8080/test1", header, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
