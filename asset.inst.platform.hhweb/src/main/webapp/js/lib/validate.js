/**
 * 主要用于定义校验类函数,如校验手机号是否正确,相关函数均可在此定义
 * 引入:jQuery库
 * */
define(['jquery'], function($){
	
	var _this = {
		/** 校验手机号格式  **/
		validatePhoneNum : function(val){
		    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
		    if(!myreg.test(val)){
		    	return false;
		    }
		    return true;
		},
		/** 校验密码 **/
		password : function(val){
			var myreg = /(^[A-Za-z0-9]+$)/;
			if(val.length < 6 || val.length > 10){
				return false; 
			}else if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 校验短信验证码 **/
		smsCode : function(val){
			var myreg = /(^[0-9]+$)/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 校验金额-保留两位小数 **/
		validateAmt: function(val){
			var myreg = /^([1-9][\d]{0,20})(\.[\d]{1,2})?$/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 校验金额-保留两位小数(区分validateAmt可以为0) **/
		validateAmtZero: function(val){
			var myreg = /^([1-9][\d]{0,20})(\.[\d]{1,2})?$/;
			if((!myreg.test(val)) && val != 0){
		    	return false;
		    }
			return true;
		},
		/** 判断输入的参数是否是个合格的固定电话号码 **/
		validateTelePhone: function(val){
			var myreg = /^\d{3,4}\d{7,8}$/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 判断输入的参数是否是个合格的手机号码,不能判断号码的有效性,有效性可以通过运营商确定 **/
		validateMobilePhone: function(val){
			var myreg = /^(13[0-9]|14[57]|15[012356789]|17[0678]|18[0-9])\d{8}$/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 校验文件格式 **/
		validateFileType: function(val){
			var myreg = /image\/\w+/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		},
		/** 校验文件大小(前端上传限制为2M) **/
		validateFileSize: function(val){
			return val <= 1024 * 1024 * 5;
		}
	};
	
	return _this;
});

