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
	var phoneNum = "";
	var url = ""; 
	var totalPage = 2; 
	var pageSize = 5;
	var currentPage = 1;
	var isLoading = false;
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
	        Base.post(Base.url.rootUrl + '/2/myContractOrder', option, function(result){
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
	    			var showDataHtml = "";
	    			if(List[i].contractStatus == 1){
	    				showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
	                    List[i].orderName + "</span><span class='state'>待签约</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
	                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li></ul><ul class='orderRight'><li>"+
	                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li></ul></div></div>";
	    			}else if(List[i].contractStatus == 2){
	    				showDataHtml += "<div class='block'><div class='orderTop'><img src='../../../img/orderIcon.png' class='orderIcon'><span class='orderName'>"+
	                    List[i].orderName + "</span><span class='state'>已签约</span></div><div class='orderBottom'><p class='orderNumber'>订单编号 : "+List[i].orderId+
	                    "</p><ul class='orderLeft'><li>订单金额</li><li>每期分摊</li></ul><ul class='orderRight'><li>"+
	                    Formatter.formatterMoney(List[i].orderAmt)+"元</li><li>"+Formatter.formatterMoney(List[i].monthInterest)+"元</li></ul></div></div>";
	    			}
	    			$(".borderArea").append(showDataHtml);
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
			  //写进本地存储
			  localStorage.setItem("orderId",orderId);
			  var url = 'contractOrderDetails.html';
			  window.location.href = url;
		});
	});
})


