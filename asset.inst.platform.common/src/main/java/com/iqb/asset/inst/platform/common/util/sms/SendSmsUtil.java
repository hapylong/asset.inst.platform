package com.iqb.asset.inst.platform.common.util.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.config.BaseConfig;

/**
 * 发送短信工具类
 * 
 * @author lj
 *
 */
@Component
public class SendSmsUtil {

	/**
	 * 系统参数配置注入
	 */
	@Autowired
	private BaseConfig baseConfig;

	/**
	 * 
	 * Description: 发送短信接口
	 * 
	 * @param url
	 *            应用地址，类似于http://ip:port/msg/
	 * @param un
	 *            账号
	 * @param pw
	 *            密码
	 * @param phone
	 *            手机号码，多个号码使用","分割
	 * @param msg
	 *            短信内容
	 * @param rd
	 *            是否需要状态报告，需要1，不需要0
	 * @return String
	 * @throws
	 * @Author wangxinbang Create Date: 2016年12月5日 下午4:59:51
	 */
	public static String batchSend(String path, String postContent)
			throws Exception {
	    URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            httpURLConnection.setConnectTimeout(10000);//连接超时 单位毫秒
            httpURLConnection.setReadTimeout(10000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            
//          PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//          printWriter.write(postContent);
//          printWriter.flush();

            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();
            
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                // 开始获取数据
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
}
