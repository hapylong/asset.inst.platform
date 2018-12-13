require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
	},
	shim: { 
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2','jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
		var orderId = localStorage.getItem("orderId");
		//查询订单详情
		var option = {
				'orderId' : orderId
		};
		Base.post(Base.url.rootUrl + '/getOrderInfo', option, function(result){
			var orderInfo = result.orderInfo;
			//赋值
			$("#orderName").html(orderInfo.orderName);
			$("#orderNo").html(orderInfo.orderId);
			$("#instalAmt").html(Formatter.formatterMoney(orderInfo.orderAmt));
			$("#monthPer").html(Formatter.formatterMoney(orderInfo.monthInterest));
		    //分期详情
			var detail = orderInfo.orderCurrItem +'/'+orderInfo.orderItems;
			$("#stageDetail").html(detail);
			$("#downPayment").html(Formatter.formatterMoney(orderInfo.downPayment));
			$("#margin").html(Formatter.formatterMoney(orderInfo.margin));
			$("#serviceFee").html(Formatter.formatterMoney(orderInfo.serviceFee));
			$("#finalPayment").html(Formatter.formatterMoney(orderInfo.feeAmount));
			$("#orderTime").html(Formatter.timeformatter(orderInfo.createTime));
			var contractStatus = orderInfo.contractStatus;
			if(contractStatus == 1){
				$('.sr').html('待签约');
			}else if(contractStatus == 2){
				$('.sr').html('已签约');
			}
		});
		//查看合同
		$('.forContract').click(function(){
			var url = 'signContract.html?orderId=' + orderId;
			window.location.href = url;
		});
	})
});