require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'base': 'base',
		'formatter': 'formatter',
		'weui': 'weui/js/weui.min',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
    }
});
require(['json2', 'jquery', 'base', 'formatter', 'weui'], function(JSON, $, Base, Formatter, WEUI){
	$(function(){ 
		query();
		//请求后台，加载审核中信息
		function query(){
	    	/*var option = {
	        		'openId' : '10101',
	        		'regId' : '18911908439'
	        }*/
	        Base.post(Base.url.rootUrl + '/pay/balanceAdvanceOrder', {}, function(result){
	        	 var result = result;
	        	 // 加载信息
		         var List = result; // 信息
		         //加载数据
		         showData(List);
	        })  
	    }
	    //将加载的数据放在页面中
	    function showData(List){
	        if(List.length == 0){
	        	$(".iconBlock").show();
	        	$(".payNow").css('background','#ccc').addClass('noTap');
	        }else if(List.length != 0){
	        	$(".iconBlock").hide();
	    		for(var i=0;i<List.length;i++){
	    			var showDataHtml = "";
	    			showDataHtml += "<div class='block'><div class='orderTop'><div class='checkbox'><input type='hidden' value='"+List[i].orderId+"' regId='"+List[i].regId+"'><input type='checkbox' class='checkboxInput' id='"+i+"' name='"+i+"'/><label for='"+i+"'></label></div><span class='orderName'>"+
	                List[i].orderName + "</span></div><div class='orderBottom'><p class='orderNumber'>订单号 ： "+List[i].orderId+"</p></div></div>";
	    			$(".borderArea").append(showDataHtml);
	            }
	        }    
	    }
	    //银行卡
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
					var imgHtml = "<img src='../../../img/bindR.png' style='position:absolute;right:1rem;top:calc((7rem - 12px)/2);width:15px;'>";
					$(bankLists[i]).append(imgHtml);
					$(bankLists[i]).remove();
					$(".planContentItem").prepend($(bankLists[i]));
				}
			}
	    })
		//支付
		//所有复选框点击事件
	    var orderId,regId,hiddenFee,payMethod,id;
	    $(".borderArea").delegate(".checkboxInput","click",function(){
	    	var cur = $(this);
	    	if($(this).prop('checked')){
	    		$(".checkboxInput").prop('checked',false);
	    		cur.prop('checked',true);
	    		orderId = $(this).prev().val();
	    		regId = $(this).prev().attr('regId');
	    		Base.post(Base.url.rootUrl + '/pay/reCalculate', {'orderId':orderId}, function(result){
	    			/*Base.loadingOn('正在获取详情');*/
	    			if(result.status == 'fail'){
	    				Base.alertMsg(result.errMsg,null,function(){
		    				cur.prop('checked',false);
		    			});
	    			}else if(result.retCode == 'error'){
	    				Base.alertMsg(result.retMsg,null,function(){
	    				cur.prop('checked',false);
	    				});
	    			}else{
	    				$(".paied").remove();
	    				//cur.parent().parent().next().find('.paied').remove();
		    			var details = '';
		    			details += "<input type='hidden' class='paied' value='"+result.totalRepayAmt+"'><p class='paied'  style='margin-top:1rem;'>已还本金 ： "+Formatter.formatterMoney(result.payPrincipal)+
		                "</p><p class='paied'>剩余本金 ： "+Formatter.formatterMoney(result.surplusPrincipal)+"</p><p class='paied'>违约金： "+Formatter.formatterMoney(result.finalOverdueAmt)+"</p>";
		    			//是否显示费用明细
		    			hiddenFee = result.hiddenFee;
		    			//审核状态
		    			settleStatus = result.settleStatus;
		    			//支付方式
		    			payMethod = result.payMethod;
		    			id = result.id;
		    			//累计应还
		    			needPayAmt = result.needPayAmt;
		    			//不隐藏费用明细时显示
		    			if(hiddenFee == 2 && payMethod == 1){
		    				cur.parent().parent().next().append(details);
		    			}
		    			//处理还款状态（是否要提交审核）
		    			console.log(settleStatus);
		    			if( settleStatus == 1){
		    				//不隐藏费用明细&线上支付
		    				if(hiddenFee == 2 && payMethod == 1){
		    					$('.moneyText').show();
		    					$(".amoutMoney").html(Formatter.formatterMoney(needPayAmt));
		    					$(".payNow").css('background','#354384').html('提前退租').removeClass('noTap');
		    				}else{
		    					$(".payNow").css('background','#ccc').html('正在申请').addClass('noTap');
		    				}
		    			}else if(settleStatus == '' || settleStatus == null){
		    				$(".payNow").css('background','#354384').html('提交申请').removeClass('noTap');
		    			}else if(settleStatus == 3){
		    				$(".payNow").css('background','#354384').html('提交申请').removeClass('noTap');
		    			}else if(settleStatus == 4){
		    				$(".payNow").css('background','#354384').html('提交申请').removeClass('noTap');
		    			}
	    			}
	    		})
	    	}else if(!($("this").prop('checked'))){
	    		//console.log(0);
	    		cur.parent().parent().next().find('.paied').remove();
	    		$(".payNow").css('background','#354384').html('提前退租').removeClass('noTap');
	    		$(".amoutMoney").html('0.00');
	    	}
        });    
		//支付
		$(".payNow").click(function(){
			if(!$(this).hasClass("noTap")){
				if($('.checkboxInput:checked').length > 0){
					//判断去支付或者提交申请
					if($(this).html() == '提前退租'  || (settleStatus == 1 && hiddenFee == 2 && payMethod == 1)){
						//本地缓存
						var mobileObject = {
								"regId": regId,
		        	            "orderId": orderId,
		        	            "sumAmt": $('.orderBottom input').val(),
						}
						localStorage.setItem("mobileObject",JSON.stringify(mobileObject));
						window.location.href = './paySplit.html?id='+id+'&orderId='+orderId;
					}else if($(this).html() == '提交申请'){
						//提交申请--启动工作流
						WEUI.alert('您好，您确认要提交提前还款申请吗？', {title: '', buttons: [{label: '确定', type: 'primary', onClick: function(){
							Base.post(Base.url.rootUrl + '/pay/repayAuthenticate', {'orderId':orderId}, function(result){
								if(result.status == 'success'){
									Base.loadingOn();
									Base.post(Base.url.rootUrl + '/pay/prepaymentStartWF', {'orderId':orderId}, function(result){
										if(result == '1'){
											Base.alertMsg('申请成功',null,function(){
												window.location.href = 'earlyPay.html';
											});
										}else if(result == '0'){
											Base.alertMsg('提前还款流程启动失败',null,function(){});
											Base.loadingOut();
										}
									})
								}else if(result.status == 'fail'){
									Base.alertMsg(result.errMsg,null,function(){});
								}
							})
						 }},
                         {label: '取消', type: 'primary', onClick: function(){}}]});
					}
				}else{
					 Base.alertMsg('请勾选要提前退租的订单',null,function(){});
				}
			}
		})
	});
})