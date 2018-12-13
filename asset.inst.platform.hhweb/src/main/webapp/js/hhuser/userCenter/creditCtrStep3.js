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
    	
    	//用于缓存页面变量(上传文件次序)
    	var cache = {count: 1};
    	
    	//浏览增信照片
    	$(document).on('click', '.img2', function(e){
    		if(cache.count > 1){
    			var gallery = weui.gallery($(e.target).prop('src'), {className: 'custom-classname', onDelete: function(){$(e.target).parent().remove();gallery.hide();var id = '#' + $(e.target).attr('for');$(id).parent().remove();}});
    		}    		   		
    	});
    	
    	//监听文件域(增信照片)
    	$(document).on('change', 'input', function(e){
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
					    		$('.IdCardBcak').before(img_html);
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
    	
    	//上传增信照片
    	$('.IdCardBcak').find('img').on('click', function(e){
    		if($('.IdCardFace').length < 6){  
    			if($('#file' + cache.count).length == 0){
    				var file_html = '<form id="form' + cache.count + '">' +
										'<input type="file" class="hidden" id="file' + cache.count + '" name="file" accept="image/*" />' + 
									'</form>';
					$('body').append(file_html);
    			}
    			$('#file' + cache.count).click();   			
    		}else{
    			Base.alertMsg('最多只能上传6张增信照片！');
    		}    		
    	});
    	
    	//保存-绑定单击事件回调
    	$('#btn-apply').on('click', function(e){
    		//加载框开启
			Base.loadingOn();
			var option = {};
    		if(($('.IdCardFace').length > 0) && (cache.count > 1)){    			
    			var otherImgUrlArr = [];        	
            	$.each($('.img2'), function(i, n){
        			  var id = '#' + $(n).attr('for');
        			  //调用上传文件接口
    				  upload($(id).parent(), function(result){
    					  otherImgUrlArr.push(Base.url.imgUrl + result.replace(/\\/g, '/'));
    	        	  });        			  
            	});   
            	option.otherImgUrl = otherImgUrlArr.join(',');          	
    		}else{
    			option.otherImgUrl = '';
    		}
    		//调用保存接口
    		save({step3: JSON.stringify(option)});
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
    		Base.post(Base.url.rootUrl + '/3/saveStepRiskInfo', option, function(result){
    			//跳转中心页
				window.location.href = Base.url.webUrl + '/views/hhuser/userCenter/memberCenter.html';
			});
    	}
    	
    });
});