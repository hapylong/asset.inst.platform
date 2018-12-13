require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base'], function($, Base){
	$(function(){
		//从缓存中取商户简称,商户号
		var merchantShortName = sessionStorage.getItem('merchantShortName');
		var merchantNo = sessionStorage.getItem('merchantNo');
		
		$('.merchant').text(merchantShortName);
		$('.main').find('.span2').first().text(merchantShortName);
		$('.main').find('.span2').last().text(merchantNo);
		
		//重置密码-绑定单击事件回调
		$('.resetPass').on('click', function(){
			window.location.href = Base.url.webUrl + '/views/merchant/merchantCenter/carResetPwd.html';
		});
		
		//退出-绑定单击事件回调
		$('.quit').on('click', function(){
			//调用退出接口
			Base.post(Base.url.rootUrl + '/unIntcpt-req/logout/merchant', {}, function(result){
				//返回登录页
				window.location.href = Base.url.webUrl + '/views/merchant/login/login.html';
			});
		});
	});
});
