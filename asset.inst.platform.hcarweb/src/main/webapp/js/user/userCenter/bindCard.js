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
			//银行卡号、银行卡类型
			var bankCard=$("#bankCard").val();
			var phone=$("#phone").val();
			var CardReg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
			var bankType = $('#bankType').val();
			var phoneReg = /^(1)\d{10}$/;
			if(bankType != '' && CardReg.test(bankCard) && phoneReg.test(phone)){
				$(".redBtns").css("background","#b38a53").removeAttr("disabled");
			}else{
				$(".redBtns").css("background","#ccc").attr("disabled",true);
			}
		}
		$(function(){
			
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
            //银行卡类型
			$("#bankType").change(function(){
				judge();
			});
            //银行卡号验证
			$("#bankCard").blur(function(){
				var bankId=$("#bankCard").val();
				var bankReg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/; 
				if(!bankReg.test(bankId)){
					Base.alertMsg('请正确输入您的银行卡号',null,function(){
		    			$("#IDCard").focus();
		    			$(".redBtns").css("background","#ccc").attr("disabled",true);
		    		});
		        	return false;
		        }
			});
			
			//点击提交按钮。进行表单校验，如果校验通过则提交表单。
			$('.redBtns').click(function(){
				Base.loadingOn('正在提交数据');
				//提交表单
				var option = {
						'bankCardNo' : $("#bankCard").val(),
		                'bankCode' : $("#bankCode").val(),
		                'bankName' : $("#bankType").val(),
		                'bankMobile':$('#phone').val()
				};
				Base.post(Base.url.rootUrl + '/pledge/user_authority', option, function(result){
					if(result == 'true'){
						window.location.href = 'memberCenter.html';
					}
		        })
			})
		}) 
	})
});
