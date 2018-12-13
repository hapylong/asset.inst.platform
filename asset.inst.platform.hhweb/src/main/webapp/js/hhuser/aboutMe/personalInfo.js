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
		Base.post(Base.url.rootUrl + '/about/getUserInfo', {}, function(result){
			$("#realName").val(result.realName);
			$("#phoneNumber").val(result.regId);
			$("#idCard").val(result.idNo);
		})
	})
})