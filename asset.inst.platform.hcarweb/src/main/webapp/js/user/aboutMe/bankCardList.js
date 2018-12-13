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
require(['json2', 'jquery',  'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
		Base.get(Base.url.rootUrl + '/pay/getBankCardList', {}, function(result){
			$(".borderArea .bankCard").remove();
			var list = result.bankList;
			for(var i=0;i<list.length;i++){
				if(list[i].status != 1){
					switch (list[i].bankCode){ 
					case 'BOC' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOC.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
						break; 
					case 'GDB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/GDB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>广发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'SPDB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/SPDB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>浦发银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CMB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>招商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'PAB' :
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PAB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>平安银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CNCB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CNCB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中信银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CIB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CIB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>兴业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'BOCOM' :
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOCOM.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>交通银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CEB' :
						var htmlStr = ''; 
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CEB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国光大银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'PSBC' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PSBC.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国邮政储蓄银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'ABC' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ABC.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国农业银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'ICBC' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ICBC.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国工商银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CCB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CCB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>中国建设银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CMBC' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMBC.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>民生银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'BCCB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BCCB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>北京银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'HXB' : 
						var htmlStr = '';
						var number = list[i].bankCardNo.substr(list[i].bankCardNo.length-4);
						htmlStr += "<div class='block bankCard'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/HXB.png' class='bankImg'>"+
					    "<div class='TextBox'><p class='bankTitle'>华夏银行储蓄卡(尾号"+number+")</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div><img src='../../../img/arrowRight.png' class='arrow'></div>";
						$(".moreCard").before(htmlStr);
			            break;
					default :
						break; 
					}
				}
			};
			//添加卡标示
			var bankLists = $(".bankCard");
			for(var i=0;i<bankLists.length;i++){
				var status = $(bankLists[i]).find('.hidden2').val();
				if(status == 3){
					var imgHtml = "<img src='../../../img/bind.png' style='position:absolute;left:0;top:0;width:55px;'>";
					$(bankLists[i]).append(imgHtml);
					$(bankLists[i]).remove();
					$(".borderArea").prepend($(bankLists[i]));
				}
			}
		})
		//更多银行卡
		$(".moreCard").click(function(){
			window.location.href= 'addBankCard.html'
		});
		//银行卡详情
		$(".borderArea").delegate(".bankCard","click",function(){
			  var bankTitle = $(this).find('.bankTitle').html();
			  bankTitle=bankTitle.substr(0,bankTitle.length-8);
			  var bankNum = $(this).find('.hidden1').val();
			  var status = $(this).find('.hidden2').val();
			  var limit = $(this).find('.limit').html();
			  var src = $(this).find(".bankImg").attr('src');
			  localStorage.setItem("src", src);
			  var url = 'bankCardDetail.html?bankTitle='+bankTitle+'&bankNum='+bankNum+'&status='+status+'&limit='+limit;
			  url = encodeURI(url);
			  window.location.href = url;
		});
	});
})
