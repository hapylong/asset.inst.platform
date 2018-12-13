package com.iqb.asset.inst.platform.front.rest.upload;

import java.util.LinkedHashMap;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iqb.asset.inst.platform.common.base.CommonReturnInfo;
import com.iqb.asset.inst.platform.common.exception.IqbException;
import com.iqb.asset.inst.platform.common.util.upload.impl.FilesUploadService;
import com.iqb.asset.inst.platform.front.base.FrontBaseService;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping("/fileUpload")
public class FilesUploadRest extends FrontBaseService {

	/**
	 * 日志记录器
	 * */
	private static Logger logger = LoggerFactory.getLogger(FilesUploadRest.class);

	/**
	 * 上传文件 Service
	 * */
	@Autowired
	private FilesUploadService filesUploadService;

	
	@ResponseBody
	@RequestMapping(value = {"/upload/{fileType}/{moduleName}"}, method = {RequestMethod.GET, RequestMethod.POST})
	public Map upload(@PathVariable String fileType, @PathVariable String moduleName, @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		try {
			logger.debug("IQB--开始上传文件...");
			String filePath = filesUploadService.saveUploadFile(fileType, moduleName, file);
			logger.info("IQB信息--上传文件完成");
			LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
	        linkedHashMap.put("result", filePath);
			return super.returnSuccessInfo(linkedHashMap);
		} catch (Exception e) {//系统发生异常
			logger.error("IQB错误信息：" + CommonReturnInfo.BASE00000001.getRetFactInfo(), e);
			return super.returnFailtrueInfo(new IqbException(CommonReturnInfo.BASE00000001));
		}
	}

}
