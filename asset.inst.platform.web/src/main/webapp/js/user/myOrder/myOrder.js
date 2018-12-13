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
        }
    }
});
require(['json2', 'jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
	var phoneNum = "${phoneNum}";
	var url = ""; 
	var totalPage = 2; //一共有多少页数据
	var pageSize = 5; // 每页显示10条数据
	var currentPage = 1; // 当前第几页数据，默认为1
	var isLoading = false; // 是否正在加载更多的数据
	$(function(){
		query(1,url);
		bindScrollEvent(url);
		//请求后台，加载审核中信息
	    function query(pageNum,url){
	        if(isLoading){
	            return;
	        }else{
	            isLoading = true; // 修改状态为正在加载数据
	        }
	        var option = {
	        		 'pageSize' : pageSize,
	        		 'pageNum' : pageNum
	        };
	        Base.post(Base.url.rootUrl + '/2/myOrder', option, function(result){
	        	 // 加载信息
		         totalPage = result.pages; // 一共有多少页数据
		         currentPage = result.pageNum; // 获取当前第几页数据
		         var List = result.list; // 信息
     	         //加载数据
		         showData(List,currentPage); 
		         isLoading = false;
	        })
	         
	    }
	    //将加载的数据放在页面中
	    function showData(List,currentPage){
	        if(List.length == 0 && currentPage == 1){
	        	$(".iconBlock").show();
	        }else if(List.length != 0){
	        	$(".iconBlock").hide();
	    		for(var i=0;i<List.length;i++){
	    			if(List[i].preAmtStatus == 2){
	    				var showDataHtml = "";
	                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
	                    List[i].orderName + "</span><span class='state'>待支付</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
	                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
	                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
	                    $(".borderArea").append(showDataHtml); 
	    			}else{
	    				switch (Number(List[i].riskStatus)){
		                case 0: 
		                	var showDataHtml = "";
		                    showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已通过</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 1:
		                	var showDataHtml = "";
		                    showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已拒绝</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 2: 
		                	var showDataHtml = "";
		                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>审核中</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 3:
		                	var showDataHtml = "";
		                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已分期</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 7:
		                	var showDataHtml = "";
		                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已放款</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 10:
		                	var showDataHtml = "";
		                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已结清</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                case 11:
		                	var showDataHtml = "";
		                	showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
		                    List[i].orderName + "</span><span class='state'>已终止</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
		                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li><li>分期期数</li></ul><ul class='orderRight'><li>"+
		                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li><li>"+List[i].orderItems+"</li></ul></div></div>";
		                    $(".borderArea").append(showDataHtml);
		                    break;
		                default:break
		              }
	    			}
	          }
	        }    
	    }
	    function bindScrollEvent(url){   
	        // 添加滚动监听事件
	        $(window).scroll( function() {
	            var docHeight = $(document).height(); // 获取整个页面的高度
	            var winHeight = $(window).height(); // 获取当前窗体的高度
	            var winScrollHeight = $(window).scrollTop(); // 获取滚动条滚动的距离
	            if(docHeight<= winHeight + winScrollHeight){
	                // 加载更多的数据
	                query(currentPage+1,url);
	            }
	        });
	    }
		//订单详情
		$(".borderArea").delegate(".block","click",function(){
			  var orderId = $(this).find('.orderNumber').html();
			  var length = orderId.length;
			  orderId = orderId.substring(7,length);
			  var url = 'orderDetails.html?orderId=' + orderId;
			  window.location.href = url;
		});
	});
})


