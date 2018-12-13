require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base',
		'formatter': 'formatter',
		'validate': 'validate'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base', 'formatter', 'weui', 'validate'], function($, Base, Formatter, weui, Validate){
	//调用查询是否开户接口
	Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
		if(!result){
     		window.location.href = '../userCenter/bindCard.html';
    	}else{
    		//调用查询是否风控接口
    		Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
    			if(!result.riskInfo){
    				window.location.href = '../userCenter/riskInfoAdd.html?regId=' + result.regId;
                }
    		});
    	}
	});
	
	
	$(function(){
		
		//查询页面入参
		function getQueryString(name){
			var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		};
		
		//缓存
		sessionStorage.setItem('qr_order_url', window.location.href);
		
		Base.post(Base.url.rootUrl + '/getQrOrderInfoById ', {id: getQueryString('id')}, function(result){
			$('#merchantShortName').val(result.merchantShortName);
			$('#orderName').val(result.projectName + result.projectDetail);
			$('#installAmount').val(result.installAmount);
			$('#installPeriods').val(result.installPeriods);
			
			$('#downPayment').val(result.downPayment);
			$('#monthAmount').val(result.monthInterest);
			$('#margin').val(result.margin);
			$('#serviceFee').val(result.serviceFee);
			$('#fee').val(result.fee);		
		});
		
		//提交-绑定单击事件回调
		$('#btn-apply').on('click', function(e){
			//调用查询是否开户接口
			document.getElementById("btn-apply").disabled=true;
			document.getElementById("btn-apply").style.background = "gray";
			Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
				if(!result){
		     		window.location.href = '../userCenter/bindCard.html';
		    	}else{
		    		//调用查询是否风控接口
		    		Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
		    			if(!result.riskInfo){
		    				window.location.href = '../userCenter/riskInfoAdd.html?regId=' + result.regId;
		                }else{
		                	//等待框开启
		        			Base.loadingOn();
		        			Base.post(Base.url.rootUrl + '/2001/generateOrder', {id: getQueryString('id')}, function(result){
		        				//等待框关闭
		        				//Base.loadingOut();
		        				window.location.href = Base.url.webUrl + '/views/user/buyCar/orderSucc.html';
		        			});
		                }
		    		});
		    	}
			});
		});
		
	});
	
});