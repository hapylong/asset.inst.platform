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
	
	//调用查询注册信息是否完善
	Base.get(Base.url.rootUrl + '/pledge/is_account_exist', {}, function(result){
		if(result == 'false'){
     		window.location.href = 'improveReg.html';
    	}
	});
	
	$(function(){
		
		//缓存页面变量
		var cache = {isQuery: false, isRisk: false, isQueryBindCard: false, isBindCard: false};	
		
		//调用查询是否风控接口
		Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
			cache.regId = result.regId;
			$('.t-font li').html('尊敬的'+ cache.regId + '用户，您好！');
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
		//调用请求微信JSSDK权限接口
		Base.post(Base.url.rootUrl + '/4/getBBSJSConfig', {reqUrl: window.location.href}, function(result){
			//配置微信JSSDK
			wx.config({
			      debug: false, 
			      appId: result.appId, 
			      timestamp: result.timestamp, 
			      nonceStr: result.nonceStr, 
			      signature: result.signature,
			      jsApiList: ['scanQRCode'] 
			});
		});	
		
		//配置微信JSSDK成功回调
		wx.ready(function(){
			//新车-绑定单击事件回调微信扫一扫
			$('#buyNewCar').on('click', function(){
				//绑卡
				if(cache.isQueryBindCard){
					if(cache.isBindCard){
						//风控
						if(cache.isQuery){
							if(cache.isRisk){
								//
								wx.scanQRCode({
					                needResult: 0,//扫描结果由微信处理
					                scanType: ['qrCode'],//只能处理二维码 
					                success: function(res){}//扫描成功回调(needResult为1时有效)
					            });
							}else{
								window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
							}
						}else if(!cache.isQuery){
							//调用查询是否风控接口
							Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
								if(result.riskInfo){
									wx.scanQRCode({
						                needResult: 0,//扫描结果由微信处理
						                scanType: ['qrCode'],//只能处理二维码 
						                success: function(res){}//扫描成功回调(needResult为1时有效)
						            }); 
					            }else{
					            	window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
					            }
							});
						}					
					}else{
						window.location.href = 'bindCard.html';
					}
				}else if(!cache.isQueryBindCard){
					//调用查询是否开户接口
					Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
						if(result){
							//风控
							if(cache.isQuery){
								if(cache.isRisk){
									wx.scanQRCode({
						                needResult: 0,//扫描结果由微信处理
						                scanType: ['qrCode'],//只能处理二维码 
						                success: function(res){}//扫描成功回调(needResult为1时有效)
						            });					
								}else{
									window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
								}
							}else if(!cache.isQuery){
								//调用查询是否风控接口
								Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
									if(result.riskInfo){
										wx.scanQRCode({
							                needResult: 0,//扫描结果由微信处理
							                scanType: ['qrCode'],//只能处理二维码 
							                success: function(res){}//扫描成功回调(needResult为1时有效)
							            }); 
						            }else{
						            	window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
						            }
								});
							} 
			            }else{
			            	window.location.href = 'bindCard.html';
			            }
					});
				}
			});
	    });
		
		//配置微信JSSDK失败回调
		wx.error(function(res){
			Base.alertMsg('微信配置JSSDK失败，请刷新页面重新请求配置！');
		});
		//点击二手车
		$('#buyOldCar').click(function(e){
			//绑卡
			if(cache.isQueryBindCard){
				if(cache.isBindCard){
					//风控
					if(cache.isQuery){
						if(cache.isRisk){
							//
							window.location.href = '../buyCar/buyOldCar.html';
						}else{
							window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
						}
					}else if(!cache.isQuery){
						//调用查询是否风控接口
						Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
							if(result.riskInfo){
								window.location.href = '../buyCar/buyOldCar.html'; 
				            }else{
				            	window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
				            }
						});
					}					
				}else{
					window.location.href = 'bindCard.html';
				}
			}else if(!cache.isQueryBindCard){
				//调用查询是否开户接口
				Base.post(Base.url.rootUrl + '/queryHasAuthority', {}, function(result){
					if(result){
						//风控
						if(cache.isQuery){
							if(cache.isRisk){
								window.location.href = '../buyCar/buyOldCar.html';				
							}else{
								window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
							}
						}else if(!cache.isQuery){
							//调用查询是否风控接口
							Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
								if(result.riskInfo){
									window.location.href = '../buyCar/buyOldCar.html'; 
					            }else{
					            	window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
					            }
							});
						} 
		            }else{
		            	window.location.href = 'bindCard.html';
		            }
				});
			}
		});
		//点击关于我
		$('#aboutMe').click(function(e){
			window.location.href = '../aboutMe/aboutMe.html';
		});
		//点击信用管理
		$('#credit').click(function(e){
			if(cache.isQuery){
				window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
			}else{
				//调用查询是否风控接口
				Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
					window.location.href = 'riskInfoAdd.html?regId=' + cache.regId;
				});
			}			
		});
		//点击我的订单
		$('#stageOrder').click(function(e){
			window.location.href = '../myOrder/myOrder.html';
		});
		//点击我的账单
		$('#myBill').click(function(e){
			window.location.href = '../myBill/myBill.html';
		});
		//点击我要还款
		$('#repayment').click(function(e){
			window.location.href = '../toPay/toPay.html';
		});
		//点击我要签约
		$('#contract').click(function(e){
			window.location.href = '../contract/contractOrder.html';
		});
	})
});