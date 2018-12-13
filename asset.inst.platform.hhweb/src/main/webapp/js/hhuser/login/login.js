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
    /** 判断传参是否为空 **/
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
    		Base.alertMsg('请输入手机号', null, function(){
    			$(eId).focus();
    		});
    		return false;
    	}
    	if(!Validate.validatePhoneNum($(eId).val())){
    		Base.alertMsg('手机号格式不正确', null, function(){
    			$(eId).focus();
    		});
    		return false;
    	}
    	return true;
    }
    
    /** 验证密码 **/
    function validatePwd(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		Base.alertMsg('请输入密码', null, function(){
    			$(eId).focus();
    		});
    		return false;
    	}
    	if(!Validate.password($(eId).val())){
    		Base.alertMsg('密码格式不正确', null, function(){
    			$(eId).focus();
    		});
    		return false;
    	}
    	return true;
    }
    
    /** 验证表单信息 **/
    function validateForm(){
    	return validatePhone('regId') && validatePwd('passWord');
    }
    
    /** 初始化提交按钮 **/
    function initSubmitBtn(){
    	$('#loginBtn').on('click', function(){
    		if(validateForm()){
    			Base.loadingOn('登录中');
    			submitLoginForm();
    		}
    	});
    }
    
    /** 设置是否自动登录 **/
    function setIsAutoLogin(){
    	var hasChk = $('#checkboxInput').is(':checked');
    	if(hasChk){
    		$('#isAutoLogin').val('1');
    	}else{
    		$('#isAutoLogin').val('0');
    	}
    }
    
    /** 提交登录表单 **/
    function submitLoginForm(){
    	setIsAutoLogin();
    	Base.post(Base.url.rootUrl + '/unIntcpt-req/login/user', $('#loginForm').serializeObject(), function(result){
			Base.loadingOut();
			Base.redirectUrl('../userCenter/memberCenter.html');
		});
    }
    
	/** 页面初始化 **/
    function init(){
        initSubmitBtn();
    }
    init();
	
});

