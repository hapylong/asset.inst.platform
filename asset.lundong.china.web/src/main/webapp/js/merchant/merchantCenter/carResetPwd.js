require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base',
		'formatter': 'formatter',
		'validate': 'validate'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base', 'formatter', 'validate'], function($, Base, Formatter, Validate){
	
	$(function(){
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
		//确认保存密码-绑定单击事件回调
		$('.redBtn').on('click', function(e){
			var passwordCurrent = $.trim($('#password-current').val());
			if(passwordCurrent.length == 0){	
				Base.alertMsg('当前密码不能为空！', null, function(){
					$('#password-current').focus();
	    		});
				return false;
			}
			var passwordReset = $.trim($('#password-reset').val());
			if(passwordReset.length == 0){
				Base.alertMsg('密码不能为空！', null, function(){
					$('#password-reset').focus();
	    		});
				return false;
			}
			if(!Validate.password(passwordReset)){
				Base.alertMsg('密码格式错误！');
				return false;
			}
			var passwordConfirm = $.trim($('#password-confirm').val());
			if(passwordConfirm.length == 0 || passwordConfirm != passwordReset){
				Base.alertMsg('两次密码输入不一致！');
				$('#password-confirm').focus();
				return false;
			}
			//等待框开启
			Base.loadingOn();
			//调用重置密码接口
			Base.post(Base.url.rootUrl + '/unIntcpt-req/resetPwd/merchant', {password: passwordCurrent, newPassword: passwordReset}, function(result){
				//等待框关闭
				//Base.loadingOut();
				/*window.location.href = Base.url.webUrl + '/views/merchant/login/login.html';*/
				Base.alertMsg('密码重置成功！',null,function(){
					Base.redirectUrl('aboutMe.html');
				});
			});	
		});
	});	
});