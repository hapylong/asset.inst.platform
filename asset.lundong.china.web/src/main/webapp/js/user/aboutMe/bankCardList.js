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
		//用于缓存全局变量
		var cache = {bankCardNum: ''};
		Base.get(Base.url.rootUrl + '/pay/getBankCardList', {}, function(result){
			$(".borderArea .bankCard").remove();
			var list = result.bankList;
			cache.bankCardNum = list.length;
			for(var i=0;i<list.length;i++){
				if(list[i].status != 1){
					var number = list[i].bankCardNo.replace(/(\w{4})(?=\w)/g,"$1 ");
					switch (list[i].bankCode){ 
					case 'BOC' : 
						var htmlStr = '';
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOC.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
						break; 
					case 'GDB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#3ec1c0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/GDB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>广发银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'SPDB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#759ad0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/SPDB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>浦发银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CMB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>招商银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'PAB' :
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#ff9a4f'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PAB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>平安银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CNCB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CNCB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中信银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CIB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#759ad0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CIB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>兴业银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'BOCOM' :
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#759ad0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BOCOM.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>交通银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CEB' :
						var htmlStr = ''; 
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CEB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国光大银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'PSBC' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#3ec1c0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/PSBC.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国邮政储蓄银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'ABC' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#3ec1c0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ABC.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国农业银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'ICBC' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/ICBC.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国工商银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CCB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#759ad0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CCB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>中国建设银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'CMBC' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#3ec1c0'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/CMBC.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>民生银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'BCCB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/BCCB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>北京银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
						$(".moreCard").before(htmlStr);
			            break;
					case 'HXB' : 
						var htmlStr = '';
						
						htmlStr += "<div class='block bankCard' style='background-color:#f84d69'><div class='imgBox'><input type='hidden' class='hidden1' value='"+list[i].bankCardNo+"'><input type='hidden' class='hidden2' value='"+list[i].status+"'><img src='../../../img/bankImg/HXB.png' class='bankImg'>"+
					    "</div><div class='TextBox'><p class='bankTitle'>华夏银行<span class='cardType'>储蓄卡</span></p><p class='number'>"+number+"</p><p class='limit'>单笔"+Formatter.money(list[i].singleLimit)+"   单日"+Formatter.money(list[i].dayLimit)+"</p></div></div>";
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
					var imgHtml = "<img src='../../../img/bind.png' style='position:absolute;right:30px;top:27px;width:55px;'>";
					$(".cardType").css("right","24%")
					$(bankLists[i]).append(imgHtml);
					$(bankLists[i]).remove();
					$(".borderArea").prepend($(bankLists[i]));
				}
			}
		})
		//更多银行卡
		$(".moreCard").click(function(){
			Base.post(Base.url.rootUrl + '/pay/bing_bankcard', {}, function(result){
				if(result == true){
					window.location.href= 'addBankCard.html'
				}else{
					Base.alertMsg(result);
				}
			})
		});
		//银行卡详情
		$(".borderArea").delegate(".bankCard","click",function(){
			  var bankTitle = $(this).find('.bankTitle').html();
			  var bankNum = $(this).find('.hidden1').val();
			  var status = $(this).find('.hidden2').val();
			  var limit = $(this).find('.limit').html();
			  var src = $(this).find(".bankImg").attr('src');
			  localStorage.setItem("src", src);
			  var url = 'bankCardDetail.html?bankTitle='+bankTitle+'&bankNum='+bankNum+'&status='+status+'&limit='+limit+'&bankCardNum='+cache.bankCardNum;
			  url = encodeURI(url);
			  window.location.href = url;
		});
	});
})
