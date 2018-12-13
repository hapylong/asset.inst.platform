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
require(['json2','jquery', 'base', 'formatter','weui'], function(JSON, $, Base, Formatter, WEUI){
	$(function(){
		var cache = {'preAmt':0, 'orderId':''};
		var orderId = location.search.match(new RegExp("[\?\&]orderId=([^\&]+)","i"))[1];
		cache.orderId = orderId;
		//查询订单详情
		var option = {
				'orderId' : orderId
		};
		function Subtr(arg1,arg2){ 
			  var r1,r2,m,n; 
			  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
			  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
			  m=Math.pow(10,Math.max(r1,r2)); 
			  n=(r1>=r2)?r1:r2; 
			  return ((arg1*m-arg2*m)/m).toFixed(n); 
		}
		Base.post(Base.url.rootUrl + '/getOrderInfo', option, function(result){
			var orderInfo = result.orderInfo;
			//赋值
			$("#orderName").html(orderInfo.orderName);
			$("#orderNo").html(orderInfo.orderId);
			$("#instalAmt").html(Formatter.formatterMoney(orderInfo.orderAmt));
			$("#monthPer").html(Formatter.formatterMoney(orderInfo.monthInterest));
		    //分期详情
			var detail = orderInfo.orderCurrItem +'/'+orderInfo.orderItems;
			$("#stageDetail").html(detail);
			$("#downPayment").html(Formatter.formatterMoney(orderInfo.downPayment));
			$("#margin").html(Formatter.formatterMoney(orderInfo.margin));
			$("#serviceFee").html(Formatter.formatterMoney(orderInfo.serviceFee));
			$("#finalPayment").html(Formatter.formatterMoney(orderInfo.feeAmount));
			$("#orderTime").html(Formatter.timeformatter(orderInfo.createTime));
			var userInfo = result.userBean;
			$("#userName").html(userInfo.realName);
			$("#regId").html(userInfo.regId);
			$("#idCard").html(userInfo.idNo);
			if(orderInfo.preAmtStatus == 2){
				$(".toshow").show();
				$(".toSplit").show();
				var preAmt = Subtr(Formatter.null2Zero(orderInfo.preAmt),Formatter.null2Zero(orderInfo.receivedPreAmt));
				if(preAmt.indexOf('.') < 0){
					preAmt = Formatter.string2moneyfloat(preAmt);
				} 
				$(".parti-br").html(preAmt);
            	cache.preAmt = preAmt;
			}else{
				switch (Number(orderInfo.riskStatus)){
	            case 0: 
	            	$(".sr").html('已通过');
	            	break;
	            case 1: 
	            	$(".sr").html('已拒绝');
	            	break;
	            case 2: 
	            	$(".sr").html('审核中');
	            	break;
	            case 3: 
	            	$(".sr").html('已分期');
	            	break;
	            case 10: 
	            	$(".sr").html('已结清');
	            	break;
	            case 11: 
	            	$(".sr").html('已终止');
	            	break;   
	            default:break
				}
			}
		});
		//超限额
		$('.toSplit').click(function(){
			WEUI.alert('支付失败了？是否提示“交易金额不在指定范围内”？是请点击“去支付”，否请返回', {title: '', buttons: [{label: '去支付', type: 'primary', onClick: function(){window.location.href='paySplit.html?preAmt=' + cache.preAmt + '&orderId=' + cache.orderId;}},
			                                                                                            {label: '返回', type: 'primary', onClick: function(){}}]});
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
		$(".pay").click(function(){
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
	        	url = Base.url.rootUrl + '/pay/xfPreAmountRepay/11?data=' + data;
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