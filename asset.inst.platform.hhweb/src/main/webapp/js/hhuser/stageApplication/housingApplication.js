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
		
		//用于缓存全局变量(城市,商户,分期计划...)
		var cache = {locationArr: [], merchantArr: [], planArr: [], projectNameArr: [], projectDetailArr: []};
	
		//调用自动定位接口	
		Base.post(Base.url.rootUrl + '/user/getUserAddress', {}, function(result){
			if(result){
				//自动定位成功,静默调用查找商户接口
				$('#location').val(result.province + '-' + result.city);
				Base.post(Base.url.rootUrl + '/1100/getMerchantList', {city: result.city}, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
						});
					}				
				});
			}else{
				$('#location').val('自动定位失败');
				//自动定位失败,静默调用查找城市接口
				Base.post(Base.url.rootUrl + '/1/getProAndCity', {}, function(result){
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
				Base.post(Base.url.rootUrl + '/1/getProAndCity', {}, function(result){
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
							Base.post(Base.url.rootUrl + '/1100/getMerchantList', {city: value[1]}, function(result){
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
					//静默调用查询商户接口
					Base.post(Base.url.rootUrl + '/1100/getMerchantList', {city: value[1]}, function(result){
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
		
		//商户名称-绑定获取焦点事件回调
		$('#merchantShortName').on('focusin', function(e){
			
			//为避免月供金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#monthAmount').val().length > 0) && (!Validate.validateAmt($('#monthAmount').val()))){
				return false;
			};
			
			//为避免押金金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#margin').val().length > 0) && (!Validate.validateAmtZero($('#margin').val()))){
				return false;
			};
			
			if(cache.merchantArr.length == 0){
				var option= {city: ''};
				var location = $('#location').val();
				if(location.length > 0){
					option.city = (location.split('-'))[1];
				}
				Base.post(Base.url.rootUrl + '/1100/getMerchantList', option, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
						});
						weui.picker(cache.merchantArr, {className: 'custom-classname', defaultValue: [cache.merchantArr[0].value], onChange: function(){}, onConfirm: function(value){
							$('#merchantShortName').val((value[0].split('-'))[0]);
							$('#merchantNo').val((value[0].split('-'))[1]);
							//静默调用查找项目名称接口
							Base.post(Base.url.rootUrl + '/getModelsByMerchantNo', {merchantNo: (value[0].split('-'))[1]}, function(result){
								if(result.length > 0){
									$.each(result, function(i, n){
										cache.projectNameArr.push({label: n, value: n});
									});	
								}				
							});
						}, id: 'singleLinePicker'});
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
						//静默调用查找项目名称接口
						Base.post(Base.url.rootUrl + '/getModelsByMerchantNo', {merchantNo: (value[0].split('-'))[1]}, function(result){
							if(result.length > 0){
								cache.projectNameArr.length = 0;
								$.each(result, function(i, n){
									cache.projectNameArr.push({label: n, value: n});
								});	
							}				
						});
						$('#projectName').val('');
						$('#projectDetail').val('');
						cache.planArr.length = 0
						$('#planShortName').val('');
						$('#planId').val('');			        	
					}
				}, id: 'singleLinePicker' + (new Date()).getMilliseconds()});
			}			
		});		
		$('#merchantShortName').next().find('img').on('click', function(e){
			$('#merchantShortName').focus();
		});
		
		//项目名称-绑定获取焦点事件回调
		$('#projectName').on('focusin', function(e){
			
			//为避免月供金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#monthAmount').val().length > 0) && (!Validate.validateAmt($('#monthAmount').val()))){
				return false;
			};
			
			//为避免押金金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#margin').val().length > 0) && (!Validate.validateAmtZero($('#margin').val()))){
				return false;
			};
			
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length > 0){
				if(cache.projectNameArr.length == 0){
					//调用查询项目名称接口
					Base.post(Base.url.rootUrl + '/getModelsByMerchantNo', {merchantNo: merchantNo}, function(result){
						if(result.length > 0){
							$.each(result, function(i, n){
								cache.projectNameArr.push({label: n, value: n});
							});							
							weui.picker(cache.projectNameArr, {className: 'custom-classname', defaultValue: [cache.projectNameArr[0].value], onChange: function(){}, onConfirm: function(value){
								$('#projectName').val(value[0]);
							}, id: 'projectNamePicker' + (new Date()).getMilliseconds()});
						}else{
							Base.alertMsg('系统暂时无任何项目信息！');
						}
					});
				}else{
					var projectName = $('#projectName').val();
					weui.picker(cache.projectNameArr, {className: 'custom-classname', defaultValue: [cache.projectNameArr[0].value], onChange: function(){}, onConfirm: function(value){
						$('#projectName').val(value[0]);
						if(projectName != value[0]){
							$('#projectDetail').val('');
						}
					}, id: 'projectNamePicker' + (new Date()).getMilliseconds()});
				}
			}else{
				Base.alertMsg('请优先选择商户名称！');	
			}			
		});
		$('#projectName').next().find('img').on('click', function(e){
			$('#projectName').focus();
		});
		
		//项目信息-绑定获取焦点事件回调
		$('#projectDetail').on('focusin', function(e){
			
			//为避免月供金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#monthAmount').val().length > 0) && (!Validate.validateAmt($('#monthAmount').val()))){
				return false;
			};
			
			//为避免押金金额为无效金额类型时继续弹出项目名称下拉问题
			if(($('#margin').val().length > 0) && (!Validate.validateAmtZero($('#margin').val()))){
				return false;
			};
			
			var merchantNo = $('#merchantNo').val();
			var projectName = $('#projectName').val();
			if(projectName.length > 0){
				//调用查询项目信息接口
				Base.post(Base.url.rootUrl + '/getVehByProjectName', {merchantNo: merchantNo, projectName: projectName}, function(result){
					if(result.length > 0){
						var arr = [];
						$.each(result, function(i, n){
							arr.push({label: n, value: n});
						});						
						weui.picker(arr, {className: 'custom-classname', defaultValue: [arr[0].value], onChange: function(){}, onConfirm: function(value){$('#projectDetail').val(value[0]);}, id: 'projectDetailPicker' + (new Date()).getMilliseconds()});
					}else{
						Base.alertMsg('系统暂时无' + projectName + '信息！');						
					}
				});	
			}else{
				Base.alertMsg('请优先选择项目名称！');
			}
		});
		$('#projectDetail').next().find('img').on('click', function(e){
			$('#projectDetail').focus();
		});
		
		//月供金额-绑定失去焦点事件回调
		$('#monthAmount').on('focusout', function(e){
			if(($(e.target).val().length > 0) && (!Validate.validateAmt($(e.target).val()))){
				Base.alertMsg('月供金额为无效金额类型！', null, function(){
					$(e.target).focus();
				});
			};
		});
		
		//押金金额-绑定失去焦点事件回调
		$('#margin').on('focusout', function(e){
			if(($(e.target).val().length > 0) && (!Validate.validateAmtZero($(e.target).val()))){
				Base.alertMsg('押金金额为无效金额类型！', null, function(){
					$(e.target).focus();
				});
			};
		});
		
		//分期方案-绑定获取焦点事件回调
		$('#planShortName').on('focusin', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length > 0){				
				if(cache.planArr.length == 0){
					Base.post(Base.url.rootUrl + '/1000/getPlanListByMerchantNo ', {merchantNo: merchantNo}, function(result){
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
							Base.alertMsg('该商户暂时无任何分期方案！');						
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
			}else{
				Base.alertMsg('请优先选择商户名称！');	
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
		        	$('#planShortName').val($(this).attr('planShortName'));
		        	$('#planId').val($(this).attr('planId'));
	        	}	        	
       	 	});	        
		});
		
		//提交-绑定单击事件回调
		$('#btn-apply').on('click', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length == 0){
				Base.alertMsg('商户名称不能为空！', null, function(){$('#merchantShortName').focus();});
				return false;
			}
			var projectName = $('#projectName').val();
			if(projectName.length == 0){
				Base.alertMsg('项目名称不能为空！', null, function(){$('#projectName').focus();});
				return false;
			}
			var projectDetail = $('#projectDetail').val();
			if(projectDetail.length == 0){
				Base.alertMsg('项目信息不能为空！', null, function(){$('#projectDetail').focus();});
				return false;
			}
			var monthAmount = $('#monthAmount').val();
			if(monthAmount.length == 0){
				Base.alertMsg('月供金额不能为空！', null, function(){$('#monthAmount').focus();});
				return false;
			}
			var margin = $('#margin').val();
			if(margin.length == 0){
				margin = 0;
			}
			var planId = $('#planId').val();
			if(planId.length == 0){
				Base.alertMsg('分期方案不能为空！', null, function(){$('#planShortName').focus();});
				return false;
			}
			var remark = $.trim($('#remark').val());
			//加载框开启
			Base.loadingOn();
			//调用保存接口
			Base.post(Base.url.rootUrl + '/1100/generateOrder', {merchantNo: merchantNo, orderName: (projectName + projectDetail), monthInterest: monthAmount, margin: margin, planId: planId, orderRemark: remark}, function(result){
				//跳转支付页面
				window.location.href = Base.url.webUrl + '/views/hhuser/stageApplication/housingApplicationToPay.html?id=' + result.id;
			});
		});
		
		
	});
	
});