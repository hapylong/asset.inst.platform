/**
 * 主要用于定义URL与AJAX,基础,公共基础类函数均可在此定义
 * 引入:jQuery库/JSON2库/自定义常量库/WEUI库
 * */
define(['jquery', 'json2', 'cons', 'weui'], function($, JSON, CONS, weui){
	
	var _this = {
		url: {
			rootUrl: CONS.protocol + '://' + CONS.hostname + ':' + CONS.port + '/asset.inst.platform.front',
			webUrl: CONS.protocol + '://' + CONS.hostname + ':' + CONS.port + '/asset.lundong.china.web',
			imgUrl: CONS.protocol + '://' + CONS.hostname + ':' + CONS.port
		},
		/** 网页跳转 **/
		redirectUrl: function(url){
			location.href = url;
		},
		/** 是否存在值 **/
		isNotEmpty: function(val){
			if(val == undefined || val == ''){
				return false;
			}
			return true;
		},
		/*提示*/
		alertMsg: function(msg, title, callback){
			weui.alert(msg, {title: title, buttons: [{label: '确定', type: 'primary', onClick: function(){if($.isFunction(callback)){callback();}}}]});
		},
		/*提示*/
		alertMsgCancel: function(msg, title, callback){
			weui.alert(msg, {title: title, buttons: [{label: '取消', type: 'primary', onClick: function(){if($.isFunction(callback)){callback();}}}]});
		},
		loading: null,
		/*开始加载*/
		loadingOn: function(title){
			var remine = title || '操作中';
			_this.loading = weui.loading(remine, {className: 'custom-classname'});
			setTimeout(function(){_this.loading.hide();}, 8000);
		},
		/*加载结束*/
		loadingOut: function(){
			_this.loading.hide();
		},
		/*toast*/
		toast: function(title){
			var remine = title || '操作成功';
			weui.toast(remine, 1500);
		},
		/*重新登录*/
		toLogin: function(){
			//跳转首页
			window.top.location = _this.url.webUrl + '/views/user/login/login.html';
		},
		/*回退*/
		rollBack: function(){
			window.history.go(-1);
		},
		/*展示错误信息*/
		showInfo: function(data){
			if(_this.loading){
				_this.loading.hide();
			}
			_this.alertMsg(data.retUserInfo + '！');
		},
		/*登录检查*/
		checkLogin: function(data){
			if(data == 'unlogin'){
				return false;
			}
			return true;
		},
		/*权限检查*/
		checkAuth: function(data){
			return true;
		},
		/*系统和业务检查*/
		checkSuccess: function(data){
			if(data.success != 1){
				return false;
			}
			return true;
		},
		/*AJAX提交JSON*/
		ajaxJson: function(url, type, async, option, callback){
			$.ajax({
				url: url,
				type: type,
				async: async,
				cache: false,
				data: JSON.stringify(option),
				dataType: 'json',
				contentType: 'application/json',
				beforeSend: function(){},
				complete: function(){},
			 	success: function(data){//请求成功
			 		if(!_this.checkLogin(data)){
			 			_this.toLogin();
			 			return false;
			 		}	
			 		if(!_this.checkAuth(data)){
			 			_this.rollBack();
		 				return false;
		 			}
			 		if(!_this.checkSuccess(data)){
			 			_this.showInfo(data);
			 			return false;
			 		}	
			 		if($.isFunction(callback)){
			 			callback(data.iqbResult.result);
			 		}
			 	},
			 	error: function(response, textStatus, errorThrown){//请求失败
			 		try{		 
			 			if(!_this.checkLogin(response.responseText)){
				 			_this.toLogin();
				 			return false;
				 		}	
			 			if(_this.loading){
							_this.loading.hide();
						}
			 			if(response.readyState == 4 && response.status == 404){
			 				_this.alertMsg('网络异常，连接服务器失败');
			 			}else{
			 				_this.alertMsg('系统异常，请联系管理员');
			 			}
			 		}catch(e){
			 			_this.alertMsg('系统异常，请联系管理员');
			 		}
			 	}
			});
		},
		ajaxJsonIfFail: function(url, type, async, option, callback){
			$.ajax({
				url: url,
				type: type,
				async: async,
				cache: false,
				data: JSON.stringify(option),
				dataType: 'json',
				contentType: 'application/json',
				beforeSend: function(){},
				complete: function(){},
			 	success: function(data){//请求成功
			 		if(!_this.checkLogin(data)){
			 			_this.toLogin();
			 			return false;
			 		}	
			 		if(!_this.checkAuth(data)){
			 			_this.rollBack();
		 				return false;
		 			}
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error: function(response, textStatus, errorThrown){//请求失败
			 		try{		 
			 			if(!_this.checkLogin(response.responseText)){
				 			_this.toLogin();
				 			return false;
				 		}	
			 			if(_this.loading){
							_this.loading.hide();
						}
			 			if(response.readyState == 4 && response.status == 404){
			 				_this.alertMsg('网络异常，连接服务器失败');
			 			}else{
			 				_this.alertMsg('系统异常，请联系管理员');
			 			}
			 		}catch(e){
			 			_this.alertMsg('系统异常，请联系管理员');
			 		}
			 	}
			});
		},
		/*GET请求*/
		get: function(url, option, callback){
			_this.ajaxJson(url, 'get', true, option, function(data){
				if($.isFunction(callback)){
		 			callback(data);
		 		}		
			});
		},
		/*同步get请求*/
		curGet: function(url, option, callback){
			_this.ajaxJson(url, 'get', false, option, function(data){
				if($.isFunction(callback)){
		 			callback(data);
		 		}		
			});
		},
		/*POST请求*/
		post: function(url, option, callback){
			_this.ajaxJson(url, 'post', true, option, function(data){
				if($.isFunction(callback)){
		 			callback(data);
		 		}		
			});
		},
		postIfFail: function(url, option, callback){
			_this.ajaxJsonIfFail(url, 'post', true, option, function(data){
				if($.isFunction(callback)){
		 			callback(data);
		 		}		
			});
		},
		/*同步POST请求*/
		asyncPost: function(url, option, callback){
			_this.ajaxJson(url, 'post', false, option, function(data){
				if($.isFunction(callback)){
					callback(data);
				}		
			});
		}
	};
	
	/*将表单数据转换为JSON对象*/
	$.fn.extend({
		serializeObject: function(){
			var o = {};
		    var a = this.serializeArray();
		    $.each(a, function(){
		        if(o[this.name]){
		            if(!o[this.name].push){
		                o[this.name] = [ o[this.name] ];
		            }
		            o[this.name].push(this.value || '');
		        }else{
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		}

	}); 
	
	/*重写javaScript日期对象原型*/
	Date.prototype.format = function(fmt){
		var o = {
			'M+': this.getMonth() + 1,//月份
			'd+': this.getDate(),//日
			'h+': this.getHours(),//小时
			'm+': this.getMinutes(),//分
			's+': this.getSeconds(),//秒
			'q+': Math.floor((this.getMonth() + 3) / 3),//季度
			'S': this.getMilliseconds()//毫秒
		};
		if(/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
		for( var k in o)
			if(new RegExp('(' + k + ')').test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
		return fmt;
	};
	
	/*重写javaScript数组对象原型*/
	Array.prototype.contain = function(e){  
		for(i = 0; i < this.length; i ++){  
			if(this[i] == e){
				return true;
			} 			  
		}  
		return false;  
	};
	
	return _this;
});
