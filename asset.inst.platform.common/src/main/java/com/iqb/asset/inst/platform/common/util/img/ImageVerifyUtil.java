package com.iqb.asset.inst.platform.common.util.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * Description: 用于生成图片加减运算验证码（1.用户注册验证码校验）
 * @author iqb
 * @version 1.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
------------------------------------------------------------------
 * 2016年2月19日    wangxinbang     1.0        1.0 Version 
 * </pre>
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class ImageVerifyUtil {
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ImageVerifyUtil.class);
	
	/**
	 * 
	 * Description: 生成图片二维码（1.用户注册数字加减验证码校验; 2.用户手机短信登录; 3.用户修改登录密码）
	 * @param filepath		图片保存路径
	 * @param fontFileName	字体文件路径
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年2月19日 上午11:15:10
	 */
	public static Map generateImgVerify(String fontFileName){
		String val = "";//图片上的内容
		int res = 0;//数字加减结果
		Random rand = new Random();
		int type = rand.nextInt(2);//生成一个0 ， 1任一随机数
		if(type == 1){//如果生成的随机数是1，那么就进行加法运算
			int num1 = rand.nextInt(19)+1;
			int num2 = rand.nextInt(19)+1;
			val = num1 + " + " + num2;
			res = num1 + num2;
		}else{//如果生成的随机数是0，那么就进行减法预算
			int num1 = rand.nextInt(89)+10;
			int num2 = rand.nextInt(9)+1;
			val = num1 + " - " + num2;
			res = num1 - num2;
		}
		val = val + " =";
		BufferedInputStream bis = getImage(283, 48, 50f, fontFileName, val);
		Map m = new HashMap();
		m.put("bis", bis);
		m.put("res", res + "");
		return m;
	}
	
	/**
	 * 
	 * Description: 生成数字运算验证码图片
	 * @param width			图片宽度
	 * @param height		图片高度
	 * @param fontpx		字体大小
	 * @param filepath		图片保存路径
	 * @param fontFileName	字体文件路径
	 * @param value			验证码图片内容
	 * @return 
	 * @return void
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年2月19日 上午11:22:34
	 */
	private static BufferedInputStream getImage(int width, int height, float fontpx, String fontFileName, String value){
		BufferedInputStream bis = null;
		int num = 25; // 每行字数
		Font font = loadFont(fontFileName, fontpx); // 字体
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		Color bgColor = new Color(246, 241, 235);//弹出匡的背景色
		g2.setBackground(bgColor); // 背景颜色
		g2.clearRect(0, 0, width, height);
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(value, context);
		double x = 20;//
		double y = -8;//
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		// //////////////////////////////////////////////////
		String aa = "";
		g2.setFont(font);
		Color fontColor = new Color(248, 100, 2); //文字的颜色
		g2.setPaint(fontColor);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  
		do {
			if (value.length() > num) {
				aa = value.substring(0, 25);
				value = value.substring(num, value.length());
			} else
				break;
			g2.drawString(aa, (int) x, (int) baseY);
			baseY += num;
		} while (value.length() - 25 > 0);
		if (value.length() < num)
			g2.drawString(value, (int) x, (int) baseY);
		// ////////////////////////////////////////////////
		try {
			bis = getInputStreamFromImage(bi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bis;
	}
	
	/**
	 * 
	 * Description: 读取字体文件获取字体信息（1.生成数字验证码）
	 * @param
	 * @return Font
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年2月19日 上午11:21:43
	 */
	public static Font loadFont(String fontFileName, float fontSize)  //第一个参数是外部字体名，第二个是字体大小
    {
        try
        {
            File file = new File(fontFileName);
            FileInputStream aixing = new FileInputStream(file);
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, aixing);
            Font dynamicFontPt = dynamicFont.deriveFont(fontSize);
            aixing.close();
            return dynamicFontPt;
        }
        catch(Exception e)//异常处理
        {
            e.printStackTrace();
            return new java.awt.Font("宋体", Font.PLAIN, 14);
        }
    }
	
	/**
	 * 
	 * Description: 根据url获取网络连接output
	 * @param
	 * @return OutputStream
	 * @throws IOException 
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年4月5日 下午6:52:15
	 */
	private static BufferedOutputStream getImageOutputStream(String urlStr) throws IOException{
		URLConnection urlconnection=null;
		URL url = new URL(urlStr);
		urlconnection = url.openConnection();
        urlconnection.setDoOutput(true);
        urlconnection.setDoInput(true);

        if (urlconnection instanceof HttpURLConnection) {
	        try {
		        ((HttpURLConnection)urlconnection).setRequestMethod("PUT");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Connection", "Keep-Alive");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Cache-Control", "no-cache");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Content-type", "text/plain");
		        ((HttpURLConnection)urlconnection).connect();
	        } catch (ProtocolException e) {
	        	e.printStackTrace();
	        }
		}
        BufferedOutputStream out = new BufferedOutputStream(urlconnection.getOutputStream());
        out.flush();
        return out;
	}
	
	/**
	 * 
	 * Description: 根据url获取output
	 * @param
	 * @return OutputStream
	 * @throws IOException 
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年4月5日 下午6:52:15
	 */
	private static OutputStream getOutputStream(String urlStr) throws IOException{
		File f=new File(urlStr);
		FileOutputStream out = new FileOutputStream(f);
		return out;
	}
	
	/**
	 * 
	 * Description: 从图片中获取输入字节流
	 * @param
	 * @return InputStream
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年4月6日 上午11:08:26
	 */
	public static BufferedInputStream getInputStreamFromImage(BufferedImage bi){ 
        InputStream is = null; 
        
        BufferedInputStream in = null;
        bi.flush(); 
         
        ByteArrayOutputStream bs = new ByteArrayOutputStream();  
         
        ImageOutputStream imOut; 
        try { 
            imOut = ImageIO.createImageOutputStream(bs); 
             
            ImageIO.write(bi, "png",imOut); 
             
            is= new ByteArrayInputStream(bs.toByteArray()); 
             
            in = new BufferedInputStream(is);
        } catch (IOException e) { 
            e.printStackTrace(); 
        }  
        return in; 
    } 
	
	/**
	 * 
	 * Description: 进行传输操作
	 * @param
	 * @return void
	 * @throws IOException 
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年4月6日 上午11:18:59
	 */
	private static void doTransfer(String urlStr, BufferedImage bi) throws IOException{
		URLConnection urlconnection=null;
		URL url = new URL(urlStr);
		urlconnection = url.openConnection();
        urlconnection.setDoOutput(true);
        urlconnection.setDoInput(true);

        if (urlconnection instanceof HttpURLConnection) {
	        try {
		        ((HttpURLConnection)urlconnection).setRequestMethod("PUT");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Connection", "Keep-Alive");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Cache-Control", "no-cache");
		        ((HttpURLConnection)urlconnection).setRequestProperty("Content-type", "text/plain");
		        ((HttpURLConnection)urlconnection).connect();
	        } catch (ProtocolException e) {
	        	e.printStackTrace();
	        }
		}
        BufferedOutputStream out = new BufferedOutputStream(urlconnection.getOutputStream());
        out.flush();
        BufferedInputStream in = getInputStreamFromImage(bi);
		try{
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = in.read(buffer)) > -1)
				out.write(buffer,0,len);
        }finally{
        	out.close();
        	in.close();
        }
        try {
        	InputStream inputStream;
        	int responseCode=((HttpURLConnection)urlconnection).getResponseCode();
        	if ((responseCode>= 200) &&(responseCode<=202) ) {
	        } else {
	        	inputStream = ((HttpURLConnection)urlconnection).getErrorStream();
	        }
	        ((HttpURLConnection)urlconnection).disconnect();
        } catch (IOException e) {
        	logger.error("iqb错误信息---向图片服务器中写数据异常---" + e);
        }
	}
	
	/**
	 * 
	 * Description: 获取url文件输入流
	 * @param
	 * @return FileInputStream
	 * @throws IOException 
	 * @throws
	 * @Author wangxinbang
	 * Create Date: 2016年4月6日 上午11:41:34
	 */
	public static InputStream getURLFileInputStream(String urlStr){
		InputStream is = null;
		try {
			URLConnection urlconnection=null;
			URL url = new URL(urlStr);
			urlconnection = url.openConnection();
	        urlconnection.setDoOutput(true);
	        urlconnection.setDoInput(true);
	
	        if (urlconnection instanceof HttpURLConnection) {
		        try {
			        ((HttpURLConnection)urlconnection).setRequestMethod("PUT");
			        ((HttpURLConnection)urlconnection).setRequestProperty("Connection", "Keep-Alive");
			        ((HttpURLConnection)urlconnection).setRequestProperty("Cache-Control", "no-cache");
			        ((HttpURLConnection)urlconnection).setRequestProperty("Content-type", "text/plain");
			        ((HttpURLConnection)urlconnection).connect();
		        } catch (ProtocolException e) {
		        	e.printStackTrace();
		        }
			}
	        is = urlconnection.getInputStream();
        } catch (IOException e) {
        	logger.error("iqb错误信息---从服务器中获取图片信息异常---" + e);
		}
		return is;
	}	
}
