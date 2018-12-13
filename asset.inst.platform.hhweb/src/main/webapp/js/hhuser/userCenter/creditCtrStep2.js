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
    	
    	//保存-绑定单击事件回调
    	$('#btn-apply').on('click', function(e){
    		if($('#fileA').val().length == 0){
    			Base.alertMsg('身份证正面照片不能为空！');
    			return false;
    		}
    		
    		if($('#fileB').val().length == 0){
    			Base.alertMsg('身份证背面照片不能为空！');
    			return false;
    		}
    		
    		if($('#fileC').val().length == 0){
    			Base.alertMsg('借款人手持身份证照片不能为空！');
    			return false;
    		}
    		
    		//加载框开启
			Base.loadingOn();
			//调用上传文件接口
			upload($('#formA'), function(result1){
				result1 = result1.replace(/\\/g, '/');
				upload($('#formB'), function(result2){
					result2 = result2.replace(/\\/g, '/');
					upload($('#formC'), function(result3){
						result3 = result3.replace(/\\/g, '/');
						var option = {idUrl: Base.url.imgUrl + result1, idUrl2: Base.url.imgUrl + result2, idUrl3: Base.url.imgUrl + result3};
						//调用保存接口
						Base.post(Base.url.rootUrl + '/2/saveStepRiskInfo', {step2: JSON.stringify(option)}, function(result){
							//跳转下一步页面
							window.location.href = Base.url.webUrl + '/views/hhuser/userCenter/creditCtrStep3.html';
						});
					});
				});
			});    		
    	});
    	
    	
    	
    	//异步上传文件
    	function upload(form, callback){
    		form.ajaxSubmit({
    			url: Base.url.rootUrl + '/fileUpload/upload/PIC/hh',
    			type: 'post',
    			async: true,
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
    	
    	
    }); 
});