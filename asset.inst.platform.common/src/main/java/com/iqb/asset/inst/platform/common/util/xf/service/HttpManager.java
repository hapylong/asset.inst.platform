/**
 * @Copyright (c) http://www.iqianbang.com/  All rights reserved.
 * @Description: TODO
 * @date 2016年6月14日 下午8:47:10
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.common.util.xf.service;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.iqb.asset.inst.platform.common.util.apach.StringUtil;
/**
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
public class HttpManager {
	static final String ENCODE = "UTF-8";
	public static final String OS = "OS";
	public static final String VERSION = "VERSION";
    static DefaultHttpClient client;
	public static String sessionToken = "";
	static final String HTTP = "http";
	static final String HTTPS = "https";
	static final int CONN_PER_ROUTE_BEAN = 20;
	static final int MAX_TOTAL_CONNECTIONS = 200;
	static final int CONNECTION_TIME_OUT = 30000;
	static final int SOCKET_TIME_OUT = 30000;
	static final int SOCKET_BUFFER_SIZE = 8192;
	static final int HTTP_PROXY_PORT = 80;
	static final int HTTPS_PROXY_PORT = 443;

	static {
		try {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");
			HttpProtocolParams.setUseExpectContinue(params, false);

			HttpConnectionParams.setStaleCheckingEnabled(params, false);
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			HttpConnectionParams.setSoTimeout(params, 30000);

			HttpConnectionParams.setSocketBufferSize(params, 8192);

			HttpClientParams.setRedirecting(params, false);

			ConnManagerParams.setMaxConnectionsPerRoute(params,
					new ConnPerRouteBean(20));
			ConnManagerParams.setMaxTotalConnections(params, 200);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));

			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory csf = new MySSLSocketFactory(trustStore);
			csf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			schReg.register(new Scheme("https", csf, 443));

			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			client = new DefaultHttpClient(conMgr, params);
			client.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
				public boolean retryRequest(IOException exception,
						int executionCount, HttpContext context) {
					return executionCount < 3;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String sendSSLPostRequest(String url,
			Map<String, String> params, String encoding) {
		String result = null;
		HttpPost httpPost = null;

		label289: try {
			httpPost = new HttpPost(url);
			List formParams = new ArrayList();
			for (Map.Entry entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair((String) entry.getKey(),
						(String) entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));

			HttpResponse response = client.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity resEntity = response.getEntity();

				if (resEntity != null) {
					try {
						if (StringUtil.isEmpty(encoding)) {
							encoding = "UTF-8";
						}
						result = EntityUtils.toString(resEntity, encoding)
								.trim();
					} finally {
						EntityUtils.consume(resEntity);
					}
					break label289;
				}
			} else {
			}
		} catch (Exception e) {
		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}

		return result;
	}
}
