package com.iqb.asset.inst.platform.common.util.upload.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iqb.asset.inst.platform.common.base.service.BaseService;
import com.iqb.asset.inst.platform.common.util.upload.FileUtil;
import com.iqb.asset.inst.platform.common.util.upload.IFilesUploadService;

@Service
public class FilesUploadService extends BaseService implements IFilesUploadService {

	@Autowired
	private FileUtil fileUtil;
	
	@Override
	public String saveUploadFile(String fileType, String moduleName, MultipartFile file) throws Exception {	
		String filePath = fileUtil.generateFilePath(fileType, moduleName, file.getOriginalFilename());
		file.transferTo(new File(filePath));
		return fileUtil.substring(filePath);
	}

	@Override
	public String[] saveMultiUploadFile(String fileType, String moduleName,
			MultipartFile[] files) throws Exception {
		List<String> list = new ArrayList<String>();
		int len = files.length;
		for(int i = 0; i < len; i ++){
			String filePath = fileUtil.generateFilePath(fileType, moduleName, files[i].getOriginalFilename());
			files[i].transferTo(new File(filePath));
			list.add(fileUtil.substring(filePath));
		}
		return list.toArray(new String[list.size()]);
	}	

	
	@Override
	public String removeUploadedFile(String filePath) throws Exception {
		File removeFile = new File(fileUtil.combine(filePath));
		if(removeFile.exists()) {
			removeFile.delete();
		}
		return filePath;
	}

	@SuppressWarnings("resource")
	@Override
	public void downloadUploadedFile(String fileName, String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		File downloadFile = new File(fileUtil.combine(filePath));

	    response.setContentType("application/x-msdownload");
	    response.setContentLength((int)downloadFile.length());
	    response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));       

	    //读出文件到i/o流
	    FileInputStream fis = new FileInputStream(downloadFile);
	    BufferedInputStream buff = new BufferedInputStream(fis);
	    
	    OutputStream os = response.getOutputStream();	

	    byte [] b = new byte[1024 * 10];//相当于我们的缓存
	    long k = 0;//该值用于计算当前实际下载了多少字节	
	    
	    while(k < downloadFile.length()){//开始循环下载
	    	int j = buff.read(b, 0, 1024 * 10);
	        os.write(b, 0, j);//将b中的数据写到客户端的内存
	        k = k + j;
	    }
	    
	    //将写入到客户端的内存的数据,刷新到磁盘
	    os.flush();
	}

}
