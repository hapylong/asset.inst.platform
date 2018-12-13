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
		//查询页面入参
		function getQueryString(name){
			var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		};
		
		//调用查询二维码地址接口
		Base.post(Base.url.rootUrl + '/merchant/queryQrCodeUrl', {id: getQueryString('id')}, function(result){
			$('.page-title').append(result.carType);
			$('.qrCode').find('img').prop('src', Base.url.imgUrl + result.imgName);
			$('.p2').text(result.planFullName);
		});	
	});
});