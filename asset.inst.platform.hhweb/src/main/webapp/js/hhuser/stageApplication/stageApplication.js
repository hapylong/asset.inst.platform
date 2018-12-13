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
		
		//用于缓存全局变量(城市,商户,借款总额,分期计划...)
		var cache = {locationArr: [], merchantArr: [], amt: 0, planArr: []};
		
		//更新接口(首付,月供)
		function request(){
			Base.post(Base.url.rootUrl + '/estimateOrderByPlanId ', {orderAmt: cache.amt, planId: $('#planId').val()}, function(result){
				$('#downPayment').val(result.downPayment);
				$('#monthInterest').val(result.monthInterest);		
			});
		};
		
		//调用自动定位接口
		Base.post(Base.url.rootUrl + '/user/getUserAddress', {}, function(result){
			if(result){
				$('#location').val(result.province + '-' + result.city);
				//自动定位成功,静默调用查找商户接口
				Base.post(Base.url.rootUrl + '/1000/getMerchantList', {city: result.city}, function(result){
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
							Base.post(Base.url.rootUrl + '/1000/getMerchantList', {city: value[1]}, function(result){
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
					Base.post(Base.url.rootUrl + '/1000/getMerchantList', {city: value[1]}, function(result){
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
			
			//为避免单价为无效金额类型时继续弹出商户名称下拉问题
			var boo = false
			$.each($('.price'), function(i, n){
				  if(($(n).val().length > 0) && !(Validate.validateAmt($(n).val()))){
					  boo = true;
				  }
			});
			if(boo){
				return false;
			}
			
			if(cache.merchantArr.length == 0){
				var option= {city: ''};
				var location = $('#location').val();
				if((location.length > 0) && (location.indexOf('-') > 0)){
					option.city = (location.split('-'))[1];
				}
				Base.post(Base.url.rootUrl + '/1000/getMerchantList', option, function(result){
					if(result.length > 0){
						$.each(result, function(i, n){
							cache.merchantArr.push({label: n.merchantShortName, value: (n.merchantShortName + '-' + n.merchantNo)});
						});
						weui.picker(cache.merchantArr, {className: 'custom-classname', defaultValue: [cache.merchantArr[0].value], onChange: function(){}, onConfirm: function(value){
							$('#merchantShortName').val((value[0].split('-'))[0]);
							$('#merchantNo').val((value[0].split('-'))[1]);
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
						cache.planArr.length = 0
						$('#planShortName').val('');
						$('#planId').val('');	
						$('#downPayment').val('');
						$('#monthInterest').val('');
					}
				}, id: 'singleLinePicker' + (new Date()).getMilliseconds()});
			}			
		});		
		$('#merchantShortName').next().find('img').on('click', function(e){
			$('#merchantShortName').focus();
		});
		
		//单价-绑定获取焦点事件回调
		$(document).on('focusin', '.price', function(e){
			if($.trim($(e.target).parent().parent().find('.item').first().val()).length == 0){
				Base.alertMsg('请优先输入项目名称！', null, function(){
					$(e.target).parent().parent().find('.item').first().focus();
				});
			}
		});
		
		//单价-绑定失去焦点事件回调
		$(document).on('focusout', '.price', function(e){
			
			if($(e.target).val().length > 0){
				if(!Validate.validateAmt($(e.target).val())){
					Base.alertMsg('当前单价为无效金额类型！', null, function(){
						$(e.target).focus();
					});
					return false;
				}
			}
			cache.amt = 0;
			$.each($('.price'), function(i, n){
				 var price = 0;
				 if($(n).val().length > 0){
					 price = parseFloat($(n).val());
				 }
				 cache.amt += price;
			});		
			if(cache.amt == 0){
				$('#amt').val('');
				$('#downPayment').val('');
				$('#monthInterest').val('');
			}else{
				$('#amt').val(cache.amt);
				if($('#planId').val().length > 0){
					//调用更新接口
					request();
				}
			}			
		});
		
		//添加项目-绑定单击事件回调
		$('.main3').on('click', function(e){			
			if($('.main2').length < 5){
				var str = '';
				str +=	'<div class="main2">' +
							'<div class="main2-r">' +
								'<div class="mainr2 clearfix">' +
									'<span class="span1">单价</span>' +
									'<input type="number" id="price' + ($('.main2').length + 1) + '" class="price" name="price' + ($('.main2').length + 1 ) + '" placeholder="金额" />' +
									'<span class="span2">元</span>' +
								'</div>' +
								'<div class="mainr1 clearfix">' +
									'<span>项目' + ($('.main2').length + 1) + '</span><img src="../../../img/deleteRed.png" class="proDelete  deleteRed">' +
									'<input type="text" id="item' + ($('.main2').length + 1) + '" class="item" name="item' + ($('.main2').length + 1 ) + '" placeholder="请输入项目名称" />' +
								'</div>' +
							'</div>' +
						'</div>';
			    $(".main3").before(str);
			}else{
				Base.alertMsg('最多填写5项项目信息！');
			}
		});
		//删除项目-绑定单击事件回调
		$(".proMain").delegate(".deleteRed","click",function(){
			var $this = $(this);
			alerts();
			$('.cb').html('确认删除该项目？');
			$('.qd').click(function () {
				var pro = $this.parent().parent().parent();
				pro.remove();
			})
		});
		//确认弹框
		function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	        $('.qx').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	    }
		//分期方案-绑定获取焦点事件回调
		$('#planShortName').on('focusin', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length > 0){
				if($('#amt').val().length > 0){					
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
					Base.alertMsg('请优先填写至少一条完整的项目信息！');				
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
	        		$('#planId').val($(this).attr('planId'));
		        	$('#planShortName').val($(this).attr('planShortName'));
		        	//调用更新接口
					request();
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
			var amt = $('#amt').val();
			var projectName = [];
			var projectAmt = [];
			if(amt.length > 0){
				var boo = true
				$.each($('.price'), function(i, n){
					  if($(n).val().length == 0){
						  boo = false;
					  }
				});
				if(boo){
					$.each($('.item'), function(i, n){
						projectName.push($.trim($(n).val()));
					});
					$.each($('.price'), function(i, n){
						projectAmt.push($(n).val());
					});
					projectName = projectName.join(';');
					projectAmt = projectAmt.join(';');
				}else{
					Base.alertMsg('项目信息需要全部填写完整！');
					return false;
				}
			}else{
				Base.alertMsg('至少填写一条完整的项目信息！');
				return false;
			}
			var planId = $('#planId').val();
			if(planId.length == 0){
				Base.alertMsg('分期方案不能为空！', null, function(){$('#planShortName').focus();});
				return false;
			}
			//加载框开启
			Base.loadingOn();
			Base.post(Base.url.rootUrl + '/1000/generateOrder', {merchantNo: merchantNo, projectName: projectName, projectAmt: projectAmt, orderAmt: amt, planId: planId, orderRemark: $.trim($('#remark').val())}, function(result){
				//跳转生成订单成功页面
				window.location.href = Base.url.webUrl + '/views/hhuser/stageApplication/orderSucc.html';
			});
		});
		
		
	});
	
});