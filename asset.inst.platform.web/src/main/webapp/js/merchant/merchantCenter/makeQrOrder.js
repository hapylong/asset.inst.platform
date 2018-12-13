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
		
		//用于缓存全局变量(品牌,车辆总价,分期方案)
		var cache = {projectNameArr: [], amt: '', planArr: []};
		
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
		
		//品牌-绑定获取焦点事件回调
		$('#projectName').on('focusin', function(e){
			
			//避免焦点上次在车辆总价但是格式错误还会弹出品牌下拉的问题
			var amt = $('#amt').val();
			if((amt.length > 0) && !(Validate.validateAmt(amt))){
				return false;
			}
			
			if(cache.projectNameArr.length == 0){
				//调用查询品牌接口
				Base.post(Base.url.rootUrl + '/getCarModelsByMerchantNo ', {}, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.projectNameArr.push({label: n, value: n});
						});	
						weui.picker(cache.projectNameArr, {className: 'custom-classname', defaultValue: [cache.projectNameArr[0].value], onChange: function(){}, onConfirm: function(value){$('#projectName').val(value[0]);}, id: 'projectNamePicker'});
					}else{
						Base.alertMsg('系统暂时无任何品牌信息！');
					}
				});
			}else{
				var projectName = $('#projectName').val();
				weui.picker(cache.projectNameArr, {className: 'custom-classname', defaultValue: [cache.projectNameArr[0].value], onChange: function(){}, onConfirm: function(value){
					$('#projectName').val(value[0]);
					if(projectName != value[0]){
						$('#projectDetail').val('');
					}
				}, id: 'projectNamePicker'});
			}			
		});
		
		//车系-绑定获取焦点事件回调
		$('#projectDetail').on('focusin', function(e){

			//避免焦点上次在车辆总价但是格式错误还会弹出车系下拉的问题
			var amt = $('#amt').val();
			if((amt.length > 0) && !(Validate.validateAmt(amt))){
				return false;
			}
			
			var projectName = $('#projectName').val();
			if(projectName.length > 0){
				//调用查询车系接口
				Base.post(Base.url.rootUrl + '/getCarVehByProjectName ', {projectName: projectName}, function(result){
					if(result.length > 0){
						var arr = [];
						$.each(result, function(i, n){
							arr.push({label: n, value: n});
						});						
						weui.picker(arr, {className: 'custom-classname', defaultValue: [arr[0].value], onChange: function(){}, onConfirm: function(value){$('#projectDetail').val(value[0]);}, id: 'projectDetailPicker' + (new Date()).getMilliseconds()});
					}else{
						Base.alertMsg('系统暂时无' + projectName + '相关车系信息！');						
					}
				});	
			}else{
				Base.alertMsg('请优先选择品牌！');
			}					
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
			var amt = $('#amt').val();
			if(amt.length > 0){
				if(Validate.validateAmt(amt)){
					if(cache.planArr.length == 0){
						var merchantNo = sessionStorage.getItem('merchantNo');
						Base.post(Base.url.rootUrl + '/2001/getPlanListByMerchantNo ', {merchantNo: merchantNo}, function(result){
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
		
		//生成二维码-绑定单击事件回调
		$('#btn-code').on('click', function(e){
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
			Base.post(Base.url.rootUrl + '/2/merchant/createQrCode', {projectName: projectName, projectDetail: projectDetail, amt: amt, planId: planId, remark: $.trim($('#remark').val())}, function(result){
				//跳转生成订单成功页面
				window.location.href = Base.url.webUrl + '/views/merchant/merchantCenter/qrCodeInfo.html?id=' + result;
			});		
		});		
	});
});