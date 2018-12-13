package com.iqb.asset.inst.platform.common.util.apach;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.gson.Gson;

/**
 * 操作json的工具类
 */
@SuppressWarnings("unchecked")
public class JSONUtil{
    
    private static Gson gson = new Gson();

	public static Map<?, ?> toMap(String text, boolean ordered){
		if(ordered){
			return JSON.parseObject(text, LinkedHashMap.class);
		}else{
			return toMap(text);
		}
	}
	
	public static Map<String,Object> toMap(String text){
		Map<String,Object> map = new HashMap<String, Object>();
		JSONObject json = strToJSON(StringUtil.trimToEmpty(text));
		if(json == null){
			return map;
		}
		for(String key : json.keySet()){
			String s = json.getString(key);
			if(StringUtil.isNotEmpty(s)){
				map.put(key, json.get(key));
			}
		}
		return map;
	}
	
	/**
	 * 将字符串转化为json对象
	 * @param str
	 * @return
	 */
	public static JSONObject strToJSON(String str){
		return JSONObject.parseObject(str);
	}
	
	/**
	 * 将json字符转转换为java对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T toJavaObject(JSON json, Class<T> clazz){
		Object obj = new Object();
		obj = JSON.toJavaObject(json, clazz);
		return (T) obj;
	}
	
	
	public static Date getDate(JSONObject json,String key){
		if(json == null){
			return null;
		}
		return json.getDate(key);
	}
	
	public static Boolean getBoolean(JSONObject json,String key){
		if(json == null){
			return null;
		}
		return json.getBoolean(key);
	}

	/**
	 * 将浏览器参数转为json,去除指为空的参数
	 * @param params
	 * @return
	 */
	public static JSONObject paramsToJSON(String params){
		JSONObject json = new JSONObject(true);
		if(StringUtils.isNotEmpty(params)){
			String[] ss = params.split("&");
			for(String s : ss){
				String[] param = s.split("=");
				if(param.length == 2){
					json.put(param[0], param[1]);
				}
			}
		}
		return json;
	}
	
	public static String getStringTrimNull(JSONObject json, String key){
		if(json == null){
			return null;
		}
		String value = json.getString(key);
		return StringUtil.trimToNull(value);
	}
	public static JSONObject filteNullValue(Object object){
		return JSON.parseObject(JSON.toJSONString(object,new ValueFilter() {
			
			@Override
			public Object process(Object object, String name, Object value) {
				if(value == null){
					return "";
				}
				return value;
			}
		}));
	}
	public static JSONArray filteNullValue2(Object object){
	String str = JSONObject.toJSONString(object,new ValueFilter() {
		@Override
		public Object process(Object object, String name, Object value) {
			if(value == null){
				return "";
			}
			return value;
		}
	});
	return (JSONArray) JSON.parse(str);
	}
	
	public static String objToJson(Object obj){
	    if(obj == null){
	        return null;
	    }
	    return JSONObject.toJSONString(obj, SerializerFeature.SortField);
	}
	
	/**
	 * 
	 * Description: Gson转json
	 * @param
	 * @return String
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年8月31日 下午5:24:46
	 */
	public static String objToJsonFromGson(Object obj){
	    if(obj == null){
	        return null;
	    }
	    return gson.toJson(obj);
	}
	
	/**
	 * 
	 * Description: 将json中的日期转换为时间戳
	 * @param
	 * @return JSONObject
	 * @throws DateParseException 
	 * @throws ParseException 
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年8月29日 下午2:02:26
	 */
	public static void formatDateTime(JSONObject objs) throws ParseException{
	    if(objs == null){
	        return;
	    }
	    for(String key : objs.keySet()){
	        if(StringUtil.containsIgnoreCase(key, "time")){
	            Object val = objs.get(key);
	            if(val == null || StringUtil.isEmpty(val.toString())){
	                continue;
	            }
	            Date d = DateUtils.parseDate(val.toString(), new String[]{"yyyy-MM-dd HH:mm:ss"});
	            Integer t = (int) (d.getTime()/1000);
	            objs.put(key, t);
	        }
	    }
	}
	
}