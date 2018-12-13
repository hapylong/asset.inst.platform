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
		//调用查询当前商户详情接口
		Base.post(Base.url.rootUrl + '/about/getmerchantInfo', {}, function(result){
			$('#merchantNo').text(result.merchantShortName);
			//缓存商户简称,商户号到浏览器
			sessionStorage.setItem('merchantShortName', result.merchantShortName);
			sessionStorage.setItem('merchantNo', result.merchantNo);
		});			
		//生成新车二维码-绑定单击事件回调
		$('#toQr').on('click', function(){
			window.location.href = Base.url.webUrl + '/views/merchant/merchantCenter/makeQrOrder.html';
		});
		//订单查询-绑定单击事件回调
		$('#orderList').on('click', function(){
			window.location.href = Base.url.webUrl + '/views/merchant/order/underReview.html';
		});
		//关于我-绑定单击事件回调
		$('#aboutMe').on('click', function(){
			window.location.href = Base.url.webUrl + '/views/merchant/merchantCenter/aboutMe.html?version=1.1';
		});
	});
});

