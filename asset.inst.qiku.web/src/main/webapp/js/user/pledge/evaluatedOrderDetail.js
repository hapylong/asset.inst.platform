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
	$(function(){
		//缓存页面变量
		var cache = {isQuery: false, isRisk: false, isQueryBindCard: false, isBindCard: false};	
		
		//调用查询是否风控接口
		Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
			cache.regId = result.regId;
			cache.isQuery = true;
			if(result.riskInfo){
				cache.isRisk = true;
            }
		});
		//调用查询是否鉴权接口
		Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
			cache.isQueryBindCard = true;
			if(result){
				cache.isBindCard = true;
            }
		});
		/*获取订单id及相关信息*/
		var id = location.search.match(new RegExp("[\?\&]id=([^\&]+)","i"))[1];	
		Base.post(Base.url.rootUrl + '/pledge/order_details', {'orderId':id}, function(result){
			//赋值
			$('#merchantShortName').val(result.merchantShortName);
			//拆分
			var orderName = result.orderName;
			var orderNames = orderName.split('-');
			$('#projectName').val(orderNames[0]);
			$('#typeConfigure').val(orderNames[1]);
			$('#licenseNumber').val(result.plate);
		    var carType = result.carType;
		    if(carType == 1){
		    	$('#carType').val('二手车');
		    }else if(carType == 2){
		    	$('#carType').val('新车');
		    }
			$('#VIN').val(result.carNo);
			$('#engineNumber').val(result.engine);
			$('#firstDate').val(Formatter.timeformatter3(result.registDate));
			$('#licenseLocation').val(result.registAdd);
			$('#kiloNum').val(result.mileage);
			$('#creditLine').val(Formatter.moneyTenThousand(result.applyAmt));
		});
		//点击我要融资(绑卡和风控)
		$('#toFinance').click(function(e){
			var url = "toFinance.html?id="+id;
			//绑卡
			if(cache.isQueryBindCard){
				if(cache.isBindCard){
					//风控
					if(cache.isQuery){
						if(cache.isRisk){
					    	window.location.href=url;					
						}else{
							window.location.href = '../userCenter/riskInfoAdd.html?regId=' + cache.regId;
						}
					}else if(!cache.isQuery){
						//调用查询是否风控接口
						Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
							if(result.riskInfo){
						    	window.location.href=url;
				            }else{
				            	window.location.href = '../userCenter/riskInfoAdd.html?regId=' + cache.regId;
				            }
						});
					}					
				}else{
					window.location.href = '../userCenter/bindCard.html';
				}
			}else if(!cache.isQueryBindCard){
				//调用查询是否开户接口
				Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
					if(result){
						//风控
						if(cache.isQuery){
							if(cache.isRisk){
								window.location.href=url;				
							}else{
								window.location.href = '../userCenter/riskInfoAdd.html?regId=' + cache.regId;
							}
						}else if(!cache.isQuery){
							//调用查询是否风控接口
							Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
								if(result.riskInfo){
									window.location.href=url; 
					            }else{
					            	window.location.href = '../userCenter/riskInfoAdd.html?regId=' + cache.regId;
					            }
							});
						} 
		            }else{
		            	window.location.href = '../userCenter/bindCard.html';
		            }
				});
			}		
		});
		//取消订单
		$('#cancelOrder').click(function(){
			Base.loadingOn();
			Base.post(Base.url.rootUrl + '/pledge/refund_order', {'orderId':id}, function(result){
				if(result == 'true'){
					window.location.href='enquiryOrders.html';
				}
			})
		});
	});
	
});