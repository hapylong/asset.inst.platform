require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'LCalendar': 'LCalendar',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base',
		'formatter': 'formatter',
		'validate': 'validate'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        },
        'LCalendar': {
            exports: 'LCalendar'
        }
    }
});

require(['jquery', 'base', 'formatter', 'weui', 'validate', 'LCalendar'], function($, Base, Formatter, weui, Validate, LCalendar){
	
	$(function(){
		
		//用于缓存全局变
		var cache = {carTypeArr: [], merchantArr: []};
		var calendar = new LCalendar();
	    calendar.init({
	        'trigger': '#firstDate', 
	        'type': 'date',
	        'minDate': '1900-1-1',
	        'maxDate': new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() //最大日期
	    });
		
		//商户-绑定获取焦点事件回调
		$('#merchantShortName').on('focusin', function(e){
			if(cache.merchantArr.length == 0){
				Base.post(Base.url.rootUrl + '/2200/getMerchantList', {}, function(result){
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
				weui.picker(cache.merchantArr, {className: 'custom-classname', defaultValue: [cache.merchantArr[0].value], onChange: function(){}, onConfirm: function(value){
					$('#merchantShortName').val((value[0].split('-'))[0]);
					$('#merchantNo').val((value[0].split('-'))[1]);
				}, id: 'singleLinePicker'});
			}
		});
		//车辆类型
		$('#carType').on('focusin', function(e){
			var result = [{'carType':'新车','carTypeNo':'2'},{'carType':'二手车','carTypeNo':'1'}];
			cache.carTypeArr = [];
			$.each(result, function(i, n){
				cache.carTypeArr.push({label: n.carType, value: (n.carType + '-' + n.carTypeNo)});
			});
			weui.picker(cache.carTypeArr, {className: 'custom-classname', defaultValue: [cache.carTypeArr[0].value], onChange: function(){}, onConfirm: function(value){
				$('#carType').val((value[0].split('-'))[0]);
				$('#carTypeNo').val((value[0].split('-'))[1]);
			}, id: 'singleLinePicker'});			
		});

		//提交申请-绑定单击事件回调
		$('#btn-apply').on('click', function(e){
			var merchantNo = $('#merchantNo').val();
			if(merchantNo.length == 0){
				Base.alertMsg('商户名称不能为空！', null, function(){$('#merchantShortName').focus();});
				return false;
			}
			var projectName = $('#projectName').val();
			if(projectName.length == 0){
				Base.alertMsg('品牌不能为空！', null, function(){$('#projectName').focus();});
				return false;
			}
			var typeConfigure = $('#typeConfigure').val();
			if(typeConfigure.length == 0){
				Base.alertMsg('型号配置不能为空！', null, function(){$('#typeConfigure').focus();});
				return false;
			}
			var orderName = projectName + '-' + typeConfigure;
			var licenseNumber = $('#licenseNumber').val();
			if(licenseNumber.length == 0){
				Base.alertMsg('车牌号不能为空！', null, function(){$('#licenseNumber').focus();});
				return false;
			}
			var carTypeNo = $('#carTypeNo').val();
			if(carTypeNo.length == 0){
				Base.alertMsg('车辆类型不能为空！', null, function(){$('#carType').focus();});
				return false;
			}
			var VIN = $('#VIN').val();
			if(VIN.length == 0){
				Base.alertMsg('车架号不能为空！', null, function(){$('#VIN').focus();});
				return false;
			}
			var engineNumber = $('#engineNumber').val();
			if(engineNumber.length == 0){
				Base.alertMsg('发动机编号不能为空！', null, function(){$('#engineNumber').focus();});
				return false;
			}
			var firstDate = $('#firstDate').val();
			if(firstDate.length == 0){
				Base.alertMsg('初始登记日期不能为空！', null, function(){$('#firstDate').focus();});
				return false;
			}
			var licenseLocation = $('#licenseLocation').val();
			if(licenseLocation.length == 0){
				Base.alertMsg('上牌所在地不能为空！', null, function(){$('#licenseLocation').focus();});
				return false;
			}
			var kiloNum = $('#kiloNum').val();
			if(kiloNum.length == 0){
				Base.alertMsg('公里数不能为空！', null, function(){$('#kiloNum').focus();});
				return false;
			}
			//加载框开启
			Base.loadingOn();
			var option = {
					'merchantNo': merchantNo,
				    'orderName': orderName,
				    'plate': licenseNumber,
				    'carType': carTypeNo,
				    'carNo': VIN,
				    'engine': engineNumber,
				    'registDate': firstDate,
				    'registAdd': licenseLocation,
				    'mileage': kiloNum
			};
			Base.post(Base.url.rootUrl + '/pledge/submit_estimate_order', option, function(result){
				if(result == 'true'){
					//跳转生成订单成功页面
					window.location.href = Base.url.webUrl + '/views/user/userCenter/memberCenter.html';
		    	}
			});
		});

	});
	
});