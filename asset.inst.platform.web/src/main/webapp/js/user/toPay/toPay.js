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
	        Base.post(Base.url.rootUrl + '/pay/selectCurrBill/Car', {}, function(result){
	        	if(result.retCode == 'error'){
	        		Base.alertMsg(result.retMsg,null,function(){});
	        	}else{
	        		if(result.retCode == 'success' && result.result != null && result.result != undefined ){
       			     var result = result.result;
       			     // 加载信息
			         var List = result; // 信息
	    	         //加载数据
			         showData(List);
			         isLoading = false;
       		         }else{
       			     $(".iconBlock").show();
       			     $(".payNow").css('background','#ccc').addClass('noTap');
       		        } 
	        	} 
	        })  
	    }
	    //将加载的数据放在页面中
	    function showData(List){
	        if(List.length == 0){
	        	$(".iconBlock").show();
	        }else if(List.length != 0){
	        	$(".iconBlock").hide();
	    		for(var i=0;i<List.length;i++){
	    			var showDataHtml = "";
	    			showDataHtml += "<div class='block'><input type='hidden' value='"+List[i].orderId+"'><div class='orderTop'><img src='../../../img/billIcon.png' class='orderIcon'><span class='orderName'>"+
	                List[i].orderName + "</span></div><div class='orderBottom'><table id='"+i+"'><tr class='tableTitle'><td></td><td>期数</td><td>本期应付(元)</td><td>最迟付款日</td><td style='position:relative;width:25%;padding-right:1rem;'>滞纳金</td></tr>"+
	                "</table></div>";
	    			$(".borderArea").append(showDataHtml);
	    			var tableContent = '';
	    			for(var j=0;j<List[i].billList.length;j++){
	    				var amt=Subtr(List[i].billList[j].curRepayAmt, List[i].billList[j].curRealRepayamt);
	    				tableContent += "<tr class='tableContent'><td><div class='checkbox'><input type='checkbox' class='checkboxInput' id='"+(String(i) + String(List[i].billList[j].repayNo))+"' name='"+(String(i) + String(List[i].billList[j].repayNo))+"' regId='"+List[i].billList[j].regId+"' orderId='"+List[i].billList[j].orderId+"' repayNo='"+List[i].billList[j].repayNo+"' amt='"+amt+"'/><label for='"+(String(i) + List[i].billList[j].repayNo)+"'></label></div></td><td>"+List[i].billList[j].repayNo+"</td><td>"+Formatter.formatterMoney(amt)+"</td><td>"+Formatter.timeformatter2(List[i].billList[j].lastRepayDate)+
	    				"</td><td style='padding-right:1rem;'>"+Formatter.formatterMoney(List[i].billList[j].curRepayOverdueInterest - List[i].billList[j].cutOverdueInterest)+"</td></tr>";
	    			}	
	    			$("#" + i).append(tableContent);
	            }
	        }    
	    }
	    function Subtr(arg1,arg2){ 
	  	  var r1,r2,m,n; 
	  	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	  	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	  	  m=Math.pow(10,Math.max(r1,r2)); 
	  	  n=(r1>=r2)?r1:r2; 
	  	  return ((arg1*m-arg2*m)/m).toFixed(n); 
	  	}
	    //tip
	    $(".borderArea").delegate(".tips","click",function(){
	    	Base.alertMsg('罚息金额=剩余本金*罚息利率*逾期天数+滞纳金',null,function(){});
	    })
	    //弹窗
	    function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
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
					var imgHtml = "<img src='../../../img/bindR.png' style='position:absolute;right:1rem;top:calc((7rem - 12px)/2);width:15px;'>";
					$(bankLists[i]).append(imgHtml);
					$(bankLists[i]).remove();
					$(".planContentItem").prepend($(bankLists[i]));
				}
			}
	    })
	    // 所有复选框点击事件
	    $(".borderArea").delegate(".checkboxInput","click",function(){
	        var curr = $(this);
	        //勾选限制
	        if($(this).prop('checked')){
	        	var prevParent = curr.parent().parent().parent().prev().first();
		        if(prevParent.hasClass('tableContent')){
		        	var prev = prevParent.find('.checkboxInput');
		        	if(!prev.prop('checked')){
		        		curr.prop('checked',false);
		        		Base.alertMsg('请勿跨期勾选！',null,function(){});
		        	}
		        }
	        }else{
	        	var nextParent = curr.parent().parent().parent().next().first();
	        	var next = nextParent.find('.checkboxInput');
	        	if(next.prop('checked')){
	        		curr.prop('checked',true);
	        		Base.alertMsg('请勿跨期勾选！',null,function(){});
	        	}
	        }
	    	GetCount();
        });    
	    function GetCount() {
	    	var conts = 0
	    	$(".checkboxInput").each(function () {
	    		if($(this).prop("checked")){
	    			conts += parseFloat($(this).attr('amt'));
	    		}
	    	})
	    	$(".amoutMoney").html(Formatter.formatterMoney(conts));
        }
	    function pay(bankCardNo){
	    	var bankCardNum = bankCardNo;
		    var c = $('input[type="checkbox"]:checked');
		    if(c.length > 0){
				var arr = [];
				$.each(c, function(i, m){
					var o = {};
				    o.regId = $(m).attr('regId');
					o.orderId = $(m).attr('orderId');				
					o.repayNo = $(m).attr('repayNo');
					o.amt = $(m).attr('amt');
					arr.push(o);
				});
				var arr2 = [];
				$.each(arr, function(i, m){
					if(!arr2.contain(m.orderId)){
						arr2.push(m.orderId);
					}
				});
				var arr3 = [];
				$.each(arr2, function(i, m){
					var o = {};
					o.orderId = m;
					o.repayModel = 'normal';
					o.sumAmt = 0;
					o.repayList = [];
					$.each(arr, function(j, n){
						if(m == n.orderId){
							o.regId = n.regId;
							o.sumAmt += parseFloat(n.amt);
							o.repayList.push(n);
						}
					});
					arr3.push(o);
				});
				var data = {
						'bankId':bankCardNum,
				        'payList' : []		
				}           
				for(var i=0;i<arr3.length;i++){
					data.payList.push(arr3[i]);
				}
           }
		    return data;
	    }
		 //支付
    	 $(".payNow").click(function(){
    		 if(!$(this).hasClass("noTap")){
    			 if($(".amoutMoney").html() != 0.00){
 	 				$("#mask").show();
 	 				$(".planContent").show();
 	 				//选择银行卡支付
 	 		        $(".planContentItem").delegate(".bankCard","click",function(){
 	 					//调用后台方法到先锋支付
 	 		        	var banKCardNo = $(this).find('.hidden1').val();
 	 		        	var data = JSON.stringify(pay(banKCardNo));
 	 		        	//console.log(data);
 	 		        	url = Base.url.rootUrl + '/pay/xfAmountRepay/2?data=' + data;
 	 		        	window.location.href = url;
 	 				});
 	 			}else if($(".amoutMoney").html() == 0.00){
 	 				Base.alertMsg('请勾选要支付的账单',null,function(){});
 	 			}
 	 			//使用新卡
 	 			$(".useNewCard").click(function(){
 	 				var url = '../aboutMe/addBankCard.html?toPay=true';
 	 				window.location.href = url;
 	 				//添加成功跳回
 	 			});
 	 			$(".close").click(function(){
 	 				$("#mask").hide();
 	 				$(".planContent").hide();
 	 			}); 
    		 }
 		});
    	 //拆分支付
    	 $('.toSplit').on('click',function(){
    		 var c = $('input[type="checkbox"]:checked');
    		 var len = c.length;
    		 var orderId = $(c[0]).attr('orderId'),regId = $(c[0]).attr('regId'),repayNo = $(c[0]).attr('repayNo'),amt = $(c[0]).attr('amt');
    		 if(len > 1){
    			 Base.alertMsgCancel('多笔账单不可拆分支付',null,function(){});
    		 }else if(len == 1){
    			 WEUI.alert('支付失败了？是否提示“交易金额不在指定范围内”?是请点击“去支付”，否请返回', {title: '', buttons: [{label: '去支付', type: 'primary', onClick: function(){
     				//单笔账单跳转拆分支付
         			 window.location.href = './topPaySplit.html?orderId='+orderId+'&regId='+regId+'&repayNo='+repayNo+'&amt='+amt;
     			 }},
                 {label: '返回', type: 'primary', onClick: function(){}}]});
    		 }
    	 });
	});
})