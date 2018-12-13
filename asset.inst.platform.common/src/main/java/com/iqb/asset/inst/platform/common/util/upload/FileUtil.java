package com.iqb.asset.inst.platform.common.util.upload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.iqb.asset.inst.platform.common.base.config.BaseConfig;
@Component
public class FileUtil {
	
	@Resource(name="baseConfig")
	private BaseConfig config;		
	
	private String DOC = "doc";
	private String PIC = "pic";	
	
	private String baseDir = DOC;
	
	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };
	
	
	
	public String generateFilePath(String fileType, String moduleName, String fileName) {
		setBaseDir(fileType);
		String filePath = config.getUploadBaseDir() + File.separator + baseDir + File.separator + moduleName;
		Date date = new Date(); 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	    filePath = filePath + File.separator + sdf.format(date);
	    SimpleDateFormat sdf2 = new SimpleDateFormat("dd"); 
	    filePath = filePath + File.separator + sdf2.format(date);
	    if(fileName != null && !fileName.trim().equals("")) {
	    	filePath = filePath + File.separator + generateShortUuid() + fileName;
	    }
	    File folder = new File(filePath);
	    if(!folder.exists()) {
	    	folder.mkdirs();
		}
		return filePath;
	}
	
	
	
	private String generateShortUuid() {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        String str = uuid.substring(i * 4, i * 4 + 4);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  	  
	}  
	
	public void setBaseDir(String baseDir) {
		if(baseDir == null || baseDir.equals(DOC)){
			this.baseDir = DOC;
		} else {
			this.baseDir = PIC;
		}
	} 
	
	public String substring(String filePath) {
		return filePath.substring(config.getUploadBaseDir().length());
	}
	
	public String combine(String filePath) {
		return config.getUploadBaseDir() + filePath;
	}
	
	
}
