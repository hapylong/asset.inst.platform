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
require(['json2', 'jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
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
	        			 $(".toPay").css('background','#ccc').attr('href','javascript:;');
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
	                List[i].orderName + "</span></div><div class='orderBottom'><table id='"+i+"'><tr class='tableTitle'><td>期数</td><td>本期应付(元)</td><td>最迟付款日</td><td>已付金额(元)</td><td>付款状态</td></tr>"+
	                "</table><p class='more'><img src='../../../img/arrowDown.png' class='forMore'><span class='forMoreSpan'>查看更多</span><p></div>";
	    			$(".borderArea").append(showDataHtml);
	    			var tableContent = '';
	    			for(var j=0;j<List[i].billList.length;j++){
	    				switch (List[i].billList[j].status){  
	    				case 0 :
	    					tableContent += "<tr class='tableContent'><td>"+List[i].billList[j].repayNo+"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(List[i].billList[j].lastRepayDate)+
	        				"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRealRepayamt)+"</td><td>已逾期</td></tr>";
	    					break;
	    				case 1 :
	    					tableContent += "<tr class='tableContent'><td>"+List[i].billList[j].repayNo+"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(List[i].billList[j].lastRepayDate)+
	        				"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRealRepayamt)+"</td><td>待付款</td></tr>";
	    					break;
	    				case 2 :
	    					tableContent += "<tr class='tableContent'><td>"+List[i].billList[j].repayNo+"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(List[i].billList[j].lastRepayDate)+
	        				"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRealRepayamt)+"</td><td>部分付款</td></tr>";
	    					break;
	    				case 3 : 
	    					tableContent += "<tr class='tableContent'><td>"+List[i].billList[j].repayNo+"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(List[i].billList[j].lastRepayDate)+
	        				"</td><td>"+Formatter.formatterMoney(List[i].billList[j].curRealRepayamt)+"</td><td>已付款</td></tr>";
	    					break;
	    				default :
	    					break; 
	    				} 
	    			}	
	    			$("#" + i).append(tableContent);
	            }
	        }    
	    }
	    //点击更多加载更多数据
	    $(".borderArea").delegate(".more","click",function(){
	    	var current = $(this);
	    	current.find(".forMore").toggleClass("noMore");
	    	var orderId = $(this).parent().parent().find('input').val();
	    	if(current.find(".forMore").hasClass("noMore")){
	    		if(current.prev().find('.moreContent').length == 0){
	    			//查看更多数据
		    		var option = {
		    				'orderId' : orderId
		    		};
		    		Base.post(Base.url.rootUrl + '/selectBills', option, function(result){
		    			/*Base.loadingOn();*/
		    			var result = result.result.recordList;
		    			if(result.length > 0){
		    				//有更多账单的情况下
		    				current.prev().find('.moreContent').remove();
			    			var moreTr = '';
				        	for(var i=0;i<result.length;i++){
	                          switch (result[i].status){  
	                                case 0 :
				    					moreTr += "<tr class='tableContent moreContent'><td>"+result[i].repayNo+"</td><td>"+Formatter.formatterMoney(result[i].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(result[i].lastRepayDate)+
			        				    "</td><td>"+Formatter.formatterMoney(result[i].curRealRepayamt)+"</td><td>已逾期</td></tr>";
			    					    break;
				    				case 1 :
				    					moreTr += "<tr class='tableContent moreContent'><td>"+result[i].repayNo+"</td><td>"+Formatter.formatterMoney(result[i].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(result[i].lastRepayDate)+
			        				    "</td><td>"+Formatter.formatterMoney(result[i].curRealRepayamt)+"</td><td>待付款</td></tr>";
				    					break;
				    				case 2 :
				    					moreTr += "<tr class='tableContent moreContent'><td>"+result[i].repayNo+"</td><td>"+Formatter.formatterMoney(result[i].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(result[i].lastRepayDate)+
			        				    "</td><td>"+Formatter.formatterMoney(result[i].curRealRepayamt)+"</td><td>部分付款</td></tr>";
				    					break;
				    				case 3 : 
				    					moreTr += "<tr class='tableContent moreContent'><td>"+result[i].repayNo+"</td><td>"+Formatter.formatterMoney(result[i].curRepayAmt)+"</td><td>"+Formatter.timeformatter2(result[i].lastRepayDate)+
			        				    "</td><td>"+Formatter.formatterMoney(result[i].curRealRepayamt)+"</td><td>已付款</td></tr>";
				    					break;
				    				default :
				    					break; 
			    			   }
				        	}
				        	current.prev().find(".tableContent").addClass('hidden').hide();
				        	current.prev().append(moreTr);
		    			}else{
		    				current.prev().find(".tableContent").show();
		    			}
			        })
	    		}else{
	    			current.prev().find('.hidden').hide();
	    			current.prev().find('.moreContent').show();
	    		}
	    		current.find(".forMoreSpan").html('收起');
	    	}else{
	    		current.prev().find('.moreContent').hide();
	    		current.prev().find('.hidden').show();
	    		current.find(".forMoreSpan").html('查看更多');
	    	}
	    });
	});
})