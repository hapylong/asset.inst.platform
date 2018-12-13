require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
		'common': 'common',
		'validate': 'validate'
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base', 'formatter', 'common', 'validate'], function($, Base, Formatter, Common, Validate){
	//
	$('.close').click(function(){
        $(this).siblings('.login-input').val('');
        $(this).hide();
    });
	$('.eyeIcon').click(function(){
	    if ($(this).hasClass('eye-close')) {
	        $(this).siblings('.login-pwd').prop('type','text');
	        $(this).removeClass('eye-close').addClass('eye-open');
	    }else if ($(this).hasClass('eye-open')){
	        $(this).siblings('.login-pwd').prop('type','password');
	        $(this).removeClass('eye-open').addClass('eye-close');
	    }
	});
	$('.login-input').keyup(function(){
        if ($(this).val()!='') {
            $(this).siblings('.close').show();
        }else if($(this).val()==''){
            $(this).siblings('.close').hide();
        }
        clearIpt();
    });
	//倒计时
	var tim =60; // 剩余时间
	function tim_Down(){
		if (tim>0) {
			$('.sendTim').show().html(tim+'s后');
				tim--;
				setTimeout("tim_Down()", 1000);
		}
		else if (tim<=0) {
			$('.sendTim').hide();
			$('.sendMsg').addClass('send-able').removeClass('send-unable');
			$('.voiceCode').addClass('voice-able').removeClass('voice-unable');
			tim=60;
		}
	}
	
	/** 数字验证码 **/
	function alerts() {
		var widths = document.documentElement.clientWidth;
		$("#mask").show();
		$(".maskcontent").css('left', (widths - 250) / 2).show();
	}
	
	/** 隐藏数字验证码弹框 **/
	function hideNumVerTab(){
		$("#mask").hide();
		$(".maskcontent").hide();
	}

    /** 判断传参是否为空 * */
    function isNotNull(val){
    	if(val == 'undefined' || val == ''){
    		return false;
    	}
    	return true;
    }
    
    /** 验证手机号 **/
    function validatePhone(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		$(eId).focus();
    		Base.alertMsg('请输入手机号', null);
    		return false;
    	}
    	if(!Validate.validatePhoneNum($(eId).val())){
    		Base.alertMsg('手机号格式不正确', null);
    		return false;
    	}
    	return true;
    }
    
    /** 验证注册号是否已经存在 **/
    function isRegIdExit(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		$(eId).focus();
    		Base.alertMsg('请输入手机号', null);
    		return 'f';
    	}
    	var flag = 'f';
    	Base.asyncPost(Base.url.rootUrl + '/unIntcpt-req/isRegIdExit', $('#regForm').serializeObject(), function(result){
    		if(result == 1){
    			Base.alertMsg('该手机号已注册', null);
    			flag = 't';
    			return flag;
    		}
    		flag = 'f';
    		return flag;
		});
    	return flag;
    }
    
    /** 验证短信验证码 **/
    function validateSmsCode(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		$(eId).focus();
    		Base.alertMsg('请输入短信验证码', null);
    		return false;
    	}
    	return true;
    }

    /** 验证密码 **/
    function validatePwd(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		$(eId).focus();
    		return false;
    	}
    	if(!Validate.password($(eId).val())){
    		Base.alertMsg('密码格式不正确', null);
    		return false;
    	}
    	return true;
    }
    
    /** 验证两次输入的密码是否一致 **/
    function validateEquals(id1, id2){
    	var eId1 = '#' + id1;
    	var eId2 = '#' + id2;
    	if($(eId1).val() != $(eId2).val()){
    		Base.alertMsg('两次输入的密码不一致', null);
    		return false;
    	}
    	return true;
    }
    
    /** 验证表单信息 **/
    function validateForm(){
    	return validatePhone('regId') && validateSmsCode('verificationCode') &&  validatePwd('password') && validatePwd('rePassword') && validateEquals('password', 'rePassword');
    }
    
    /** 初始化提交按钮 **/
    function initSubmitBtn(){
    	$('#regBtn').on('click', function(){
    		if($("#checkboxInput").prop('checked')){
				if(validateForm()){
					Base.loadingOn();
	    			submitRegForm();
				}
			}else{
				Base.alertMsg('您尚未同意注册协议！', null);
			}
    	});
    }
    
    /** 初始化获取验证码按钮 **/
    function initGetSmsBtn(){
    	$('#smsSendBtn').on('click', function(){
    		changNumCode();
    	});
    }
    
    /** 初始化数字验证码操作按钮 **/
    function initSmsTabBtn(){
    	/** 数字验证码关闭按钮 **/
    	$('.qd').click(function() {
    		Base.loadingOn();
    		//帮帮手短信通道‘2’
    		Base.post(Base.url.rootUrl + '/unIntcpt-req/11/verifyImageVerify/user', $('#regForm').serializeObject(), function(result){
    			Base.loadingOut();
    			hideNumVerTab();
    			//短信倒计时
    			if ($('.sendMsg').hasClass('send-able')) {
					$('.sendMsg').addClass('send-unable').removeClass('send-able');
					$('.voiceCode').addClass('voice-unable').removeClass('voice-able');
					$('.sendDet').css("color","white");
					$('.sendDet').html('重新发送');
					$('.sendTim').show().html('60');
					tim_Down();
				}else{
					$(".sendDet").val("重新发送"); 
				}
    		});
    	});
    	
    	/** 数字验证码确认按钮 **/
    	$('.qx').click(function() {
    		hideNumVerTab();
    	});

    }
    
    /** 提交登录表单 **/
    function submitRegForm(){
    	Base.post(Base.url.rootUrl + '/unIntcpt-req/11/regist/user', $('#regForm').serializeObject(), function(result){
			Base.loadingOut();
			Base.redirectUrl('../userCenter/memberCenter.html');
		});
    }

    /** 初始化数字验证码点击事件 **/
    function initNumCodeTouch(){
    	/** 数字验证码关闭按钮 **/
    	$('#numCode').click(function() {
    		changNumCode();
    	});
    }
    
    /** 改变数字验证码的值 **/
    function changNumCode(){
    	/** 验证手机号格式 **/
		if(!validatePhone('regId')){
			return false;
		}
		var flag = isRegIdExit('regId');
		/** 判断手机号是否已经注册 **/
		if(flag == 'f'){
			/** 清空数字验证码输入框 **/
			$('#imageVerifyCode').val('');
			/** 设置数字验证码的src **/
			var srcUrl = Base.url.rootUrl +'/unIntcpt-req/getImageVerify/user?regId=' + $('#regId').val() + '&' + Math.random();
			$("#numCode").attr("src", srcUrl); 
			/** 数字验证码弹框 **/
			alerts();
		}
    }
    
	/** 页面初始化 **/
    function init(){
    	initSmsTabBtn();
    	initGetSmsBtn();
    	initNumCodeTouch();
        initSubmitBtn();
    }
    init();
	
});