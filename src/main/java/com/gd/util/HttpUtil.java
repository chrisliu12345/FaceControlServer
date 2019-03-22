package com.gd.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpUtil {

    public  static  final String ContentJson="application/json";
    public  static  final String ContentXml="application/xml";

    /**
     * http get
     * @param urlstr
     * @param params url parm
     * @param requstHeader http header parm
     * @param charset 字符集
     * @return
     */
    public static ResposeData httpGet(String urlstr, String params,Map<String,String> requstHeader, String charset) {
        ResposeData resposeData = new ResposeData();
        charset = charset == null ? "utf-8" : charset;
        if (params != null && params.length() > 0) {
            if (!urlstr.endsWith("?")) {
                urlstr += "?";
            }
            urlstr += params;
        }

        try {
            // 新建一个URL对象
            URL url = new URL(urlstr);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间 默认是无限大
            urlConn.setConnectTimeout(10000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(10000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(false);
            // 设置为Get请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。Get 不需要
            //urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            if(requstHeader!=null && requstHeader.size()>0){
                for (Map.Entry<String, String> stringStringEntry : requstHeader.entrySet()) {
                    urlConn.addRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
                }
            }
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if(urlConn.getResponseCode()==200){
                resposeData.data= streamToString(urlConn.getInputStream(),charset);
            }
            resposeData.code= urlConn.getResponseCode();
            urlConn.disconnect();

        } catch (Exception e) {
            resposeData.code=500;
            resposeData.data=GetExceptionStackTrace(e);
            System.out.println(resposeData.data);
        }
        return  resposeData;
    }

    /**
     * http get
     * @param urlstr
     * @param paramMap url parm
     * @param requstHeader http header parm
     * @param charset
     * @return
     */
    public static ResposeData httpGet(String urlstr, Map<String, String> paramMap,Map<String,String> requstHeader, String charset) {
        charset = charset == null ? "utf-8" : charset;
        String params = null;
        int i = 0;
        for (String key : paramMap.keySet()) {
            if (i > 0) {
                urlstr += "&";
            }
            urlstr += key + "=" + paramMap.get(key);
            i++;
        }

        return httpGet(urlstr, params,requstHeader, charset);
    }

    /**
     * http post
     * @param urlstr
     * @param paramMap url parm
     * @param requstHeader http header parm
     * @param requestbody  http body data
     * @param contenttype body data type
     * @param charset
     * @return
     */
    public static ResposeData httpPost(String urlstr, Map<String, String> paramMap,Map<String,String> requstHeader,String requestbody, String contenttype, String charset) {
        charset = charset == null ? "utf-8" : charset;
        String params = null;
        int i = 0;
        for (String key : paramMap.keySet()) {
            if (i > 0) {
                urlstr += "&";
            }
            urlstr += key + "=" + paramMap.get(key);
            i++;
        }
        return httpPost(urlstr,params,requstHeader,requestbody,contenttype,charset);
    }

    /**
     * http post
     * @param urlstr
     * @param params url parm
     * @param requstHeader http header parm
     * @param requestbody  http body data
     * @param contenttype body data type
     * @param charset
     * @return
     */
    public static ResposeData httpPost(String urlstr, String params,Map<String,String> requstHeader, String requestbody, String contenttype,String charset) {
        ResposeData resposeData = new ResposeData();
        charset = charset == null ? "utf-8" : charset;
        if (params != null && params.length() > 0) {
            if (!urlstr.endsWith("?")) {
                urlstr += "?";
            }
            urlstr += params;
        }

        // 新建一个URL对象
        URL url = null;
        try {
            url = new URL(urlstr);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间 默认是无限大
            urlConn.setConnectTimeout(10000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(10000);
            // 设置是否使用缓存  Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为POST请求
            urlConn.setRequestMethod("POST");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", contenttype);
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            if(requstHeader!=null && requstHeader.size()>0){
                for (Map.Entry<String, String> stringStringEntry : requstHeader.entrySet()) {
                    urlConn.addRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
                    //urlConn.setRequestProperty(stringStringEntry.getKey(),stringStringEntry.getValue());
                }
            }
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);

            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // 开始连接
            urlConn.connect();
            if (requestbody!=null && requestbody.length()>0) {
                OutputStream outputStream = urlConn.getOutputStream();
                outputStream.write(requestbody.getBytes(charset));
                outputStream.flush();
                outputStream.close();
            }
            // 判断请求是否成功
            if(urlConn.getResponseCode()==200){
                resposeData.data= streamToString(urlConn.getInputStream(),charset);
            }
            resposeData.code= urlConn.getResponseCode();
            urlConn.disconnect();
        } catch (Exception e) {
            resposeData.code=500;
            resposeData.data=GetExceptionStackTrace(e);
            System.out.println(resposeData.data);
        }

        return resposeData;
    }

    public static String streamToString(InputStream inputStream,String charset) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        String readline = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((readline = bufferedReader.readLine()) != null) {
            stringBuilder.append(readline);
        }
        return stringBuilder.toString();
    }
    public static String GetExceptionStackTrace(Exception e) {
        if (e == null) {
            return "";
        } else {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            return stringWriter.toString();
        }
    }
    public static class ResposeData{
        private int code;
        private String data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}