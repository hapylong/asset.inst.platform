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

require(['jquery', 'base', 'formatter', 'weui', 'validate'], function($, Base, Formatter, weui, Validate){
	
	$(function(){
		
		//查询页面入参
		function getQueryString(name){
			var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		};
		var id = location.search.match(new RegExp("[\?\&]id=([^\&]+)","i"))[1];
		//用于缓存全局变量(订单号)
		var cache = {orderId: '',id:id}
		
		//调用查询订单接口
		Base.post(Base.url.rootUrl + '/order/getOrderInfoById', {id: getQueryString('id')}, function(result){
			$('#merchantShortName').val(result.merchantName);
			$('#orderName').val(result.proName);
			$('#monthAmount').val(result.monthInterest);
			$('#margin').val(result.margin);
			$('#remark').val(result.orderRemark);
			
			$('#amt').val(result.preAmt);
			
			cache.orderId = result.orderId;
		});	
		
		//银行卡列表
		Base.get(Base.url.rootUrl + '/pay/getBankCardList', {}, function(result){
	    	var list = result.bankList;
			$('.planContentItem .bankCard').remove();
	    	for(var i = 0; i < list.length; i ++){
	    		var html = '';
	    		switch (list[i].bankCode){ 
		    		case 'BOC': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOC.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		    			break; 
		    		case 'GDB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/GDB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>广发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'SPDB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/SPDB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>浦发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CMB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>招商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'PAB':
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PAB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>平安银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CNCB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CNCB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中信银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CIB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CIB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>兴业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'BOCOM': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOCOM.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>交通银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CEB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CEB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国光大银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'PSBC': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PSBC.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国邮政储蓄银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'ABC': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ABC.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国农业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'ICBC': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ICBC.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国工商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CCB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CCB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>中国建设银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'CMBC': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMBC.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>民生银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'BCCB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BCCB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>北京银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		case 'HXB': 
		    			var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
		    			html += "<div class='bankCard'><input type='hidden' class='hidden1' value='"+list[i].id+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/HXB.png' class='bankImg'>"+
		    		    "<div class='TextBox'><p class='bankTitle'>华夏银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
		    			$(".useNewCard").before(html);
		                break;
		    		default:
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
	    //立即支付-绑定单击事件回调
		$('#btn-apply').on('click', function(e){
			$('#mask').show();
			$('.planContent').show();     
			//使用新卡付款-绑定单击事件回调
			$('.useNewCard').on('click', function(e){
				window.location.href = Base.url.webUrl + '/views/hhuser/aboutMe/addBankCard.html?housingApplicationToPay=true&'+'id='+ cache.id;
			});
		    
			//选择银行卡支付-绑定单击事件回调
			$(".planContentItem").delegate(".bankCard","click",function(){
	        	var bankId = $(this).find('.hidden1').val();
	        	var option = {
	        			'orderId' : cache.orderId,
	        			'bankId' : bankId
	        	};	
	        	var data = JSON.stringify(option);
	        	url = Base.url.rootUrl + '/pay/xfPreAmountRepay/Huahua?data=' + data;
	        	window.location.href = url;
			});
			//点击关闭
			$(".close").click(function(){
				$("#mask").hide();
				$(".planContent").hide();
			});
		});
	});
	
});