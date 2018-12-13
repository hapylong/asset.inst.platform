require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'weui': 'weui/js/weui.min',
		'base': 'base',
		'formatter': 'formatter',
	},
	shim: {
        'json2' : {
            exports: 'JSON' 
        }
    }
});
require(['json2','jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
		var orderId = location.search.match(new RegExp("[\?\&]orderId=([^\&]+)","i"))[1];
		var riskStatus = location.search.match(new RegExp("[\?\&]riskStatus=([^\&]+)","i"))[1];
		//页面元素
		switch (Number(riskStatus)){
        case 0: 
        	$(".spanR").html('已通过');
        	$(".approvedAmt").show();
        	$("#btn-apply").hide();
        	break;
        case 1: 
        	$(".spanR").html('已拒绝');
        	$(".approvedAmt").show();
        	$("#btn-apply").hide();
        	break;
        case 2: 
        	$(".spanR").html('审核中');
        	$("#btn-apply").hide();
        	break;
        case 3: 
        	$(".spanR").html('已分期');
        	$(".approvedAmt").show();
        	$("#btn-apply").hide();
        	break;
        case 4: 
        	$(".spanR").html('待支付');
        	$(".approvedAmt").show();
        	$(".preAmt").show();
        	$(".btnBox").show();
        	$("#btn-apply").show();
        	break;
        case 5: 
        	$(".spanR").html('待确认');
        	$(".approvedAmt").show();
        	$(".btnBox").show();
        	$("#btn-apply").hide();
        	$("#quit").show();
        	$("#confirm").show();
        	break;
        default:break;
		}
		//查询订单详情
		var option = {
				'orderId' : orderId
		};
		Base.post(Base.url.rootUrl + '/order/getDetByOrderId', option, function(result){
			var orderInfo = result;
			//赋值
			$(".preAmtInput").val(orderInfo.preAmt);
			$("#merchantShortName").val(orderInfo.merchantName);
			$("#amt").val(Formatter.formatterMoney(orderInfo.applyAmt));
			$("#approvedAmt").val(Formatter.formatterMoney(orderInfo.orderAmt));
			$("#installTerms").val(orderInfo.orderItems);
		    $("#downPayment").val(Formatter.formatterMoney(orderInfo.downPayment));
		    $("#monthInterest").val(Formatter.formatterMoney(orderInfo.monthInterest));
		    $("#remark").val(orderInfo.orderRemark);
		    $("#preAmt").val(Formatter.formatterMoney(orderInfo.preAmt));
		    //默认显示一项
		    $(".pro").hide();
		    $(".pro1 .main2-r").css('border-bottom','none');
		    var proName = orderInfo.proName;
		    proName = proName.split(";");
		    var proLength = proName.length;
		    var proAmt = orderInfo.proAmt;
		    proAmt = proAmt.split(";");
		    if(proLength != 1){
		    	switch (proLength){
		        case 2: 
		        	$(".pro2").show();
		        	$(".pro1 .main2-r").css('border-bottom','1px solid #d5d5d5');
		        	$(".pro2 .main2-r").css('border-bottom','none');
		        	break;
		        case 3: 
		        	$(".pro3").show();
		        	$(".pro1 .main2-r").css('border-bottom','1px solid #d5d5d5');
		        	$(".pro3 .main2-r").css('border-bottom','none');
		        	break;
		        case 4: 
		        	$(".pro4").show();
		        	$(".pro1 .main2-r").css('border-bottom','1px solid #d5d5d5');
		        	$(".pro4 .main2-r").css('border-bottom','none');
		        	break;
		        case 5: 
		        	$(".pro5").show();
		        	$(".pro1 .main2-r").css('border-bottom','1px solid #d5d5d5');
		        	$(".pro5 .main2-r").css('border-bottom','none');
		        	break;
		        default:break;
		       }
		    }
		    for(var i=0;i<proLength;i++){
		    	$(".pro" + (i+1)).find('.mainr1').find('.item').val(proName[i]);
		    	$(".pro" + (i+1)).find('.mainr2').find('.price').val(Formatter.formatterMoney(proAmt[i]));
		    }
		});
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
		$("#btn-apply").click(function(){
			$("#mask").show();
			$(".planContent").show();
			//选择银行卡支付
	        $(".planContentItem").delegate(".bankCard","click",function(){
				//调方法到先锋支付
	        	var bankId = $(this).find('.hidden1').val();
	        	var option = {
	        			'orderId' : orderId,
	        			'bankId' : bankId
	        	};	        	
	        	var data = JSON.stringify(option);
	        	url = Base.url.rootUrl + '/pay/xfPreAmountRepay/Huahua?data=' + data;
	        	window.location.href = url;
			});
			//使用新卡
			$(".useNewCard").click(function(){
				var url = '../aboutMe/addBankCard.html?orderId=' + orderId + '&riskStatus=' + riskStatus;
				window.location.href = url;
				//添加成功跳回
			});
			$(".close").click(function(){
				$("#mask").hide();
				$(".planContent").hide();
			});
		});
		//确定核准金额并提交
		$("#confirm").click(function(){
			var preAmt = $(".preAmtInput").val();
			if(preAmt > 0){
				$("#mask").show();
				$(".planContent").show();
				//选择银行卡支付
		        $(".planContentItem").delegate(".bankCard","click",function(){
					//调方法到先锋支付
		        	var bankId = $(this).find('.hidden1').val();
		        	var option = {
		        			'orderId' : orderId,
		        			'bankId' : bankId
		        	};	
		        	//console.log(option);
		        	var data = JSON.stringify(option);
		        	url = Base.url.rootUrl + '/pay/xfPreAmountRepay/Huahua?data=' + data;
		        	window.location.href = url;
				});
				//使用新卡
				$(".useNewCard").click(function(){
					var url = '../aboutMe/addBankCard.html?orderDetails=true&orderId=' + orderId + '&riskStatus=' + riskStatus;
					window.location.href = url;
					//添加成功跳回
				});
				$(".close").click(function(){
					$("#mask").hide();
					$(".planContent").hide();
				});
			}else{
				alerts();
				$('.cb').html('确认提交您的订单吗？');
				$('.qd').click(function () {
					var option = {
		        			'orderId' : orderId
		        	};
					Base.post(Base.url.rootUrl + '/order/confirm', option, function(result){
						if(result == 'success'){
						     window.location.href = '../stageApplication/orderToMoney.html'
						}
					})
				})	
			}
		});
		//
        $("#quit").click(function(){
        	alerts();
			$('.cb').html('确认放弃您的订单吗？');
			$('.qd').click(function () {
				var option = {
	        			'orderId' : orderId
	        	};
	        	Base.post(Base.url.rootUrl + '/order/giveup', option, function(result){
					if(result == 'success'){ 
	        			window.location.href = 'myOrder.html'
					}
				})
			})	
		});
        function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	        $('.qx').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	      }
	})
});