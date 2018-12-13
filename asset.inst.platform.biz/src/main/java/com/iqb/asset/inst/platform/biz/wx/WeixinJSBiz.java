package com.iqb.asset.inst.platform.biz.wx;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
import com.iqb.asset.inst.platform.common.util.sys.Attr.WXConst;

/**
 * 微信接口相关service服务
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2016年2月22日-下午4:00:39
 */
@SuppressWarnings({"rawtypes", "finally", "deprecation", "resource"})
@Component
public class WeixinJSBiz{
	
	/**
	 * 日志类
	 */
	private Logger logger = LoggerFactory.getLogger(WeixinJSBiz.class);
	
	/**
	 * 注入微信服务biz
	 */
	@Autowired
	private CacheWeCharBiz cacheWeCharBiz;
	
	@Autowired
    private BaseConfig baseConfig;
	 
	/**
	 * 
	 * Description: 获取微信的access_token 
	 * @param wxInd 
	 * @param
	 * @return String
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月28日 下午8:27:31
	 */
	public String getWxAccessToken(JSONObject objs){
	    
	    String appId = objs.getString("appId");
        String secret = objs.getString("secret");
		
		/**
		 * 封装请求信息
		 */
        String turl = String.format("%s?grant_type=client_credential&appid=%s&secret=%s", baseConfig.getWxGetAccessTokenUrl(), appId, secret);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(turl);
        String result = null;
        try{
            HttpResponse res = client.execute(get);
            String responseContent = null; 
            HttpEntity entity = res.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            Map jsonMap = (Map) JSONObject.parse(responseContent);
            /** 日志打印出返回数据  **/
            logger.debug(JSONObject.toJSONString(jsonMap));
            /** 将json字符串转换为json对象  **/
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                if (jsonMap.get("errcode") != null){
                	/** 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"} **/
                }
                else{
                	/** 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200} **/
                    result = (String) jsonMap.get("access_token");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            /** 关闭连接 ,释放资源  **/
            client.getConnectionManager().shutdown();
            return result;
        }
    }
	
	/**
	 * 
	 * Description: 获取JsApiTicket(1.调用微信js接口时使用)
	 * @param wxInd 
	 * @param
	 * @return String
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月28日 下午8:41:41
	 */
	public String getJsApiTicket(JSONObject objs) {
		/** 初始化ticket  **/
        String ticket = null;
        InputStream is = null;
        String appId = objs.getString("appId");
        try {
        	/**
        	 * 从redis中取出access_token，判断取出来的值是否为空，为空则手动再次获取
        	 */
        	String access_token =cacheWeCharBiz.getCacheWeCharToken(appId);
        	int i = 0;
			while((null == access_token || "".equals(access_token)) && i<10){
				Thread.sleep(3000);
				access_token = getWxAccessToken(objs);
				if(null != access_token || !"".equals(access_token)){
					cacheWeCharBiz.setCacheWeCharToken(access_token,appId);
				}
				i++;
			}
        	
        	/**
        	 * http接口调用
        	 */
        	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); 
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
            http.connect();
            is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            
            /**
             * 获取到返回结果并进行解析
             */
            String message = new String(jsonBytes, "UTF-8");
            JSONObject retJson = JSONObject.parseObject(message);
            ticket = retJson.getString("ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	 try {
        		if(is != null){
        			is.close();
        		}
			} catch (IOException e) {
				
			}
        } 
        return ticket;
    }
	
	/**
	 * 
	 * Description: js接口调用所需要的信息
	 * @param wxInd 
	 * @param
	 * @return Map<String,String>
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年3月28日 下午8:53:00
	 */
	public Map<String, String> sign(String url, JSONObject objs) {
		/**
		 * 初始化返回值
		 */
		Map<String, String> ret = new HashMap<String, String>();
		String appId = objs.getString("appId");
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		String jsapi_ticket = "";
		try{
			/**
			 * 从redis中取出jsapi_ticket，判断取出来的值是否为空，为空则手动再次获取
			 */
			jsapi_ticket = cacheWeCharBiz.getCacheWeCharJsApiTicket(appId);
			int i = 0;
			while((null == jsapi_ticket || "".equals(jsapi_ticket)) && i<10){
				Thread.sleep(3000);
				jsapi_ticket = getJsApiTicket(objs);
				if(null != jsapi_ticket || !"".equals(jsapi_ticket)){
					cacheWeCharBiz.setCacheWeCharJsApiTicket(jsapi_ticket,appId);
				}
				i++;
			}
			
			/** 注意这里参数名必须全部小写，且必须有序  **/
			string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str +
					"&timestamp=" + timestamp + "&url=" + url;
			
		    MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
		    signature = byteToHex(crypt.digest());
		}
		catch (Exception e){
		    e.printStackTrace();
		}
		
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appId", appId);
	
	    return ret;
	}
	
	private static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	private static String create_nonce_str() {
	    return UUID.randomUUID().toString();
	}
	
	private static String create_timestamp() {
	    return Long.toString(System.currentTimeMillis() / 1000);
	}

}
