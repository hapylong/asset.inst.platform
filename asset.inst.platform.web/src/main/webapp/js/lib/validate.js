/**
 * 主要用于定义校验类函数,如校验手机号是否正确,相关函数均可在此定义
 * 引入:jQuery库
 * */
define(['jquery'], function($){
	
	var _this = {
		/** 校验手机号格式  **/
		validatePhoneNum : function(val){
		    var myreg = /(^[0-9]+$)/;
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
		/** 校验金额-保留两位小数(商户-车辆总价) **/
		validateAmt: function(val){
			var myreg = /^([1-9][\d]{0,7})(\.[\d]{1,2})?$/;
			if(!myreg.test(val)){
		    	return false;
		    }
			return true;
		}

	};
	
	return _this;
});

