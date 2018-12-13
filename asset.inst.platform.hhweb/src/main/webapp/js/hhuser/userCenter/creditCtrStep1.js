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
require(['jquery', 'base', 'formatter', 'weui', 'validate', 'json2'], function($, Base, Formatter, weui, Validate, JSON){   
    $(function(){   
    	
    	//缓存页面全局变量(婚姻状况,性别,文化水平)
    	var cache = {marriedStatusArr: [{label: '未婚', value: '未婚-UNMARRIED'}, {label: '已婚，有子女', value: '已婚，有子女-MARRIED'}, {label: '已婚，无子女', value: '已婚，无子女-MARRIED_NONE'}, {label: '离异', value: '离异-DISSOCIATON'}], sexArr: [{label: '男', value: '男'}, {label: '女', value: '女'}], culturalLevelArr: [{label: '小学及以下', value: '小学及以下-PRIMARY'}, {label: '初中', value: '初中-MIDDLE'}, {label: '高中', value: '高中-JUNIOR'}, {label: '中等技术学校', value: '中等技术学校-TECHNOLOGY'}, {label: '大专', value: '大专-JC'}, {label: '本科', value: '本科-COLLEGE'}, {label: '硕士及以上', value: '硕士及以上-MASTER'}]};
    	
    	//婚姻状况-绑定获取焦点事件回调
    	$('#marriedStatus').on('focusin', function(e){
    		
    		//避免格式不正确继续弹出下拉的问题
    		var phoneNum = $.trim($('#phoneNum').val());
    		if((phoneNum.length > 0) && !(Validate.validateTelePhone(phoneNum))){
    			return false;
    		}
    		
    		var income = $.trim($('#income').val());
    		if((income.length > 0) && !(Validate.validateAmt(income))){
    			return false;
    		}
    		
    		var contactMobel1 = $.trim($('#contactMobel1').val());
    		if((contactMobel1.length > 0) && !(Validate.validateMobilePhone(contactMobel1))){
    			return false;
    		}
    		
    		var contactMobel2 = $.trim($('#contactMobel2').val());
    		if((contactMobel2.length > 0) && !(Validate.validateMobilePhone(contactMobel2))){
    			return false;
    		}
    		
    		weui.picker(cache.marriedStatusArr, {className: 'custom-classname', defaultValue: [cache.marriedStatusArr[0].value], onChange: function(value){}, onConfirm: function(value){
    			$('#marriedStatus').val((value[0].split('-'))[0]);
				$('#marriedStatusValue').val((value[0].split('-'))[1]);
    		},    			
    		id: 'marriedStatusPicker'});
    	});
    	$('#marriedStatus').next().find('img').on('click', function(e){
			$('#marriedStatus').focus();
		});
    	
    	//性别-绑定获取焦点事件回调
    	$('#sex').on('focusin', function(e){
    		
    		//避免格式不正确继续弹出下拉的问题
    		var phoneNum = $.trim($('#phoneNum').val());
    		if((phoneNum.length > 0) && !(Validate.validateTelePhone(phoneNum))){
    			return false;
    		}
    		
    		var income = $.trim($('#income').val());
    		if((income.length > 0) && !(Validate.validateAmt(income))){
    			return false;
    		}
    		
    		var contactMobel1 = $.trim($('#contactMobel1').val());
    		if((contactMobel1.length > 0) && !(Validate.validateMobilePhone(contactMobel1))){
    			return false;
    		}
    		
    		var contactMobel2 = $.trim($('#contactMobel2').val());
    		if((contactMobel2.length > 0) && !(Validate.validateMobilePhone(contactMobel2))){
    			return false;
    		}
    		
    		weui.picker(cache.sexArr, {className: 'custom-classname', defaultValue: [cache.sexArr[0].value], onChange: function(value){}, onConfirm: function(value){
    			$('#sex').val(value[0]);
    		},    			
    		id: 'sexPicker'});
    	});
    	$('#sex').next().find('img').on('click', function(e){
			$('#sex').focus();
		});
    	
    	//文化水平-绑定获取焦点事件回调
    	$('#culturalLevel').on('focusin', function(e){
    		
    		//避免格式不正确继续弹出下拉的问题
    		var phoneNum = $.trim($('#phoneNum').val());
    		if((phoneNum.length > 0) && !(Validate.validateTelePhone(phoneNum))){
    			return false;
    		}
    		
    		var income = $.trim($('#income').val());
    		if((income.length > 0) && !(Validate.validateAmt(income))){
    			return false;
    		}
    		
    		var contactMobel1 = $.trim($('#contactMobel1').val());
    		if((contactMobel1.length > 0) && !(Validate.validateMobilePhone(contactMobel1))){
    			return false;
    		}
    		
    		var contactMobel2 = $.trim($('#contactMobel2').val());
    		if((contactMobel2.length > 0) && !(Validate.validateMobilePhone(contactMobel2))){
    			return false;
    		}
    		
    		weui.picker(cache.culturalLevelArr, {className: 'custom-classname', defaultValue: [cache.culturalLevelArr[0].value], onChange: function(value){}, onConfirm: function(value){
    			$('#culturalLevel').val((value[0].split('-'))[0]);
				$('#culturalLevelValue').val((value[0].split('-'))[1]);
    		},    			
    		id: 'culturalLevelPicker'});
    	});
    	$('#culturalLevel').next().find('img').on('click', function(e){
			$('#culturalLevel').focus();
		});
    	
    	//单位座机-绑定失去焦点事件回调
    	$('#phoneNum').on('focusout', function(e){
    		var phoneNum = $.trim($('#phoneNum').val());
    		if((phoneNum.length > 0) && !(Validate.validateTelePhone(phoneNum))){
    			Base.alertMsg('当前座机为无效座机格式！', null, function(){
    				$('#phoneNum').focus();
    			});
    		}
    	});
    	
    	//月收入-绑定失去焦点事件回调
    	$('#income').on('focusout', function(e){
    		var income = $.trim($('#income').val());
    		if((income.length > 0) && !(Validate.validateAmt(income))){
    			Base.alertMsg('当前月收入为无效金额格式', null, function(){
    				$('#income').focus();
    			});
    		}
    	});
    	
    	//亲属手机-绑定失去焦点事件回调
    	$('#contactMobel1').on('focusout', function(e){
    		var contactMobel1 = $.trim($('#contactMobel1').val());
    		if((contactMobel1.length > 0) && !(Validate.validateMobilePhone(contactMobel1))){
    			Base.alertMsg('当前手机为无效手机格式！', null, function(){
    				$('#contactMobel1').focus();
    			});
    		}
    	});
    	
    	//朋友手机-绑定失去焦点事件回调
    	$('#contactMobel2').on('focusout', function(e){
    		var contactMobel2 = $.trim($('#contactMobel2').val());
    		if((contactMobel2.length > 0) && !(Validate.validateMobilePhone(contactMobel2))){
    			Base.alertMsg('当前手机为无效手机格式！', null, function(){
    				$('#contactMobel2').focus();
    			});
    		}
    	});
    	
    	//保存-绑定单击事件回调
        $('#btn-apply').on('click', function(e){
        	var marriedStatusValue = $('#marriedStatusValue').val();
        	if(marriedStatusValue.length == 0){
        		Base.alertMsg('婚姻状况不能为空！', null, function(){$('#marriedStatus').focus();});
        		return false;
        	}
        	
        	var sex = $('#sex').val();
        	if(sex.length == 0){
        		Base.alertMsg('性别不能为空！', null, function(){$('#sex').focus();});
        		return false;
        	}
        	
        	var culturalLevelValue = $('#culturalLevelValue').val();
        	if(culturalLevelValue.length == 0){
        		Base.alertMsg('文化水平不能为空！', null, function(){$('#culturalLevel').focus();});
        		return false;
        	}
        	
        	var regAddr = $.trim($('#regAddr').val());
        	if(regAddr.length == 0){
        		Base.alertMsg('常住地址不能为空！', null, function(){$('#regAddr').focus();});
        		return false;
        	}
        	
        	var company = $.trim($('#company').val());
        	if(company.length == 0){
        		Base.alertMsg('工作单位不能为空！', null, function(){$('#company').focus();});
        		return false;
        	}
        	
        	var phoneNum = $.trim($('#phoneNum').val());
        	if(phoneNum.length == 0){
        		Base.alertMsg('单位座机不能为空！', null, function(){$('#phoneNum').focus();});
        		return false;
        	}
        	
        	var income = $.trim($('#income').val());
        	if(income.length == 0){
        		Base.alertMsg('月收入不能为空！', null, function(){$('#income').focus();});
        		return false;
        	}

        	var contactName1 = $.trim($('#contactName1').val());
        	if(contactName1.length == 0){
        		Base.alertMsg('亲属姓名不能为空！', null, function(){$('#contactName1').focus();});
        		return false;
        	}
        	
        	var contactMobel1 = $.trim($('#contactMobel1').val());
        	if(contactMobel1.length == 0){
        		Base.alertMsg('亲属手机号不能为空！', null, function(){$('#contactMobel1').focus();});
        		return false;
        	}
        	
        	var contactName2 = $.trim($('#contactName2').val());
        	if(contactName2.length == 0){
        		Base.alertMsg('朋友姓名不能为空！', null, function(){$('#contactName2').focus();});
        		return false;
        	}
        	
        	var contactMobel2 = $.trim($('#contactMobel2').val());
        	if(contactMobel2.length == 0){
        		Base.alertMsg('朋友手机号不能为空！', null, function(){$('#contactMobel2').focus();});
        		return false;
        	}
        	
        	var option = {marriedStatus: marriedStatusValue, sex: sex, culturalLevel: culturalLevelValue, regAddr: regAddr, company: company, phoneNum: phoneNum, income: income, contactName1: contactName1, contactMobel1: contactMobel1, contactName2: contactName2, contactMobel2: contactMobel2};
        	
        	//加载框开启
			Base.loadingOn();
			//调用保存接口
			Base.post(Base.url.rootUrl + '/1/saveStepRiskInfo', {step1: JSON.stringify(option)}, function(result){
				//跳转下一步页面
				window.location.href = Base.url.webUrl + '/views/hhuser/userCenter/creditCtrStep2.html';
			});
        });            
    }); 
});