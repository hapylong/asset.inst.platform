require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
		'validate':'validate'
	},
	shim: { 
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2','jquery', 'base', 'formatter','weui','validate'], function(JSON, $, Base, Formatter, WEUI,Validate){
	$(function(){
		var preAmt = location.search.match(new RegExp("[\?\&]preAmt=([^\&]+)","i"))[1];
		var orderId = location.search.match(new RegExp("[\?\&]orderId=([^\&]+)","i"))[1];
		$('#needToPay').val(preAmt);	
		/** 判断传参是否为空 **/
	    function isNotNull(val){
	    	if(val == ''){
	    		return false;
	    	}
	    	return true;
	    }
		/** 验证支付金额 **/
	    function validateAmt(amt){
	    	if(!isNotNull(amt)){
	    		Base.alertMsg('请输入支付金额', null, function(){
	    			$("#toPay").focus();
	    		});
	    		return false;
	    	}
	    	if(!Validate.validateAmt(amt)){
	    		Base.alertMsg('请输入有效金额', null, function(){
	    			$("#toPay").focus();
	    		});
	    		return false;
	    	}
	    	if(parseFloat(amt) > parseFloat(preAmt)){
	    		Base.alertMsg('支付金额应小于等于待支付金额', null, function(){
	    			$("#toPay").focus();
	    		});
	    		return false;
	    	}
	    	/*if(parseFloat(amt) < parseFloat(5000)){
	    		Base.alertMsg('支付金额须大于等于5000', null, function(){
	    			$("#toPay").focus();
	    		});
	    		return false;
	    	}*/
	    	return true;
	    }
		Base.get(Base.url.rootUrl + '/pay/getBankCardList', {}, function(result){
			var list = result.bankList;
			$(".planContentItem .bankCard").remove();
			for(var i=0;i<list.length;i++){
				var htmlStr = '';
				switch (list[i].bankCode){ 
				case 'BOC' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOC.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
					break; 
				case 'GDB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/GDB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>广发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'SPDB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/SPDB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>浦发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CMB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>招商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'PAB' :
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PAB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>平安银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CNCB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CNCB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中信银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CIB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CIB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>兴业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'BOCOM' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOCOM.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>交通银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CEB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CEB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国光大银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'PSBC' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PSBC.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国邮政储蓄银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'ABC' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ABC.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国农业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'ICBC' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ICBC.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国工商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CCB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CCB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>中国建设银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'CMBC' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMBC.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>民生银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'BCCB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BCCB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>北京银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				case 'HXB' : 
					var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
					htmlStr += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/HXB.png' class='bankImg'>"+
				    "<div class='TextBox'><p class='bankTitle'>华夏银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
					$(".useNewCard").before(htmlStr);
		            break;
				default :
					break; 
				} 
			};
			//添加卡标识
			var bankLists = $(".bankCard");
			for(var i=0;i<bankLists.length;i++){
				var status = $(bankLists[i]).find('.hidden2').val();
				if(status == 3){
					var imgHtml = "<img src='../../../img/bindR.png' style='position:absolute;right:0.5rem;top:calc((3.5rem - 12px)/2);width:15px;'>";
					$(bankLists[i]).append(imgHtml);
					$(bankLists[i]).remove();
					$(".planContentItem").prepend($(bankLists[i]));
				}
			}
		})
		//支付
		$(".pay").click(function(){
			if(validateAmt($('#toPay').val())){
				$("#mask").show();
				$(".planContent").show();
			}
			//选择银行卡支付
	        $(".planContentItem").delegate(".bankCard","click",function(){
				//调方法到先锋支付
	        	var bankId = $(this).find('.hidden1').val();
	        	var option = {
	        			'orderId' : orderId,
	        			'bankId' : bankId,
	        			'repayAmt':$('#toPay').val()
	        	};	        	
	        	var data = JSON.stringify(option);
	        	url = Base.url.rootUrl + '/pay/xfBreakPreAmtRepay/4?data=' + data;
	        	window.location.href = url;
			});
			//使用新卡
			$(".useNewCard").click(function(){
				var url = '../aboutMe/addBankCard.html?orderId=' + orderId;
				window.location.href = url;
				//添加成功跳回
			});
			$(".close").click(function(){
				$("#mask").hide();
				$(".planContent").hide();
			});
		});
	})
});