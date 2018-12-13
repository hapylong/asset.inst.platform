package com.iqb.asset.inst.platform.common.util.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;


public interface IFilesUploadService {

	/**
     * 保存上传文件
     * @param fileType
     * @param moduleName
     * @param file
     * @throws Exception
     * @author baiyapeng
     */
	String saveUploadFile(String fileType, String moduleName, MultipartFile file) throws Exception; 
	
	/**
     * 保存上传文件
     * @param fileType
     * @param moduleName
     * @param file
     * @throws Exception
     * @author baiyapeng
     */
	String[] saveMultiUploadFile(String fileType, String moduleName, MultipartFile[] files) throws Exception; 
	
	/**
     * 删除已上传文件
     * @param filePath
     * @throws Exception
     * @author baiyapeng
     */
	String removeUploadedFile(String filePath) throws Exception; 
	
	/**
     * 下载已上传文件
     * @param filePath
     * @throws Exception
     * @author baiyapeng
     */
	void downloadUploadedFile(String fileName, String  filePath, HttpServletRequest request, HttpServletResponse response) throws Exception; 
}
