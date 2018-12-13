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
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2', 'jquery', 'base', 'formatter', 'weui'], function(JSON, $, Base, Formatter, weui){
	$(function(){
		window.judge = function(){
			//姓名
			var ltn=$("#realName").val();
			var reg = /^([A-Za-z]|[\u4E00-\u9FA5]||[0-9]|[·])+$/;
			//身份证号
			var bankId=$("#IDCard").val();
			var bankReg = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
			//银行卡号
			var bankCard=$("#bankCard").val();
			var CardReg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
			//银行卡类型
			var bankType = $('#bankType').val();
			if((ltn.length >=2) && reg.test(ltn) && bankReg.test(bankId) && (bankType != '') && CardReg.test(bankCard)){
				$(".redBtns").css("background","#06c8c4").removeAttr("disabled");
			}else{
				$(".redBtns").css("background","#ccc").attr("disabled",true);
			}
		}
		//用于定义页面变量
		var cache = {bankTypeArr: []};
		//获取银行卡类型
		$('#bankType').on('focusin', function(e){
			if(cache.bankTypeArr.length == 0){
				Base.get(Base.url.rootUrl + '/about/addBankCard', {}, function(result){
					var bankListMore = result.bankInfoList;
					var bankList = [];
					$.each(bankListMore, function(i, n){
						cache.bankTypeArr.push({label: n.bankName, value: n.bankName + '-' + n.bankCode});
					});	
					if(cache.bankTypeArr.length > 0){
						weui.picker(cache.bankTypeArr, {className: 'custom-classname', defaultValue: [cache.bankTypeArr[0].value], onChange: function(){}, onConfirm: function(value){
							$('#bankType').val((value[0].split('-'))[0]);
							$('#bankCode').val((value[0].split('-'))[1]);
						}, id: 'bankTypePicker'});
					}else{
						Base.alertMsg('暂时无可添加的银行卡类型！');
					}
				})
			}else{
				weui.picker(cache.bankTypeArr, {className: 'custom-classname', defaultValue: [cache.bankTypeArr[0].value], onChange: function(){}, onConfirm: function(value){
					$('#bankType').val((value[0].split('-'))[0]);
					$('#bankCode').val((value[0].split('-'))[1]);
				}, id: 'bankTypePicker'});
			}			
		});
		//弹窗
	    function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	    }
		$(function(){
			//银行卡类型
			$("#bankType").change(function(){
				judge();
			});
			//真实姓名校验
			$("#realName").blur(function(){
				var ltn=$("#realName").val();
				var reg = /^([A-Za-z]|[\u4E00-\u9FA5]||[0-9]|[·])+$/;     
				if(ltn == ''){
					Base.alertMsg('请填写姓名',null,function(){
		    			$("#realName").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
					return false;
				}
				if(!reg.test(ltn)){
					Base.alertMsg('请填写真实姓名',null,function(){
		    			$("#realName").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
					return false;
				}
				if(ltn.length == 1 ){
					Base.alertMsg('真实姓名须大于一个字',null,function(){
		    			$("#realName").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
		        	return false;
				}
			});
			//验证身份证号
			$("#IDCard").blur(function(){
				var bankId=$("#IDCard").val();
				var bankReg = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
				if(!bankReg.test(bankId)){
					Base.alertMsg('请正确输入您的身份证号',null,function(){
		    			$("#IDCard").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
		        	return false;
		        }
			});
			//银行卡号验证
			$("#bankCard").blur(function(){
				var bankCard=$("#bankCard").val();
				var CardReg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
				if(!CardReg.test(bankCard)){
					Base.alertMsg('请正确输入您的银行卡号',null,function(){
		    			$("#bankCard").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
		        	return false;
		        }
			});
			//点击提交按钮。进行表单校验，如果校验通过则提交表单。
			$('.redBtns').click(function(){
				//$(".redBtns").css("background","#ccc").attr("disabled",true);
				Base.loadingOn('正在提交数据');
				//提交表单
				var option = {
					'realName' : $("#realName").val(),
					'idNo' : $('#IDCard').val(),
	                'bankCardNo' : $("#bankCard").val(),
	                'bankCode' : $("#bankCode").val(),
	                'bankName' : $("#bankType").val()
				};
				//console.log(option);
				Base.post(Base.url.rootUrl + '/userAuthority', option, function(result){
					if(result == true){
						window.location.href = 'memberCenter.html';
					}
		        })
			})
		}) 
	})
});
