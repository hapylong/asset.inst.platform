
function clearIpt(){
    $('.close').click(function(){
        $(this).siblings('.login-input').attr('value','');
        $(this).hide();
    });
}
function eyeIcon(){
	$('.eyeIcon').click(function(){
	    if ($(this).hasClass('eye-close')) {
	        $(this).siblings('.login-pwd').prop('type','text');
	        $(this).removeClass('eye-close').addClass('eye-open');
	    }else if ($(this).hasClass('eye-open')){
	        $(this).siblings('.login-pwd').prop('type','password');
	        $(this).removeClass('eye-open').addClass('eye-close');
	    }
	})
}
function showX(){
    $('.login-input').keyup(function(){
        if ($(this).val()!='') {
            $(this).siblings('.close').show();
        }else if($(this).val()==''){
            $(this).siblings('.close').hide();
        }
        clearIpt();
    });
}

function phoneNum(){
    $("#phoneNum").blur(function(){
    	var userPhone = $("#phoneNum").val();
	    if(userPhone !="" && userPhone !=null ){
	        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	        if(!myreg.test(userPhone)){
	        	contect("手机号格式输入不正确");
	        	$("#phoneNum").focus();
	        	return false;
	        }else{
	       		$(".contect-warning").html("");
	       		$(".contectBox").hide();
	       		return false;
	        }
	    }else{
	    	contect("请输入手机号");
	        $("#phoneNum").focus();
	        return false;
	    }
        
    });
}

function name(){
	$(".name").blur(function(){
		var userName = $(".name").val();
		var reg = /[^\u4E00-\u9FA5]+/g; 
		if(userName == ''){
			contect("请输入姓名");
        	$(".name").focus();
        	return false;
		}else if(reg.test(userName)){
			contect("请输入真实姓名");
        	$(".name").focus();
        	return false;
		}
	});
}

function idCard(){
	$("#idCard").blur(function(){
		var idCard = $("#idCard").val();
		var bankReg = /(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
		if(idCard == ''){
			contect("请输入身份证号");
        	$("#idCard").focus();
        	return false;
		}else if(!bankReg.test(idCard)){
			contect("请输入真实身份证号");
        	$("#idCard").focus();
        	return false;
		}
	})
}

function password(){
	$("#password").blur(function(){
		var userPwd = $("#password").val();
		var myreg = /(^[A-Za-z0-9]+$)/;
		if(userPwd == "") {
			contect("请输入密码");
			$(this).focus();
			return false; 
		}else if(userPwd.length < 6 || userPwd.length > 10){
			contect("请输入6-10位密码");
			$(this).focus();
			return false; 
		}else if(!myreg.test(userPwd)){
        	contect("密码为字母和数字组合");
        	$(this).focus();
        	return false;
        }
	});
}

function rePassword(){
	$("#rePassword").blur(function(){
		var reuserPwd = $("#rePassword").val();
		var myreg = /(^[A-Za-z0-9]+$)/;
		if(reuserPwd == "") {
			contect("请确认密码");
			$(this).focus();
			return false; 
		}else if(reuserPwd.length < 6 || reuserPwd.length > 10){
			contect("请输入6-10位密码");
			$(this).focus();
			return false; 
		}else if(!myreg.test(reuserPwd)){
        	contect("密码为字母和数字组合");
        	$(this).focus();
        	return false;
        }else if($("#password").val() != $("#rePassword").val()){
			contect("两次输入的密码不一致");
			$("#password").attr('value','');
			$("#rePassword").attr('value','');
			return false; 
        }
	});
}

function contect(val){
    $(".contectBox").show();
    $(".contect-warning").show().html(val);
}

// 短信倒计时
var tim =60; // 剩余时间
function tim_Down(){
	if (tim>0) {
		$('.sendTim').show().html(tim+'s后');
			tim--;
			setTimeout("tim_Down()", 1000);
	}
	else if (tim<=0) {
		$('.sendTim').hide();
		$('.sendMsg').addClass('send-able').removeClass('send-unable');
		$('.voiceCode').addClass('voice-able').removeClass('voice-unable');
		tim=60;
	}
}

function timeformatter(val){
	if(val){
		val = parseInt(val, 10);
		var date = new Date(val);
		return date.format('yyyy年M月d日 hh:mm:ss');
	}
	return '';
}

function isNull(val){
	if(val!=""&&val!=null){
		return val;
	}
	return "";
}

function money(indata)
{
	var v;
	if(Number(indata)==0) return ("0.00");
	else v=Number(indata)
	//以下转为US模式
	var vv=parseFloat(v).toLocaleString();
	for(var i=0;i<vv.length;i++)
	{
		var c = vv.charAt(i);
		if(c=='.')
		{
			i++;
			if(i!=vv.length)
			{
				i++;
				if(i!=vv.length)
				{
					i++;
					vv=vv.substring(0,i);
				}
				else vv=vv.substring(0,i)+"0";	
			}
			else vv=vv.substring(0,i)+"00";
			return vv;
		}
	}
	vv=vv+".00";
	return vv;
}