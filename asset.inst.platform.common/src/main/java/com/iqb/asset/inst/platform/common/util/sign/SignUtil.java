package com.iqb.asset.inst.platform.common.util.sign;

import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名工具类
 * 
 * @author jack
 *
 */
public class SignUtil {

	private static Logger log = LoggerFactory.getLogger(SignUtil.class);
	
	/**
	 * 签名算法
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	public SignUtil() {

	}

	/**
	 * 获取签名
	 * 
	 * @param signStr
	 *            待签名字符串
	 * @return
	 * @throws Exception
	 */
	public static String sign(String signStr,String prikeyPath){
		
		log.info((new StringBuilder()).append("待签名字符串：signStr=").append(signStr).toString());
		String sign = null;
		try {
			Signature sig = Signature.getInstance(SIGN_ALGORITHMS);
			sig.initSign(PkCertFactory.getPrivateKey(prikeyPath));
			sig.update((null == signStr ? "" : signStr).getBytes("UTF-8"));
			byte signData[] = sig.sign();
		    sign = new String(Base64.encode(signData));
		} catch (Exception e) {
			log.error("获取签名异常", e.getMessage());
		}
		log.info((new StringBuilder()).append("签名：sign=").append(sign).toString());
		return sign;
	}

	/**
	 * 签名校验
	 * 
	 * @param sign
	 *            签名
	 * @param plain
	 *            约定口令 可为空
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String sign,String signStr,String certPath){
		log.info((new StringBuilder()).append("待签名字符串：signStr=").append(signStr)
				.toString());
		log.info((new StringBuilder()).append("签名：sign=").append(sign)
				.toString());
		java.security.cert.X509Certificate cert = PkCertFactory.getCert(certPath);
		log.info("获取证书成功");

		boolean b = false;
		try {
			byte signData[] = Base64.decode(sign);
			Signature sig = Signature.getInstance(SIGN_ALGORITHMS);
			sig.initVerify(cert);
			sig.update((null == signStr ? "" : signStr).getBytes("UTF-8"));
		    b = sig.verify(signData);
		} catch (Exception e) {
			log.error("签名校验异常", e.getMessage());
		}
		log.info((new StringBuilder()).append("验证平台签名是否成功").append(b)
				.toString());
		return b;
	}
	
	/**
	 * 获取代签字符串
	 * @return
	 */
	public String getSignStr(){		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		return sdf.format(new Date())+new Random().nextInt(5000000);
	}
}
