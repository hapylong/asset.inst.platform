require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'base': 'base',
		'formatter': 'formatter',
		'weui': 'weui/js/weui.min',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
    }
});
require(['json2', 'jquery',  'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
		Base.post(Base.url.rootUrl + '/about/getBankCount', {}, function(result){
        	$(".span2").html(result.count + '张');
        	$(".merchant").html(result.regId + ',您好！');
		})
		//银行卡
		$(".myBankCard").click(function(){
	    	window.location.href='bankCardList.html'
	    });
	    //个人信息
	    $(".personalInfo").click(function(){
	    	window.location.href='personalInfo.html'
	    });
	    //重置密码
	    $(".resetPass").click(function(){
	    	window.location.href='resetPass.html'
	    });
	    //退出
	    $(".quit").click(function(){
	    	Base.post(Base.url.rootUrl + '/unIntcpt-req/logout/user', {}, function(result){
	    		window.location.href = '../login/login.html';
	    	})
	    });
	})
})