/**
 * Created by changxuejian on 2017/10/27.
 */
require.config({
	baseUrl: '../../js/lib',
	paths: {
		'cons': 'cons',
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
require(['jquery', 'base', 'formatter'], function($, Base, Formatter){
	$(function(){
		 $('.btn').click(function(){
	    	url = Base.url.webUrl + '/activity/Christmas/choose.html';
	    	window.location.href = url;
		});
	})
})