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
		'wx': 'jweixin-1.0.0',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
    }
});
require(['jquery', 'base', 'formatter', 'wx'], function($, Base, Formatter, wx){
	$(function(){
		var choose = location.search.match(new RegExp("[\?\&]choose=([^\&]+)","i"))[1];
	    switch (String(choose)){
	        case 'parent':
	        	showImg('parent');
	        	break;
	        case 'lover':
	        	showImg('lover');
	        	break;
	        case 'child':
	        	showImg('child');
	        	break;
	        case 'friend':
	        	showImg('friend');
	        	break;
	        case 'self':
	        	showImg('self');
	        	break;
	        default:break
	    }
	    //我也试试
	    $('.btn1').click(function(){
	    	window.location.href = Base.url.webUrl + '/activity/Christmas/index.html';
	    });
	    function selectfrom (lowValue,highValue){
	    	var choice=highValue-lowValue+1;
	    	return Math.floor(Math.random()*choice+lowValue);
	    }
	    function showImg(object){
	    	var rander;
    	    rander = localStorage.getItem(object);
    	    if(rander != '' && rander != undefined && rander != null){
    	    	switch (Number(rander)){
	            case 1:
	            	$('.change').attr('src','img/'+object+'/1.png');
	            	break;	
	            case 2:
	            	$('.change').attr('src','img/'+object+'/2.png');
	            	break;	
	            case 3:
	            	$('.change').attr('src','img/'+object+'/3.png');
	            	break;	
	            default:break
    	    	}
    	    }else{
    	    	rander = parseInt(selectfrom(1,3));
    	        localStorage.setItem(object,rander);
    	        switch (Number(rander)){
	            case 1:
	            	$('.change').attr('src','img/'+object+'/1.png');
	            	break;	
	            case 2:
	            	$('.change').attr('src','img/'+object+'/2.png');
	            	break;	
	            case 3:
	            	$('.change').attr('src','img/'+object+'/3.png');
	            	break;	
	            default:break
    	    	}
    	    }
	    }
	})
})