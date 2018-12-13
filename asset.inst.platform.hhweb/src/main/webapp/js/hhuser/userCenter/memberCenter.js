require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'wx': 'jweixin-1.0.0',
		'jquery': 'jquery-1.11.3.min',		
		'base': 'base',
		'formatter': 'formatter',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2','jquery', 'base', 'formatter', 'wx'], function(JSON, $, Base, Formatter, wx){
	$(function(){
		
		//用于缓存页面变量(0:无风控	 1:第一步	2:第二步	3:第三步	4:完成)
		var cache = {riskInfoStep: 0};
		
		//调用查询风控接口
		Base.post(Base.url.rootUrl + '/2/queryRiskInfo', {}, function(result){
			$('.userInfo p').html(result.regId + '，您好！');			
			if(result.riskInfo){
				if(!result.riskInfo.checkInfo){
					//调用查询分控步骤接口
					Base.post(Base.url.rootUrl + '/getRiskInfoStep', {}, function(resultInfo){
						resultInfo = resultInfo || 1;
						cache.riskInfoStep = parseInt(resultInfo);
					})
				}else{
					cache.riskInfoStep = 4;
				}
			}
		});
		
		//调用查询是否开户接口
		Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
			if(result != true){
         		window.location.href = 'bindCard.html';
        	}
		});
		 
		//单击医美
		$('#yiMei').click(function(){
			switch(cache.riskInfoStep){
			case 0:
            	window.location.href = 'creditCtrStep1.html';
            	break;
			case 1:
            	window.location.href = 'creditCtrStep1.html';
            	break;
			case 2:
            	window.location.href = 'creditCtrStep2.html';
            	break;
			case 3:
            	window.location.href = 'creditCtrStep3.html';
            	break;
            case 4:
            	window.location.href = '../stageApplication/stageApplication.html';
            	break;
			}
		});
		
		//单击信用管理
		$('#credit').click(function(){
			switch(cache.riskInfoStep){
			case 0:
            	window.location.href = 'creditCtrStep1.html';
            	break;
			case 1:
            	window.location.href = 'creditCtrStep1.html';
            	break;
			case 2:
            	window.location.href = 'creditCtrStep2.html';
            	break;
			case 3:
            	window.location.href = 'creditCtrStep3.html';
            	break;
            case 4:
            	window.location.href = 'creditCtrStep4.html';
            	break;
			}
		});
		
		//点击租房
		$('#house').click(function(){
			window.location.href = '../stageApplication/housingApplication.html';
		});
		//点击关于我
		$('#aboutMe').click(function(){
			window.location.href = '../aboutMe/aboutMe.html';
		});		
		//点击我的订单
		$('#stageOrder').click(function(){
			window.location.href = '../myOrder/myOrder.html';
		});
		//点击我的账单
		$('#myBill').click(function(){
			window.location.href = '../myBill/myBill.html';
		});
		//点击我要还款
		$('#repayment').click(function(){
			window.location.href = '../toPay/toPay.html';
		});
	})
});