/**
 * @Copyright (c) www.iqb.com  All rights reserved.
 * @Description: TODO
 * @date 2016年12月1日 下午2:46:44
 * @version V1.0 
 */
package com.iqb.asset.inst.platform.front.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 购车服务
 * 
 * @author <a href="zhuyaoming@aliyun.com">yeoman</a>
 */
@RestController
public class BuyCarRestController {

	/**
	 * 购车前判断用户是否填写风控信息
	 * @param objs
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validation/{type}", method = { RequestMethod.POST })
	public Object validation(@RequestBody JSONObject objs, @PathVariable("type") String type, HttpServletRequest request) {
		//验证风控是否填写
		return null;
	}
}
