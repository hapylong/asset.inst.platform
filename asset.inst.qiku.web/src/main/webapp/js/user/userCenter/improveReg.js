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
			if((ltn.length >=2) && reg.test(ltn) && bankReg.test(bankId)){
				$(".redBtns").css("background","#f39800").removeAttr("disabled");
			}else{
				$(".redBtns").css("background","#ccc").attr("disabled",true);
			}
		}
		$(function(){
			
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
			
			//点击提交按钮。进行表单校验，如果校验通过则提交表单。
			$('.redBtns').click(function(){
				Base.loadingOn('正在提交数据');
				//提交表单
				var option = {
					'realName' : $("#realName").val(),
					'idNo' : $('#IDCard').val(),
				};
				Base.post(Base.url.rootUrl + '/pledge/update_account_info', option, function(result){
					if(result == 'true'){
						window.location.href = 'memberCenter.html';
					}
		        })
			})
		}) 
	})
});
