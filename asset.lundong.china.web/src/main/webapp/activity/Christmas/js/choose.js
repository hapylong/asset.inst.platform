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
		 var url = Base.url.webUrl + '/activity/Christmas/show.html';
	     var data;
		 $('.chooseParent').click(function(){
			 data = '?choose=parent';
			 open();
		 });
		 $('.chooseLover').click(function(){
			 data = '?choose=lover';
			 open();
		 });
		 $('.chooseChild').click(function(){
			 data = '?choose=child';
			 open();
		 });
		 $('.chooseFriend').click(function(){
			 data = '?choose=friend';
			 open();
		 });
		 $('.chooseSelf').click(function(){
			 data = '?choose=self';
			 open();
		 });
	    function open(){
	    	url = url + data;
			window.location.href = url;
	    }
	})
})