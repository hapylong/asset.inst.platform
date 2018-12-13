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
		
		//用于缓存全局变量(城市,商户,车辆总价,分期计划...)
		var cache = {locationArr: [], merchantArr: [], amt: '', planArr: []};
		
		//更新接口(首 付,月 供,保证金,服务费,上收息)
		function request(){
			Base.post(Base.url.rootUrl + '/estimateOrderByPlanId ', {orderAmt: cache.amt, planId: $('#planId').val()}, function(result){
				$('#downPayment').val(result.downPayment);
				$('#monthAmount').val(result.monthMake);
				$('#margin').val(result.margin);
				$('#serviceFee').val(result.serviceFee);
				$('#feeAmount').val(result.feeAmount);			
			});
		};
		
		//调用自动定位接口
		Base.post(Base.url.rootUrl + '/user/getUserAddress', {}, function(result){
			if(result){
				//自动定位成功
				$('#location').val(result.province + '-' + result.city);
				//静默调用查找商户接口
				Base.post(Base.url.rootUrl + '/2/2002/getMerchantList', {city: result.city}, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
						});
					}				
				});
			}else{
				//自动定位失败
				$('#location').val('自动定位失败');
				//静默调用查找城市接口
				Base.post(Base.url.rootUrl + '/2/getProAndCity', {}, function(result){
					if(result.length > 0){
						var provinceArr = [];
						$.each(result, function(i, n){
							if(!provinceArr.contain(n.province)){
								provinceArr.push(n.province);
							}
						});
						$.each(provinceArr, function(i, n){
							var location = {label: n, value: n, children: []};							
							$.each(result, function(j, k){
								if(n == k.province){
									location.children.push({label: k.city, value: k.city});
								}
							});
							cache.locationArr.push(location);
						});
					}
				});
			}
		});
		
		//手动定位-绑定单击事件回调
		$('.mainsr').on('click', function(e){
			if(cache.locationArr.length == 0){
				Base.post(Base.url.rootUrl + '/2/getProAndCity', {}, function(result){
					if(result.length > 0){
						var provinceArr = [];
						$.each(result, function(i, n){
							if(!provinceArr.contain(n.province)){
								provinceArr.push(n.province);
							}
						});
						$.each(provinceArr, function(i, n){
							var location = {label: n, value: n, children: []};							
							$.each(result, function(j, k){
								if(n == k.province){
									location.children.push({label: k.city, value: k.city});
								}
							});
							cache.locationArr.push(location);
						});
						weui.picker(cache.locationArr, {className: 'custom-classname', defaultValue: [cache.locationArr[0].value, cache.locationArr[0].children[0].value], onChange: function(){}, onConfirm: function(value){
							$('#location').val(value[0] + '-' + value[1]);
							//静默调用查找商户接口
							Base.post(Base.url.rootUrl + '/2/2002/getMerchantList', {city: value[1]}, function(result){
								if(result.length > 0){
									cache.merchantArr.length = 0;
									$.each(result, function(i, n){
										cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
									});
								}				
							});
						}, id: 'doubleLinePicker'});
					}else{
						Base.alertMsg('系统暂时无任何省市信息！');
					}
				});
			}else{
				weui.picker(cache.locationArr, {className: 'custom-classname', defaultValue: [cache.locationArr[0].value, cache.locationArr[0].children[0].value], onChange: function(){}, onConfirm: function(value){
					$('#location').val(value[0] + '-' + value[1]);
					//静默调用查找商户接口
					Base.post(Base.url.rootUrl + '/2/2002/getMerchantList', {city: value[1]}, function(result){
						if(result.length > 0){
							cache.merchantArr.length = 0;
							$.each(result, function(i, n){
								cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
							});
						}				
					});
				}, id: 'doubleLinePicker'});
			}
		});
		
		//商户-绑定获取焦点事件回调
		$('#merchantShortName').on('focusin', function(e){
			
			//避免焦点上次在车辆总价但是格式错误还会弹出商户下拉的问题
			var amt = $('#amt').val();
			if((amt.length > 0) && !(Validate.validateAmt(amt))){
				return false;
			}
			
			if(cache.merchantArr.length == 0){
				var option= {city: ''};
				var location = $('#location').val();
				if((location.length > 0) && (location.indexOf('-') > 0)){
					option.city = (location.split('-'))[1];
				}
				Base.post(Base.url.rootUrl + '/2/2002/getMerchantList', option, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
						});
						weui.picker(cache.merchantArr, {className: 'custom-classname', defaultValue: [cache.merchantArr[0].value], onChange: function(){}, onConfirm: function(value){
							$('#merchantShortName').val((value[0].split('-'))[0]);
							$('#merchantNo').val((value[0].split('-'))[1]);
						}, id: 'singleLinePicker'});;
					}else{
						Base.alertMsg('系统暂时无任何商户信息！');
					}					
				});
			}else{
				var merchantNo = $('#merchantNo').val();
				weui.picker(cache.merchantArr, {className: 'custom-classname', defaultValue: [cache.merchantArr[0].value], onChange: function(){}, onConfirm: function(value){
					$('#merchantShortName').val((value[0].split('-'))[0]);
					$('#merchantNo').val((value[0].split('-'))[1]);
					if(merchantNo != (value[0].split('-'))[1]){
						cache.planArr.length = 0;
			        	$('#planShortName').val('');
			        	$('#planId').val('');
					}
				}, id: 'singleLinePicker' + (new Date()).getMilliseconds()});
			}			
		});		
		$('#merchantShortName').next().find('img').on('click', function(e){
			$('#merchantShortName').focus();
		});
		
		//车辆总价-绑定失去焦点事件回调
		$('#amt').on('focusout', function(e){
			var amt = $('#amt').val();
			if(amt.length > 0){
				if(Validate.validateAmt(amt)){
					var planId = $('#planId').val();
					if(planId.length > 0){
						if(amt != cache.amt){
							cache.amt = amt;
							//调用更新接口
							request();
						}
					}else{
						cache.amt = amt;
					}
				}else{
					Base.alertMsg('当前车辆总价为无效金额类型！', null, function(){$('#amt').focus();});
					
				}				
			}else{
				cache.amt = '';
				$('#downPayment').val('');
				$('#monthAmount').val('');
				$('#margin').val('');
				$('#serviceFee').val('');
				$('#feeAmount').val('');				
			}
		});		
		
		//分期方案-绑定获取焦点事件回调
		$('#planShortName').on('focusin', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length > 0){
				var amt = $('#amt').val();
				if(amt.length > 0){
					if(Validate.validateAmt(amt)){
						if(cache.planArr.length == 0){
							Base.post(Base.url.rootUrl + '/2002/getPlanListByMerchantNo', {merchantNo: merchantNo}, function(result){
								$.each(result, function(i, n){
									cache.planArr.push({id: n.id, planShortName: n.planShortName, planFullName: n.planFullName});
								});		
								if(cache.planArr.length > 0){
									var html = '';
									$.each(cache.planArr, function(i, n){
										html += '<div class="planContent-item">' +  
												   	'<div class="planContent-item-left">' +
												   		'<input type="radio" id="' + i + '" planId="' + n.id + '" planShortName="' + n.planShortName + '" name="radio-1-set" class="regular-radio"/><label for="' + i + '">' +
												   	'</div>' +
												   	'<div class="planContent-item-right">' +
												   		'<span class="planContent-item-right-top">' + n.planShortName + '</span><br/><span class="planContent-item-right-bottom">' + n.planFullName + '</span>' +
												   	'</div>' +
												'</div>'
									});	
									$('.planContentItem').html(html);
									$('#mask').show();
								    $('.planContent').show();
								    checkOnClick();
								}else{
									Base.alertMsg('系统暂时无分期方案信息！');						
								}
							});
						}else{
							var html = '';
							$.each(cache.planArr, function(i, n){
								html += '<div class="planContent-item">' +  
										   	'<div class="planContent-item-left">' +
										   		'<input type="radio" id="' + i + '" planId="' + n.id + '" planShortName="' + n.planShortName + '" name="radio-1-set" class="regular-radio" /><label for="' + i + '" />' +
										   	'</div>' +
										   	'<div class="planContent-item-right">' +
										   		'<span class="planContent-item-right-top">' + n.planShortName + '</span><br /><span class="planContent-item-right-bottom">' + n.planFullName + '</span>' +
										   	'</div>' +
										'</div>'
							});	
							$('.planContentItem').html(html);
							$('#mask').show();
						    $('.planContent').show();
						    checkOnClick();
						}
					}				
				}else{
					Base.alertMsg('请优先输入车辆总价！');				
				}	
			}else{
				Base.alertMsg('请优先选择商户！');	
			}
		});
		$('.choosePlan').on('click', function(e){
			$('#planShortName').focus();
		});		
		
		//分期方案下拉插件相关一
		$('.close').on('click', function(e){
			$('#mask').hide();
	        $('.planContent').hide();
		});
		
		//分期方案下拉插件相关二
		function checkOnClick(){
			$('.planContent-item').off('click').on('click', function(e){
				$('.planContent-item').find('input').prop('checked', false);
				$(this).find('input').prop('checked', true);
			});
		};		
		
		//分期方案下拉插件相关三
		$('.planContent-bottom').on('click', function(e){
			$('#mask').hide();
	        $('.planContent').hide();
	        $('.planContent').find('input:checked').each(function(i){
	        	var planId = $('#planId').val();
	        	if(planId != $(this).attr('planId')){
	        		$('#planId').val($(this).attr('planId'));
		        	$('#planShortName').val($(this).attr('planShortName'));
		        	//调用更新接口
					request();
	        	}	        	
       	 	});	        
		});
		
		//提交申请-绑定单击事件回调
		$('#btn-apply').on('click', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length == 0){
				Base.alertMsg('商户名称不能为空！', null, function(){$('#merchantShortName').focus();});
				return false;
			}
			var projectName = $('#projectName').val();
			if(projectName.length == 0){
				Base.alertMsg('品牌不能为空！', null, function(){$('#projectName').focus();});
				return false;
			}
			var projectDetail = $('#projectDetail').val();
			if(projectDetail.length == 0){
				Base.alertMsg('车系不能为空！', null, function(){$('#projectDetail').focus();});
				return false;
			}
			var amt = $('#amt').val();
			if(amt.length == 0){
				Base.alertMsg('车辆总价不能为空！', null, function(){$('#amt').focus();});
				return false;
			}
			var planId = $('#planId').val();
			if(planId.length == 0){
				Base.alertMsg('分期方案不能为空！', null, function(){$('#planShortName').focus();});
				return false;
			}
			//加载框开启
			Base.loadingOn();
			Base.post(Base.url.rootUrl + '/2002/generateOrder', {merchantNo: merchantNo, orderName: (projectName + projectDetail), orderAmt: amt, planId: planId, chargeWay: 0}, function(result){
				//跳转生成订单成功页面
				window.location.href = Base.url.webUrl + '/views/user/buyCar/orderSucc.html';
			});
		});
		
		
	});
	
});