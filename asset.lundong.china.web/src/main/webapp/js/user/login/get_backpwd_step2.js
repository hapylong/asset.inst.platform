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
	$('.eyeIcon').click(function(){
	    if ($(this).hasClass('eye-close')) {
	        $(this).siblings('.login-pwd').prop('type','text');
	        $(this).removeClass('eye-close').addClass('eye-open');
	    }else if ($(this).hasClass('eye-open')){
	        $(this).siblings('.login-pwd').prop('type','password');
	        $(this).removeClass('eye-open').addClass('eye-close');
	    }
	});
	/** 初始化参数 **/
	function initNParam(){
		var thisURL = document.URL;   
		var getval =thisURL.split('?')[1];  
		if(getval != '' && getval != undefined){
			$('#regId').val(getval.split('&')[0]);
			$('#verificationCode').val(getval.split('&')[1].replace('#',''));
		}
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
    	return validatePwd('password') && validatePwd('rePassword') && validateEquals('password', 'rePassword');
    }
    
    /** 初始化提交按钮 **/
    function initSubmitBtn(){
    	$('#pwdModifyBtn').on('click', function(){
    		if(validateForm()){
    			Base.loadingOn();
    			submitModifyPwdForm();
    		}
    	});
    }
    
    
    /** 提交登录表单 **/
    function submitModifyPwdForm(){
    	Base.post(Base.url.rootUrl + '/unIntcpt-req/forgetPwdDoModify/user', $('#subResetForm').serializeObject(), function(result){
			Base.loadingOut();
			Base.toast();
			Base.redirectUrl('../login/login.html');
		});
    }
    
	/** 页面初始化 **/
    function init(){
    	initNParam();
        initSubmitBtn();
    }
    init();
	
});

