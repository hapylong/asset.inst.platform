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
require(['json2', 'jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
	    //点击再试一次
		$(".redBtn").click(function(){
			url = Base.url.rootUrl + '/pay/repayAgain';
        	window.location.href = url;
		});
		$(".cancel").click(function(){
			url = Base.url.hwebUrl + '/views/user/userCenter/memberCenter.html';
        	window.location.href = url;
		});
	})
})