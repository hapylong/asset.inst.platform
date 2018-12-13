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
		var rander;
	    rander = localStorage.getItem("rander");
	    if(rander != '' && rander != undefined && rander != null){
	        switch (Number(rander)){
	            case 1:
	                $('.change').attr('src','img/tippler.png');
	                break;
	            case 2:
	                $('.change').attr('src','img/troublemaker.png');
	                break;
	            case 3:
	                $('.change').attr('src','img/cry.png');
	                break;
	            case 4:
	                $('.change').attr('src','img/fl.png');
	                break;
	            case 5:
	                $('.change').attr('src','img/timid.png');
	                break;
	            default:break
	        }
	    }else{
	        rander = parseInt(Math.random()*(5-1+1)+1);
	        localStorage.setItem("rander",rander);
	        switch (Number(rander)){
	            case 1:
	                $('.change').attr('src','img/tippler.png');
	                break;
	            case 2:
	                $('.change').attr('src','img/troublemaker.png');
	                break;
	            case 3:
	                $('.change').attr('src','img/cry.png');
	                break;
	            case 4:
	                $('.change').attr('src','img/fl.png');
	                break;
	            case 5:
	                $('.change').attr('src','img/timid.png');
	                break;
	            default:break
	        }
	    }
	})
})