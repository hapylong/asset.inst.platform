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
		/*获取订单id及相关信息*/
		var currentUrl = window.location.href;
		var id = location.search.match(new RegExp("[\?\&]id=([^\&]+)","i"))[1];	
		Base.post(Base.url.rootUrl + '/pledge/order_details', {'orderId':id}, function(result){
			//赋值
			$('#merchantShortName').val(result.merchantShortName);
			//拆分
			var orderName = result.orderName;
			var orderNames = orderName.split('-');
			$('#projectName').val(orderNames[0]);
			$('#typeConfigure').val(orderNames[1]);
			$('#licenseNumber').val(result.plate);
		    var carType = result.carType;
		    if(carType == 1){
		    	$('#carType').val('二手车');
		    }else if(carType == 2){
		    	$('#carType').val('新车');
		    }
			$('#VIN').val(result.carNo);
			$('#engineNumber').val(result.engine);
			$('#firstDate').val(Formatter.timeformatter3(result.registDate));
			$('#licenseLocation').val(result.registAdd);
			$('#kiloNum').val(result.mileage);
		});
	});
	
});