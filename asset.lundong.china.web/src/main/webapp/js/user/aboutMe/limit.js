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
		Base.get(Base.url.rootUrl + '/about/lookBankList', {}, function(result){
			var limitTable = "";
			for(var i=0;i<result.length;i++){
				limitTable += "<tr><td>"+result[i].bankName+"</td><td>"+Formatter.money(result[i].singleLimit)+"</td><td>"+Formatter.money(result[i].dayLimit)+"</td></tr>";
			}
			$("table").append(limitTable);
		})
	})
})