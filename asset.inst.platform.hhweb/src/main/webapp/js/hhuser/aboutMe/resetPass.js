require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
		'common' : 'common',
		'validate': 'validate'
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
    }
});
require(['json2', 'jquery',  'base', 'formatter', 'common', 'validate'], function(JSON, $, Base, Formatter, Common, Validate){		
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
	
	        /** 判断传参是否为空 * */
		    function isNotNull(val){
		    	if(val == 'undefined' || val == ''){
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
		    
		    /** 验证两次输入的密码是否一致 **/
		    function validateEquals(id1, id2){
		    	var eId1 = '#' + id1;
		    	var eId2 = '#' + id2;
		    	if($(eId1).val() != $(eId2).val()){
		    		Base.alertMsg('两次输入的密码不一致', null, function(){
		    			$(eId1).val('');
		    			$(eId2).val('');
		    			$(eId1).focus();
		    		});
		    		return false;
		    	}
		    	return true;
		    }
		    
		    /** 验证表单信息 **/
		    function validateForm(){
		    	return validatePwd('password-current') && validatePwd('password') && validatePwd('rePassword') && validateEquals('password', 'rePassword');
		    }
		    
		    /** 初始化提交按钮 **/
		    function initSubmitBtn(){
		    	$('.redBtn').on('click', function(){
		    		if(validateForm()){
		    			Base.loadingOn();
		    			submitModifyPwdForm();
		    		}
		    	});
		    }
		    
		    
		    /** 提交登录表单 **/
		    function submitModifyPwdForm(){
		    	Base.post(Base.url.rootUrl + '/unIntcpt-req/resetPwd/user', {'passWord':$("#password-current").val(),'newPassWord':$("#password").val()}, function(result){
					Base.loadingOut();
					/*Base.toast();*/
					Base.alertMsg('密码重置成功！',null,function(){
						Base.redirectUrl('aboutMe.html');
					});
				});
		    }
		    
			/** 页面初始化 **/
		    function init(){
		        initSubmitBtn();
		    }
		    init();
});                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             