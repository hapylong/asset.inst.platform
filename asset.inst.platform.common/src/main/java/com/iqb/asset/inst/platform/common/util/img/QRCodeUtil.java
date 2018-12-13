package com.iqb.asset.inst.platform.common.util.img;

import java.io.File;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 
 * Description:二维码工具类
 * 
 * @author iqb
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2015年12月25日    wangxinbang     1.0        1.0 Version
 * </pre>
 */
@SuppressWarnings("deprecation")
public class QRCodeUtil {

	/**
	 * 
	 * Description:生成二维码
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author wangxinbang Create Date: 2015年12月25日 上午10:37:09
	 */
    public static String encode(String path, String url, int height, int width) {
		try {
			BitMatrix byteMatrix;

			byteMatrix = new MultiFormatWriter().encode(new String(url.getBytes("GBK"), "iso-8859-1"),
					BarcodeFormat.QR_CODE, 200, 200);

			File file = new File(path);
			MatrixToImageWriter.writeToFile(byteMatrix, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
