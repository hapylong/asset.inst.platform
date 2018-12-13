require.config({
    baseUrl: '../../../js/lib',
    paths: {
        'cons': 'cons',
        'json2': 'json2',
        'weui': 'weui/js/weui.min',
        'jquery': 'jquery-1.11.3.min',  
        'base': 'base',
        'formatter': 'formatter',
        'validate': 'validate',
        'jquery.form': 'jquery.form'
    },
    shim: {
        'json2': {
            exports: 'JSON'
        },
        'jquery.form': {
            deps: ['jquery']
        }
    }
});
require(['jquery', 'base', 'formatter', 'weui', 'validate', 'json2', 'jquery.form'], function($, Base, Formatter, weui, Validate, JSON){
	
	$(function(){
		
		/** 当前页面业务较为复杂,如后续维护人员存在维护困难问题,可联系我(1434943506) **/
		
		//缓存页面全局变量(上传文件次序,婚姻状况,性别,文化水平)
    	var cache = {count: 0, marriedStatusArr: [{label: '未婚', value: '未婚-UNMARRIED'}, {label: '已婚，有子女', value: '已婚，有子女-MARRIED'}, {label: '已婚，无子女', value: '已婚，无子女-MARRIED_NONE'}, {label: '离异', value: '离异-DISSOCIATON'}], sexArr: [{label: '男', value: '男'}, {label: '女', value: '女'}], culturalLevelArr: [{label: '小学及以下', value: '小学及以下-PRIMARY'}, {label: '初中', value: '初中-MIDDLE'}, {label: '高中', value: '高中-JUNIOR'}, {label: '中等技术学校', value: '中等技术学校-TECHNOLOGY'}, {label: '大专', value: '大专-JC'}, {label: '本科', value: '本科-COLLEGE'}, {label: '硕士及以上', value: '硕士及以上-MASTER'}]};
		
    	//调用查询风控详情接口
		Base.post(Base.url.rootUrl + '/2/queryRiskInfo', {}, function(result){
			
			//基本信息字符串转对象
			var info = $.parseJSON(result.riskInfo.checkInfo);
			
			$('#marriedStatus').val(Formatter.formatterMarriedStatus(info.marriedStatus));
			$('#marriedStatusValue').val(info.marriedStatus);
			$('#sex').val(info.sex);
			$('#culturalLevel').val(Formatter.formatterCulturalLevel(info.culturalLevel));
			$('#culturalLevelValue').val(info.culturalLevel);
			$('#regAddr').val(info.regAddr);
			$('#company').val(info.company);
			$('#phoneNum').val(info.phoneNum);
			$('#income').val(info.income);
			
			$('#contactName1').val(info.contactName1);
			$('#contactMobel1').val(info.contactMobel1);
			$('#contactName2').val(info.contactName2);
			$('#contactMobel2').val(info.contactMobel2);
			
			$('#btn_file1').prop('src', info.idUrl);
			$('#btn_file2').prop('src', info.idUrl2);
			$('#btn_file3').prop('src', info.idUrl3);
			
			//增信信息
			if(info.otherImgUrl.length > 0){	
				cache.count ++;
				$.each(info.otherImgUrl.split(','), function(i, n){					
					var img_html = '<li class="IdCardFace">' +
										'<img class="img2" for="file' + cache.count + '" src="' + n + '" />' + 
								   '</li>';
					$('.page3').find('.IdCardBcak').before(img_html);  
					cache.count ++;
				});				
			}else{
				var img_html = '<li class="IdCardFace">' +
									'<img class="img2 hidden" for="file0" src="../../../img/qualifications1.png" />' + 
							   '</li>';
				$('.page3').find('.IdCardBcak').before(img_html);  
			}
		});	
		
		//返回-绑定单击事件回调
		$('#btn-back').on('click', function(e){
			//跳转中心页
			window.location.href = Base.url.webUrl + '/views/hhuser/userCenter/memberCenter.html';
		});
		
		//修改-绑定单击事件回调
		$('#btn-edit').on('click', function(e){
			
			$('#marriedStatus').next().removeClass('hidden');
			$('#sex').next().removeClass('hidden');
			$('#culturalLevel').next().removeClass('hidden');
			
			$('#regAddr').prop('readonly', false);
			$('#company').prop('readonly', false);
			$('#phoneNum').prop('readonly', false);
			$('#income').prop('readonly', false);
			
			$('#contactName1').prop('readonly', false);
			$('#contactMobel1').prop('readonly', false);
			$('#contactName2').prop('readonly', false);
			$('#contactMobel2').prop('readonly', false);
			
			$('.img2').removeClass('hidden');
			$('.page3').find('.IdCardBcak').removeClass('hidden');			
			
			$('#btn-back').addClass('hidden');
			$('#btn-edit').addClass('hidden');
			$('#btn-esc').removeClass('hidden');
			$('#btn-save').removeClass('hidden');
			
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
	    	
	    	//上传,浏览身份证正面照片
	    	$('#btn_file1').on('click', function(e){
	    		if($('#btn_file1').hasClass('img1')){
	    			$('#fileA').click();
	    		}else{
	    			var gallery = weui.gallery($('#btn_file1').prop('src'), {className: 'custom-classname', onDelete: function(){$('#btn_file1').prop('src', '../../../img/IdCardFace.png');gallery.hide();$('#btn_file1').addClass('img1');$('#fileA').val('');}});
	    		}	
	    	});
	    	
	    	//监听文件域(身份证正面照片)
	    	$('#fileA').on('change', function(e){
	    		if(e.target.files.length){
	    			var file = e.target.files[0]
	        		if(Validate.validateFileType(file.type)){
	        			if(Validate.validateFileSize(file.size)){
	        				var reader = new FileReader();
	        			    reader.onload = function(result){
	        			        $('#btn_file1').prop('src', result.target.result).removeClass('img1');
	        			    }
	        			    reader.readAsDataURL(file);
	            		}else{
	            			$('#fileA').val('');
	            			Base.alertMsg('当前文件大小已超过2M！');
	            		}
	        		}else{
	        			$('#fileA').val('');
	        			Base.alertMsg('当前文件为无效文件类型！');
	        		}
	    		}    		
	    	});
	    	
	    	//上传,浏览身份证背面照片
	    	$('#btn_file2').on('click', function(e){
	    		if($('#btn_file2').hasClass('img1')){
	    			$('#fileB').click();
	    		}else{
	    			var gallery = weui.gallery($('#btn_file2').prop('src'), {className: 'custom-classname', onDelete: function(){$('#btn_file2').prop('src', '../../../img/IdCardBcak.png');gallery.hide();$('#btn_file2').addClass('img1');$('#fileB').val('');}});
	    		}
	    	});
	    	
	    	//监听文件域 (身份证背面照片)
	    	$('#fileB').on('change', function(e){
	    		if(e.target.files.length){
	    			var file = e.target.files[0]
	        		if(Validate.validateFileType(file.type)){
	        			if(Validate.validateFileSize(file.size)){
	        				var reader = new FileReader();
	        			    reader.onload = function(result){
	        			        $('#btn_file2').prop('src', result.target.result).removeClass('img1');
	        			    }
	        			    reader.readAsDataURL(file);
	            		}else{
	            			$('#fileB').val('');
	            			Base.alertMsg('当前文件大小已超过2M！');
	            		}
	        		}else{
	        			$('#fileB').val('');
	        			Base.alertMsg('当前文件为无效文件类型！');
	        		}
	    		}
	    	});
	    	
	    	//上传,浏览借款人手持身份证照片
	    	$('#btn_file3').on('click', function(e){
	    		if($('#btn_file3').hasClass('img1')){
	    			$('#fileC').click();
	    		}else{
	    			var gallery = weui.gallery($('#btn_file3').prop('src'), {className: 'custom-classname', onDelete: function(){$('#btn_file3').prop('src', '../../../img/IdCard.png');gallery.hide();$('#btn_file3').addClass('img1');$('#fileC').val('');}});
	    		}
	    	});    	
	    	
	    	//监听文件域(借款人手持身份证照片)
	    	$('#fileC').on('change', function(e){
	    		if(e.target.files.length){
	    			var file = e.target.files[0]
	        		if(Validate.validateFileType(file.type)){
	        			if(Validate.validateFileSize(file.size)){
	        				var reader = new FileReader();
	        			    reader.onload = function(result){
	        			        $('#btn_file3').prop('src', result.target.result).removeClass('img1');
	        			    }
	        			    reader.readAsDataURL(file);
	            		}else{
	            			$('#fileC').val('');
	            			Base.alertMsg('当前文件大小已超过2M！');
	            		}
	        		}else{
	        			$('#fileC').val('');
	        			Base.alertMsg('当前文件为无效文件类型！');
	        		}
	    		}
	    	});
	    	
	    	//浏览增信照片
	    	$(document).on('click', '.img2', function(e){
	    		if(cache.count > 0){
	    			var gallery = weui.gallery($(e.target).prop('src'), {className: 'custom-classname', onDelete: function(){$(e.target).parent().remove();gallery.hide();var id = '#' + $(e.target).attr('for');$(id).parent().remove();}});
	    		}    		   		
	    	});	
	    	
	    	//上传增信照片
	    	$('.page3').find('.IdCardBcak').find('img').on('click', function(e){
	    		if($('.page3').find('.IdCardFace').length < 6){  
	    			if($('#file' + cache.count).length == 0){
	    				var file_html = '<form id="form' + cache.count + '">' +
											'<input type="file" class="hidden file" id="file' + cache.count + '" name="file" accept="image/*" />' + 
										'</form>';
						$('body').append(file_html);
	    			}
	    			$('#file' + cache.count).click();   			
	    		}else{
	    			Base.alertMsg('最多只能上传6张增信照片！');
	    		}    		
	    	});
	    	
	    	//监听文件域(增信照片)
	    	$(document).on('change', '.file', function(e){
	    		if(e.target.files.length){
	    			var file = e.target.files[0]
	        		if(Validate.validateFileType(file.type)){
	        			if(Validate.validateFileSize(file.size)){
	        				var reader = new FileReader();
	        			    reader.onload = function(result){
	        			    	var id = $(e.target).prop('id');
	        			    	if($('img[for="' + id + '"]').length > 0){
	        			    		$('img[for="' + id + '"]').prop('src', result.target.result);
	        			    	}else{
	        			    		var img_html = '<li class="IdCardFace">' +
						    							'<img class="img2" for="file' + cache.count + '" src="' + result.target.result + '" />' + 
						    					   '</li>';
						    		$('.page3').find('.IdCardBcak').before(img_html);
	        			    	}
	        			    	cache.count ++;     			    	
	        			    }
	        			    reader.readAsDataURL(file);
	            		}else{
	            			$(e.target).parent().remove();
	            			Base.alertMsg('当前文件大小已超过2M！');
	            		}
	        		}else{
	        			$(e.target).parent().remove();
	        			Base.alertMsg('当前文件为无效文件类型！');
	        		}
	    		}
	    	});    	
		});
		
		
		
		//取消-绑定单击事件回调
		$('#btn-esc').on('click', function(e){
			window.location.reload();
		});
		
		//保存-绑定单击事件回调
		$('#btn-save').on('click', function(e){
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
        	
        	if($('#btn_file1').prop('src').indexOf('IdCardFace') > 0){
        		Base.alertMsg('身份证正面照片不能为空！');
        		return false;
        	}
        	
        	if($('#btn_file2').prop('src').indexOf('IdCardBcak') > 0){
        		Base.alertMsg('身份证背面照片不能为空！');
        		return false;
        	}
        	
        	if($('#btn_file3').prop('src').indexOf('IdCard') > 0){
        		Base.alertMsg('借款人手持身份证照片不能为空！');
        		return false;
        	}
        	
        	var option = {marriedStatus: marriedStatusValue, sex: sex, culturalLevel: culturalLevelValue, regAddr: regAddr, company: company, phoneNum: phoneNum, income: income, contactName1: contactName1, contactMobel1: contactMobel1, contactName2: contactName2, contactMobel2: contactMobel2};		
        	
        	//加载框开启
			Base.loadingOn();
			
			//调用上传文件接口
        	if($('#fileA').val().length > 0){
        		upload($('#formA'), function(result){
        			option.idUrl = Base.url.imgUrl + result.replace(/\\/g, '/');
        		});
        	}else{
        		option.idUrl = $('#btn_file1').prop('src');
        	}
        	
        	if($('#fileB').val().length > 0){
        		upload($('#formB'), function(result){
        			option.idUrl2 = Base.url.imgUrl + result.replace(/\\/g, '/');
        		});
        	}else{
        		option.idUrl2 = $('#btn_file2').prop('src');
        	}
        	
        	if($('#fileC').val().length > 0){
        		upload($('#formC'), function(result){
        			option.idUrl3 = Base.url.imgUrl + result.replace(/\\/g, '/');
        		});
        	}else{
        		option.idUrl3 = $('#btn_file3').prop('src');
        	}
        	
        	if(($('.page3').find('.IdCardFace').length > 0) && (cache.count > 1)){    			
    			var otherImgUrlArr = [];        	
            	$.each($('.img2'), function(i, n){
        			  var id = '#' + $(n).attr('for');
        			  if($(id).length > 0){
        				  upload($(id).parent(), function(result){
        					  otherImgUrlArr.push(Base.url.imgUrl + result.replace(/\\/g, '/'));
        	        	  });
        			  }else{
        				  otherImgUrlArr.push($(n).prop('src'));
        			  }       			  
            	});   
            	option.otherImgUrl = otherImgUrlArr.join(',');          	
    		}else{
    			option.otherImgUrl = '';
    		}
        	
        	//调用保存接口
        	save({checkInfo: JSON.stringify(option)});
		});
		
		
		//同步上传文件
		function upload(form, callback){
    		form.ajaxSubmit({
    			url: Base.url.rootUrl + '/fileUpload/upload/PIC/hh',
    			type: 'post',
    			async: false,
    			cache: false,
    			dataType: 'json',
    			contentType: 'multipart/form-data',
    			beforeSend: function(){},
    			complete: function(){},
    		 	success: function(data){//请求成功
    		 		if(!Base.checkLogin(data)){
    		 			Base.toLogin();
    		 			return false;
    		 		}	
    		 		if(!Base.checkAuth(data)){
    		 			Base.rollBack();
    	 				return false;
    	 			}
    		 		if(!Base.checkSuccess(data)){
    		 			Base.showInfo(data);
    		 			return false;
    		 		}	
    		 		if($.isFunction(callback)){
    		 			callback(data.iqbResult.result);
    		 		}
    		 	},
    		 	error: function(response, textStatus, errorThrown){//请求失败
    		 		try{		 
    		 			if(!Base.checkLogin(response.responseText)){
    			 			Base.toLogin();
    			 			return false;
    			 		}	
    		 			if(Base.loading){
    						Base.loading.hide();
    					}
    		 			if(response.readyState == 4 && response.status == 404){
    		 				Base.alertMsg('网络异常，连接服务器失败');
    		 			}else{
    		 				Base.alertMsg('系统异常，请联系管理员');
    		 			}
    		 		}catch(e){
    		 			Base.alertMsg('系统异常，请联系管理员');
    		 		}
    		 	}
    		});
    	}
    	
    	function save(option){
    		Base.post(Base.url.rootUrl + '/2/updateRiskInfo', option, function(result){
    			window.location.reload();
			});
    	}
	});
});