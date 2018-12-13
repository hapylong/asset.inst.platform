require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'base': 'base',
		'formatter': 'formatter',
		'weui': 'weui/js/weui.min',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
    }
});
require(['json2', 'jquery',  'base', 'formatter', 'weui'], function(JSON, $, Base, Formatter, weui){
	$(function(){
		//获取页面其他信息
		Base.get(Base.url.rootUrl + '/about/addBankCard', {}, function(result){
			$("#realName").val(result.userInfo.realName);
			$("#idCard").val(result.userInfo.idNo);
		})
		//用于定义页面变量
		var cache = {bankTypeArr: []};
		//获取银行卡类型
		$('#bankType').on('focusin', function(e){
			if(cache.bankTypeArr.length == 0){
				Base.get(Base.url.rootUrl + '/about/addBankCard', {}, function(result){
					var bankListMore = result.bankInfoList;
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
		//保存卡
		$(".save").click(function(){
			var CardReg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
			var phoneReg = /^(1)\d{10}$/;
			var phone=$("#phone").val();
	    	var bankCardNo=$("#banNo").val();
	    	var bankCode = $("#bankType").val();
	    	if($("#bankType").val() == ''){
	    		Base.alertMsg('请选择银行卡类型',null,function(){
	    			$("#bankType").focus();
	    		});
				return false; 
	    	}
	    	if (bankCardNo == "" || !CardReg.test(bankCardNo)) {
	    		Base.alertMsg('请填写合法银行卡号',null,function(){
	    			$("#banNo").focus();
	    		});
				return false; 
			}
	    	if (phone == "" || !phoneReg.test(phone)) {
	    		Base.alertMsg('请填写合法手机号',null,function(){
	    			$("#phone").focus();
	    		});
				return false; 
			}
	    	var option = {
	    			'bankCardNo' : bankCardNo,
	    			'bankCode' : $("#bankCode").val(),
	    			'bankName' : $("#bankType").val(),
	    			'bankMobile': $('#phone').val()
	    	}; 
	    	Base.post(Base.url.rootUrl + '/pay/bandBankCard', option, function(result){
	    		Base.loadingOn();
	    		//判断是否从'去支付'跳过来
	    		var url = window.location.href;
	    		if(url.indexOf('orderId') >= 0){
	    			var orderId = location.search.match(new RegExp("[\?\&]orderId=([^\&]+)","i"))[1];
	    			window.location.href = '../myOrder/orderDetails.html?orderId='+orderId;
	    		}else if(url.indexOf('toPay') >= 0){
	    			window.location.href = '../toPay/toPay.html';
	    		}else if(url.indexOf('earlyPay') >= 0){
	    			window.location.href = '../toPay/earlyPay.html';
	    		}else if(url.indexOf('id') >= 0){
	    			//提前退租拆分支付
	    			var id = location.search.match(new RegExp("[\?\&]id=([^\&]+)","i"))[1];
	    			window.location.href = '../toPay/paySplit.html?id'+id;
	    		}else if(url.indexOf('orderId') == -1 && url.indexOf('toPay') == -1 && url.indexOf('earlyPay') == -1){
	    			window.location.href = 'bankCardList.html';
	    		}
	    	})
		});
	})
})