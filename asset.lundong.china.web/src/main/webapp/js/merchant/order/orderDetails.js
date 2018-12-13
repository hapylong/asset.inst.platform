require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base',
		'formatter': 'formatter'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base', 'formatter'], function($, Base, Formatter){
	
	$(function(){
		//查询页面入参
		function getQueryString(name){
			var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		};
		
		//调用查询订单详情接口
		Base.post(Base.url.rootUrl + '/getOrderInfo', {orderId: getQueryString('orderId')}, function(result){			
			//订单状态
			$('.sr').text(Formatter.formatterOrderStatus(result.orderInfo.riskStatus));
			
			//订单信息
			$('#orderId').text(result.orderInfo.orderId);
			$('#orderName').text(result.orderInfo.orderName);
			$('#orderAmt').text(Formatter.formatterMoney(result.orderInfo.orderAmt));			
			$('#stageDetail').text(result.orderInfo.orderCurrItem + '/' + result.orderInfo.orderItems);
			$('#orderRemark').text(result.orderInfo.orderRemark);
			$('#downPayment').text(Formatter.formatterMoney(result.orderInfo.downPayment));
			$('#feeAmount').text(Formatter.formatterMoney(result.orderInfo.feeAmount));
		    $("#monthPer").text(Formatter.formatterMoney(result.orderInfo.monthInterest));
		    $('#margin').text(Formatter.formatterMoney(result.orderInfo.margin));
		    $('#serviceFee').text(Formatter.formatterMoney(result.orderInfo.serviceFee));
		    $('#orderTime').text(Formatter.datastampToString(result.orderInfo.createTime));
		    
		    //个人信息
			$('#realName').text(result.userBean.realName);
			$('#regId').text(result.userBean.regId);
			$('#idNo').text(result.userBean.idNo);
		});
	});
	
});