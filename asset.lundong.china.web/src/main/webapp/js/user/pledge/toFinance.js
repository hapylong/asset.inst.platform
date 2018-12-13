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
		//用于缓存全局变量
		var cache = {amt: '', planArr: [], applyAmt: '', purposeArr: [],merchantShortNameArr: [],id: ''};
		
		//商户列表
		$('#merchantShortName').on('focusin', function(e){
			Base.post(Base.url.rootUrl + '/2/2200/getMerchantList ', {}, function(result){
				$.each(result, function(i, n){
					cache.merchantShortNameArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
				});
				weui.picker(cache.merchantShortNameArr, {className: 'custom-classname', defaultValue: [cache.merchantShortNameArr[0].value], onChange: function(){}, onConfirm: function(value){
					$('#merchantShortName').val((value[0].split('-'))[0]);
					$('#merchantNo').val((value[0].split('-'))[1]);
				}, id: 'singleLinePicker'});
			})
		});
		//输入车牌号查询车辆信息
		$('#licenseNumber').on('blur',function(){
			var licenseNumber = $(this).val();
			if(licenseNumber != ''){
				var option = {
						'plate':licenseNumber,
						'engine':$('#engine').val(),
						'merchantNo':$('#merchantNo').val()
				}
				Base.post(Base.url.rootUrl + '/pledge/cget_car_info ', option, function(result){
					if(result != null){
						$('#engine').val(result.engine);
						$('#projectName').val(result.carBrand);
						$('#typeConfigure').val(result.carDetail);
						$('#evaluatedPrice').val(Formatter.moneyTenThousand(result.assessPrice));
						$('#creditLine').val(Formatter.moneyTenThousand(result.applyAmt));
						cache.applyAmt = result.applyAmt;
						cache.id = result.id;
					}
				})
			}
		});
		//输入发动机号查询车辆信息
		$('#engine').on('blur',function(){
			var engine = $(this).val();
			if(engine != ''){
				var option = {
						'plate':$('#licenseNumber').val(),
						'engine':engine,
						'merchantNo':$('#merchantNo').val()
				}
				Base.post(Base.url.rootUrl + '/pledge/cget_car_info ', option, function(result){
					if(result != null){
						$('#licenseNumber').val(result.plate);
						$('#projectName').val(result.carBrand);
						$('#typeConfigure').val(result.carDetail);
						$('#evaluatedPrice').val(Formatter.moneyTenThousand(result.assessPrice));
						$('#creditLine').val(Formatter.moneyTenThousand(result.applyAmt));
						cache.applyAmt = result.applyAmt;
						cache.id = result.id;
					}
				})
			}
		});
		
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
							Base.post(Base.url.rootUrl + '/2200/getPlanListByMerchantNo', {merchantNo: merchantNo}, function(result){
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
					Base.alertMsg('请优先输入申请金额！');				
				}	
			}else{
				Base.alertMsg('无商户信息！');	
			}
		});
		$('.choosePlan').on('click', function(e){
			$('#planShortName').focus();
		});		
		
		//借款用途
		$('#purpose').on('focusin', function(e){
			var result = [{'purpose':'资金周转','purposeNo':'purposeOne'}];
			cache.purposeArr = [];
			$.each(result, function(i, n){
				cache.purposeArr.push({label: n.purpose, value: (n.purpose + '-' + n.purposeNo)});
			});
			weui.picker(cache.purposeArr, {className: 'custom-classname', defaultValue: [cache.purposeArr[0].value], onChange: function(){}, onConfirm: function(value){
				$('#purpose').val((value[0].split('-'))[0]);
				$('#purposeNo').val((value[0].split('-'))[1]);
			}, id: 'singleLinePicker'});			
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
		//清空
		$('#clear').click(function(){
			$('input').val('');
		});
		//提交申请
		$('#submitApply').click(function(){
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
			var amt = $('#amt').val();
			if(amt.length == 0){
				Base.alertMsg('申请金额不能为空！', null, function(){$('#amt').focus();});
				return false;
			}
			//申请金额不能大于可融资额度！
			var creditLine = cache.applyAmt;
			if(amt > creditLine){
				Base.alertMsg('申请金额不能大于可融资额度！', null, function(){$('#amt').focus();});
				return false;
			}
			var purpose = $('#purpose').val();
			if(purpose.length == 0){
				Base.alertMsg('借款用途不能为空！', null, function(){$('#purpose').focus();});
				return false;
			}
			var planId = $('#planId').val();
			if(planId.length == 0){
				Base.alertMsg('产品方案不能为空！', null, function(){$('#planShortName').focus();});
				return false;
			}
			//加载框开启
			Base.loadingOn();
			var option = {
					'id':String(cache.id),
					'orderAmt':Number($('#amt').val()),
					'planId':String($('#planId').val()),
					'merchantNo':$('#merchantNo').val()
			}
			Base.post(Base.url.rootUrl + '/pledge/bing_order_info', option, function(result){
				if(result == 'success'){
					window.location.href = '../userCenter/memberCenter.html';  
				} 
			});
		});
	});
	
});