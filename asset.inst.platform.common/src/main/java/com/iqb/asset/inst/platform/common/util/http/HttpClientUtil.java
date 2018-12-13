package com.iqb.asset.inst.platform.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;

/**
 * Created by dell on 2015/1/13.
 */
@SuppressWarnings({"resource", "deprecation", "rawtypes", "unchecked"})
public class HttpClientUtil {

   
    /**
     * 封装HTTP POST方法
     * @param
     * @param
     * @return
     * @throws IqbException 
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String post(String url, Map<String, String> paramMap) throws IqbException {
        String httpEntityContent = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> formparams = setHttpParams(paramMap);
            UrlEncodedFormEntity param;
                param = new UrlEncodedFormEntity(formparams, "UTF-8");
            param.setContentType("application/json");
            httpPost.setEntity(param);
            HttpResponse response = httpClient.execute(httpPost);
            httpEntityContent = getHttpEntityContent(response);
            httpPost.abort();
        } catch (Exception e) {
            throw new IqbException(CommonReturnInfo.BASE00040001);
        }
        return httpEntityContent;
    }
    
    /**
     * 封装HTTP POST方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String rbPost(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPost.setEntity(param);
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }
    
    /**
     * 
     * Description:rest接口调用 
     * @param
     * @return String
     * @throws
     * @Author wangxinbang
     * Create Date: 2016年9月22日 下午2:22:24
     */
    public static String restPost(String url, Map<String, String> paramMap){
        RestTemplate restTemplate = new RestTemplate();
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setReadTimeout(5000);
//        requestFactory.setConnectTimeout(5000);
//        restTemplate.setRequestFactory(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Charset", "UTF-8");
        org.springframework.http.HttpEntity entity = new org.springframework.http.HttpEntity(JSON.toJSON(paramMap),headers);
        ResponseEntity response = restTemplate.exchange(url,HttpMethod.POST, entity, Map.class);
        LinkedHashMap responseMap = (LinkedHashMap)response.getBody();
        return JSON.toJSONString(responseMap);
    }
    
    /**
     * 封装HTTP POST方法
     * @param
     * @param （如JSON串）
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException  
     * @author adam 修改方法
     */
    public static String post(String url, String data) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","text/json; charset=utf-8");
        httpPost.setEntity(new StringEntity(data, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String httpEntityContent = getHttpEntityContent(response);
        httpPost.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        httpGet.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP GET方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpGet.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpGet);
        String httpEntityContent = getHttpEntityContent(response);
        httpGet.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP PUT方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        List<NameValuePair> formparams = setHttpParams(paramMap);
        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
        httpPut.setEntity(param);
        HttpResponse response = httpClient.execute(httpPut);
        String httpEntityContent = getHttpEntityContent(response);
        httpPut.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String delete(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete= new HttpDelete();
        httpDelete.setURI(URI.create(url));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }

    /**
     * 封装HTTP DELETE方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete();
        List<NameValuePair> formparams = setHttpParams(paramMap);
        String param = URLEncodedUtils.format(formparams, "UTF-8");
        httpDelete.setURI(URI.create(url + "?" + param));
        HttpResponse response = httpClient.execute(httpDelete);
        String httpEntityContent = getHttpEntityContent(response);
        httpDelete.abort();
        return httpEntityContent;
    }


    /**
     * 设置请求参数
     * @param
     * @return
     */
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }

    /**
     * 获得响应HTTP实体内容
     * @param response
     * @return
     * @throws java.io.IOException
     * @throws java.io.UnsupportedEncodingException
     */
    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            br.close();
            is.close();
            return sb.toString();
        }
        return "";
    }

   
   
}
