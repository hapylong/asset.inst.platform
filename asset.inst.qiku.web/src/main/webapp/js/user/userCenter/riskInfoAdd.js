require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
		'weui': 'weui/js/weui.min',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2', 'jquery', 'base', 'formatter', 'common', 'weui'], function(JSON, $, Base, Formatter, Common, weui){
	$(function(){
		//用于定义页面变量
		var cache = {marriedStatusArr: [{label: '未婚', value: '未婚-UNMARRIED'},{label: '已婚', value: '已婚-UNMARRIED'}], smsMobile:''};
    	//弹窗
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
		$('.close').click(function(){
	        $(this).siblings('.login-input').val('');
	        $(this).hide();
	    });
		$('.login-input').keyup(function(){
	        if ($(this).val()!='') {
	            $(this).siblings('.close').show();
	        }else if($(this).val()==''){
	            $(this).siblings('.close').hide();
	        }
	        clearIpt();
	    });
		//倒计时
		var tim =60; // 剩余时间
		function tim_Down(){
			if (tim>0) {
				$('.sendTim').show().html(tim+'s后');
					tim--;
					setTimeout('tim_Down()', 1000);
			}
			else if (tim<=0) {
				$('.sendTim').hide();
				$('.sendMsg').addClass('send-able').removeClass('send-unable');
				$('.voiceCode').addClass('voice-able').removeClass('voice-unable');
				tim=60;
			}
		}
    	/** 发送短信验证码按钮 **/
		$('.sendMsg').click(function() {
			if($('.sendMsg').hasClass('send-able')){
	    		Base.loadingOn();
	    		Base.post(Base.url.rootUrl + '/unIntcpt-req/11/sendVerifyCode', {'smsMobile':$('#smsMobile').val()}, function(result){
	    			Base.loadingOut();
	    			//短信倒计时
	    			if ($('.sendMsg').hasClass('send-able')) {
						$('.sendMsg').addClass('send-unable').removeClass('send-able');
						$('.voiceCode').addClass('voice-unable').removeClass('voice-able');
						$('.sendDet').html('重新发送');
						$('.sendTim').show().html('60');
						tim_Down();
					}else{
						$(".sendDet").val("重新发送"); 
					}
	    		});
			}
    	});
    	/** 数字验证码确认按钮 **/
    	$('#revise').click(function() {
    		$('.sendMsg').addClass('send-able').removeClass('send-unable');
    	});
    	$('#back').click(function() {
    		$('.sendMsg').addClass('send-unable').removeClass('send-able');
    	});
    	$('#smsMobile').blur(function(){
    		if($('#smsMobile').val() != cache.smsMobile){
    			//修改接收短信手机号后
    			$('.codeShow').show();
    			$('.sendMsg').addClass('send-able').removeClass('send-unable');
    		}else{
    			$('.codeShow').hide();
    			$('.sendMsg').addClass('send-unable').removeClass('send-able');
    		}
    	});
		//风控信息
		Base.post(Base.url.rootUrl + '/3/queryRiskInfo', {}, function(result){
			if(result.riskInfo != null){
	        	$(".submitss").hide();
	        	$(".btnBox").show();
	        	//赋值
	        	$("#smsMobile").val(result.riskInfo.smsMobile);
	        	cache.smsMobile = result.riskInfo.smsMobile;
	        	var checkInfo = JSON.parse(result.riskInfo.checkInfo);
	        	$("#mobelPhone").val(checkInfo.phone);
	        	$("#addProvince").val(checkInfo.addprovince);
	      	    $("#marriedStatus").val(checkInfo.marriedstatus);
	      	    $("#contactName1").val(checkInfo.contactname1);
	      	    $("#contactMobel1").val(checkInfo.contactphone1);
	      	    $("#contactName2").val(checkInfo.contactname2);
	      	    $("#contactMobel2").val(checkInfo.contactphone2);
	      	    $("input").attr("readonly",true);
	      	    $("select").attr("disabled",true);
	      	    //修改风控信息
	      	    $("#revise").click(function(){
		       		$(this).css("display","none");
		       		$("#save-revise").css("display","block");
		       		$("#back").html('取消').attr('href','javascript:;');
		       		$("input").removeAttr("readonly").css('color','#6D6D6D');
		       		$('#mobelPhone').attr("readonly",true).css('color','#ababab');
		       		$("select").removeAttr("disabled");
		       		$('.provincetab').show();
		       		var addprovince = checkInfo.addprovince;
		        	//拆分省市
		      	    if(addprovince.indexOf('省') == -1){
		      	    	var cityIndex = addprovince.indexOf('市');
		      	    	if(cityIndex == -1){
		      	    		//无省市信息
		      	    		$("#addProvince").val(addprovince);
		      	    	}else if(cityIndex != -1){
		      	    		var arr1 = addprovince.split('市');
		      	    		$('#city').val(arr1[0]);
		      	    		$('#addProvince').val(arr1[1]);
		      	    	}
		      	    }else if(addprovince.indexOf('省') != -1){
		      	    	var arr2 = addprovince.split('省');
		      	    	$('#province').val(arr2[0]);
		      	    	var cityIndex2 = arr2[1].indexOf('市');
		      	    	if(cityIndex2 == -1){
		      	    		//无市信息
		      	    		$("#addProvince").val(arr2[1]);
		      	    	}else if(cityIndex != -1){
		      	    		var arr3 = arr2[1].split('市');
		      	    		$('#city').val(arr3[0]);
		      	    		$('#addProvince').val(arr3[1]);
		      	    	}
		      	    }
		       	    //下拉
		    		$('#marriedStatus').on('focusin', function(e){
		    			weui.picker(cache.marriedStatusArr, {className: 'custom-classname', defaultValue: [cache.marriedStatusArr[0].value], onChange: function(){}, onConfirm: function(value){
		    				$('#marriedStatus').val((value[0].split('-'))[0]);
		    				$('#marriedStatusValue').val((value[0].split('-'))[1]);
		    			}, id: 'marriedStatusPicker'});			
		    		});
		       		//取消编辑提示信息
	    			$("#back").click(function(){
	    				if($("#back").html() == '取消'){
			       			alerts();
							$('.cb').html('确认取消编辑？');
							$('.qd').click(function () {
								$('.codeShow').hide();
								$("#save-revise").css("display","none");
					       		$("#revise").css("display","block");
					       		$("#back").html('返回').attr('href','memberCenter.html');
					       		$("input").attr("readonly",true).css('color','#ababab');
					        	$("select").attr("disabled",true);
					        	//赋值
					        	$("#smsMobile").val(result.riskInfo.smsMobile);
					        	cache.smsMobile = result.riskInfo.smsMobile;
					        	var checkInfo = JSON.parse(result.riskInfo.checkInfo);
					        	$("#mobelPhone").val(checkInfo.phone);
					        	$("#addProvince").val(checkInfo.addprovince);
					      	    $("#marriedStatus").val(checkInfo.marriedstatus);
					      	    $("#contactName1").val(checkInfo.contactname1);
					      	    $("#contactMobel1").val(checkInfo.contactphone1);
					      	    $("#contactName2").val(checkInfo.contactname2);
					      	    $("#contactMobel2").val(checkInfo.contactphone2);
							})
	    				}
		       		});
	       	    })
	       	    $("#save-revise").click(function(){
		       		var mobelPhone = $("#mobelPhone").val();
		       		var smsMobile = $("#smsMobile").val();
		       		var verificationCode;
		       		if(smsMobile != cache.smsMobile){
		       			verificationCode = $('#verificationCode').val();
		       		}else{
		       			verificationCode = '';
		       		}
		       		var addProvince = $("#addProvince").val();
		       		var marriedStatus = $("#marriedStatus").val();
		       		var contactName1 = $("#contactName1").val();
		       		var contactMobel1 = $("#contactMobel1").val();
		       		var contactName2 = $("#contactName2").val();
		       		var contactMobel2 = $("#contactMobel2").val();
		       		//提交
		        	var mobelPreg = /^(1)\d{10}$/;
	    	    	if (mobelPhone == "" || !mobelPreg.test(mobelPhone)) {
	    	    		Base.alertMsg('请填写合法11位手机号码',null,function(){
	    	    			$("#mobelPhone").focus();
	    	    		});
	    				return false; 
	    			} 
	    	    	if (smsMobile == "" || !mobelPreg.test(smsMobile)) {
	    	    		Base.alertMsg('请填写合法11位手机号码',null,function(){
	    	    			$("#smsMobile").focus();
	    	    		});
	    				return false; 
	    			} 
	    	    	if($('#smsMobile').val() != cache.smsMobile){
	    	    		if (verificationCode == "") {
		    	    		Base.alertMsg('请填写验证码',null,function(){
		    	    			$("#verificationCode").focus();
		    	    		});
		    				return false; 
		    			}
	    	    	}
	    			if (addProvince == "") {
	    				Base.alertMsg('请填写地址',null,function(){
	    	    			$("#addProvince").focus();
	    	    		});
	    				return false; 
	    			}
	    			//城市和省份
		       		var province = $("#province").val();
		       		var city = $("#city").val();
		       		if(province == '' && city == ''){
		       			addProvince = addProvince;
		       		}else if(province != '' && city != ''){
		       			addProvince = province +'省' + city +'市' + addProvince;
		       		}else if(province != '' && city == ''){
		       			addProvince = province +'省' + addProvince;
		       		}else if(province == '' && city != ''){
		       			addProvince = city +'市' + addProvince;
		       		}
	    			if(marriedStatus == ''){
	    				Base.alertMsg('请选择婚姻状况',null,function(){});
	    				return false; 
	    			}
	    			if ($("#contactName1").val() == "") {
	    				Base.alertMsg('请填写亲属姓名',null,function(){
	    					$("#contactName1").focus();
	    	    		});
	    				return false; 
	    			}
	    			if (contactMobel1 == "") {
	    				Base.alertMsg('请填写亲属手机号码',null,function(){
	    					$("#contactMobel1").focus();
	    	    		});
	    				return false; 
	    			}else if(!mobelPreg.test(contactMobel1)){
	    				Base.alertMsg('亲属手机号码格式不正确',null,function(){
	    					$("#contactMobel1").focus();
	    	    		});
	    				return false;
	    			}
	    			if ($("#contactName2").val() == "") {
	    				Base.alertMsg('请填写朋友姓名',null,function(){
	    					$("#contactName2").focus();
	    	    		});
	    				return false; 
	    			}
	    			if (contactMobel2 == "") {
	    				Base.alertMsg('请填写朋友手机号码',null,function(){
	    					$("#contactMobel2").focus();
	    	    		});
	    				return false; 
	    			}else if(!mobelPreg.test(contactMobel2)){
	    				Base.alertMsg('朋友手机号码格式不正确',null,function(){
	    					$("#contactMobel2").focus();
	    	    		});
	    				return false; 
	    			} 
		        	var checkInfo = {
		        			  "smsMobile":smsMobile,
				              "phone": mobelPhone,
				              "addprovince":addProvince,
				              "marriedstatus":marriedStatus,
				              "contactname1":contactName1,
				              "contactphone1":contactMobel1,
				              "contactname2":contactName2,
				              "contactphone2":contactMobel2
				    };
					checkInfo = JSON.stringify(checkInfo);
				    Base.postIfFail(Base.url.rootUrl + '/3/updateRiskInfo', {'verificationCode':verificationCode,'checkInfo': checkInfo}, function(result){
					   if(result.success == 1){
						   $(this).css("display","none");
				       	   $("#revise").css("display","block");
				       	   $("#back").html('返回').attr('href','memberCenter.html');
				       	   $("input").attr("readonly",true).css('color','#ababab');
				           $("select").attr("disabled",true);
						   //修改风控信息
						   Base.toast('保存成功');
					       window.location.href = 'memberCenter.html';
					   }else{
						   Base.alertMsg(result.retUserInfo + '！');
						   //短信校验失败
					   }
				    })
	       	 })
	        }else{
	        	//从地址栏带手机号
	        	var url = window.location.href;
	    		if(url.indexOf('regId') >= 0){
	    		   var regId = location.search.match(new RegExp("[\?\&]regId=([^\&]+)","i"))[1];
	    		   $("#mobelPhone").val(regId);
	    		}
	        	$(".submitss").show();
	        	$(".btnBox").hide();
	        	$('.provincetab').show();
	        	cache.smsMobile = $("#mobelPhone").val();
	        	//下拉
	    		$('#marriedStatus').on('focusin', function(e){
	    			weui.picker(cache.marriedStatusArr, {className: 'custom-classname', defaultValue: [cache.marriedStatusArr[0].value], onChange: function(){}, onConfirm: function(value){
	    				$('#marriedStatus').val((value[0].split('-'))[0]);
	    				$('#marriedStatusValue').val((value[0].split('-'))[1]);
	    			}, id: 'marriedStatusPicker'});			
	    		});
	        	//首次提交风控信息
	        	$(".submitss").click(function(){ 
	        		var mobelPreg = /^(1)\d{10}$/;
	    	    	var mobelPhone=$("#mobelPhone").val();
	    	    	var smsMobile=$("#smsMobile").val();
	    	    	var verificationCode = $('#verificationCode').val();
	    	    	if (mobelPhone == "" || !mobelPreg.test(mobelPhone)) {
	    	    		Base.alertMsg('请填写合法11位手机号码',null,function(){
	    	    			$("#mobelPhone").focus();
	    	    		});
	    				return false; 
	    			} 
	    	    	if (smsMobile == "" || !mobelPreg.test(smsMobile)) {
	    	    		Base.alertMsg('请填写合法11位手机号码',null,function(){
	    	    			$("#smsMobile").focus();
	    	    		});
	    				return false; 
	    			}
	    	    	if($('#smsMobile').val() != cache.smsMobile){
	    	    		if (verificationCode == "") {
		    	    		Base.alertMsg('请填写验证码',null,function(){
		    	    			$("#verificationCode").focus();
		    	    		});
		    				return false; 
		    			}
	    	    	}
	    	    	if(smsMobile != cache.smsMobile){
		       			verificationCode = $('#verificationCode').val();
		       		}else{
		       			verificationCode = '';
		       		}
	    			var addProvince=$("#addProvince").val();
	    			if (addProvince == "") {
	    				Base.alertMsg('请填写地址',null,function(){
	    	    			$("#addProvince").focus();
	    	    		});
	    				return false; 
	    			}
	    			//城市和省份
		       		var province = $("#province").val();
		       		var city = $("#city").val();
		       		if(province == '' && city == ''){
		       			addProvince = addProvince;
		       		}else if(province != '' && city != ''){
		       			addProvince = province +'省' + city +'市' + addProvince;
		       		}else if(province != '' && city == ''){
		       			addProvince = province +'省' + addProvince;
		       		}else if(province == '' && city != ''){
		       			addProvince = city +'市' + addProvince;
		       		}
	    			var marriedStatus=$("#marriedStatus").val();
	    			if(marriedStatus == ''){
	    				Base.alertMsg('请选择婚姻状况',null,function(){});
	    				return false; 
	    			}
	    			var contactName1=$("#contactName1").val();
	    			if ($("#contactName1").val() == "") {
	    				Base.alertMsg('请填写亲属姓名',null,function(){
	    					$("#contactName1").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactMobel1=$("#contactMobel1").val();
	    			if (contactMobel1 == "") {
	    				Base.alertMsg('请填写亲属手机号码',null,function(){
	    					$("#contactMobel1").focus();
	    	    		});
	    				return false; 
	    			}else if(!mobelPreg.test(contactMobel1)){
	    				Base.alertMsg('亲属手机号码格式不正确',null,function(){
	    					$("#contactMobel1").focus();
	    	    		});
	    				return false;
	    			}
	    			var contactName2=$("#contactName2").val();
	    			if ($("#contactName2").val() == "") {
	    				Base.alertMsg('请填写朋友姓名',null,function(){
	    					$("#contactName2").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactMobel2=$("#contactMobel2").val();
	    			if (contactMobel2 == "") {
	    				Base.alertMsg('请填写朋友手机号码',null,function(){
	    					$("#contactMobel2").focus();
	    	    		});
	    				return false; 
	    			}else if(!mobelPreg.test(contactMobel2)){
	    				Base.alertMsg('朋友手机号码格式不正确',null,function(){
	    					$("#contactMobel2").focus();
	    	    		});
	    				return false; 
	    			} 
					var checkInfo = {
							  "smsMobile":smsMobile,
				              "phone": mobelPhone,
				              "addprovince":addProvince,
				              "marriedstatus":marriedStatus,
				              "contactname1":contactName1,
				              "contactphone1":contactMobel1,
				              "contactname2":contactName2,
				              "contactphone2":contactMobel2,
				    };
					checkInfo = JSON.stringify(checkInfo);
					Base.loadingOn();
					Base.postIfFail(Base.url.rootUrl + '/3/updateRiskInfo', {'verificationCode':verificationCode,'checkInfo': checkInfo}, function(result){
						   if(result.success == 1){
							   //修改风控信息
							   Base.toast('保存成功');
						       window.location.href = 'memberCenter.html';
						   }else{
							   Base.alertMsg(result.retUserInfo + '！');
							   //短信校验失败
						   }
					    })
			    });
	        }
		});
	});	 
});
