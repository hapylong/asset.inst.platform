require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'wx': 'jweixin-1.0.0',
		'jquery': 'jquery-1.11.3.min',		
		'base': 'base',
		'formatter': 'formatter',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        }
    }
});
require(['json2','jquery', 'base', 'formatter', 'wx'], function(JSON, $, Base, Formatter, wx){
	var url = Base.url.rootUrl + '/pledge/2200/get_order_group';
	var totalPage = 2;
	var pageSize = 10;
	var currentPage = 1; 
	$(function(){
		//number
		var regId = location.search.match(new RegExp("[\?\&]regId=([^\&]+)","i"))[1];
		//查询订单详情
		var option = {
				'regId' : regId
		};
		Base.post(Base.url.rootUrl + '/pledge/order_statistics', option, function(result){
			
			if(result.twentyOne != 0){
				$('.number1').show().html(result.twentyOne);
			}
			if(result.twentyTwo != 0){
				$('.number2').show().html(result.twentyTwo);
			}
			if(result.six != 0){
				$('.number3').show().html(result.six);
			}
		})
		//默认待估价
	    query(1,21,url);//待估价
	    bindScrollEvent(21,url);
	    //tab
	    $('#tabs a').click(function() {
	        $(".content-son").hide();
	        $("#tabs li a").attr("id","");
	        $(this).attr("id","current");
	        $('#' + $(this).attr('title')).fadeIn();
	        $(".content-son section").remove();
	        if($(this).attr('title') == "toEvaluate"){
	             query(1,21,url);
	             $(window).unbind("scroll");
	             bindScrollEvent(21,url);
	        }else if($(this).attr('title') == "evaluated"){
	        	 query(1,22,url);
	             $(window).unbind("scroll");
	             bindScrollEvent(22,url);
	        }else if($(this).attr('title') == "cancel") {
	        	 query(1,6,url);
	             $(window).unbind("scroll");
	             bindScrollEvent(6,url);
	        }                                                                                          
	    });
	    //待估价订单详情
	    $("#toEvaluate").delegate(".toEvaluateTop","click",function(){
	    	var thisId = $(this).parent().parent().attr("id");
	    	var url = "toEvaluateDetail.html?id="+thisId;
	    	window.location.href=url;
	    });
	    //已估价订单详情
	    $("#evaluated").delegate(".evaluatedTop","click",function(){
	    	var thisId = $(this).parent().parent().attr("id");
	    	var url = "evaluatedOrderDetail.html?id="+thisId;
	    	window.location.href=url;
	    });
	    //已取消订单详情
	    $("#cancel").delegate(".cancelTop","click",function(){
	    	var thisId = $(this).parent().parent().attr("id");
	    	var url = "cancelOrderDetail.html?id="+thisId;
	    	window.location.href=url;
	    });
	    //查询方法
		function query(pageNum,status,url){
			    // 进行post请求
				var p = {
					'pageSize':pageSize,
					'pageNum':pageNum,
					'riskStatus':status
				};
		        Base.post(url, p, function(result){
		        	data=result;
                    totalOrder = data.total;
	                totalPage = data.pages; // 一共有多少页数据
	    		    currentPage = data.pageNum; // 获取当前第几页数据
	    		    var list = new Array(data.list)[0];
	    		    //加载数据
	    		    showData(list,status,currentPage);
		        })
		}
		//将加载的数据放在页面中
	    function showData(List,status,currentPage){
	    	if(List.length == 0 && currentPage == 1){
	    		$(".iconBlock").show();
	    	}else if(List.length != 0){
	    		$(".iconBlock").hide();
	    		var showDataHtml = "";
	            //待估价
	            if(status == 21){
	            	for(var i=0;i<List.length;i++){
	            		var orderName = List[i].orderName;
	            		var orderNames = orderName.split('-');
	                    showDataHtml +="<section id='"+List[i].orderId+"'><div class='orderBlock'><div class='orderBlockTop toEvaluateTop'><span>订单时间 ：</span><span class='orderTime'>"+Formatter.timeformatter3(List[i].createTime)+
	                    "</span><img src='../../../img/arrowRight.png' class='toOrderDetails'></div><div class='orderBlockBottom'><span class='blockName'>"+Formatter.isNull(orderNames[0])+"</span><span class='value'>询价中</span><p class='houdeNumber'>"+Formatter.isNull(orderNames[0])+Formatter.isNull(orderNames[1])+"</p></div></div>"+"</section>"
	                }
	            	 $("#toEvaluate").append(showDataHtml);
	            }
	            //已估价    
	            if(status == 22){
	            	for(var i=0;i<List.length;i++){
	            		var orderName = List[i].orderName;
	            		var orderNames = orderName.split('-');
	            		showDataHtml +="<section id='"+List[i].orderId+"'><div class='orderBlock'><div class='orderBlockTop evaluatedTop'><span>订单时间 ：</span><span class='orderTime'>"+Formatter.timeformatter3(List[i].createTime)+
	                    "</span><img src='../../../img/arrowRight.png' class='toOrderDetails'></div><div class='orderBlockBottom'><span class='blockName'>"+Formatter.isNull(orderNames[0])+"</span><span class='value'>"
	                    + Formatter.moneyTenThousand(Formatter.isNull(List[i].applyAmt))+"元</span><p class='houdeNumber'>"+Formatter.isNull(orderNames[0])+Formatter.isNull(orderNames[1])+"</p></div></div></section>"
	                }
	            	  $("#evaluated").append(showDataHtml);
	            }
	            //已取消    
	            if(status == 6){
	            	for(var i=0;i<List.length;i++){
	            		var orderName = List[i].orderName;
	            		var orderNames = orderName.split('-');
	            		showDataHtml +="<section id='"+List[i].orderId+"'><div class='orderBlock'><div class='orderBlockTop cancelTop'><span>订单时间 ：</span><span class='orderTime'>"+Formatter.timeformatter3(List[i].createTime)+
	                    "</span><img src='../../../img/arrowRight.png' class='toOrderDetails'></div><div class='orderBlockBottom'><span class='blockName'>"+Formatter.isNull(orderNames[0])+"</span><span class='value'>"
	                    + Formatter.moneyTenThousand(Formatter.isNull(List[i].applyAmt))+"元</span><p class='houdeNumber'>"+Formatter.isNull(orderNames[0])+Formatter.isNull(orderNames[1])+"</p></div></div>"+"</section>"
	            	}
	            	$("#cancel").append(showDataHtml);
	            }
	    	}
	    }
	    function bindScrollEvent(status,url){
	            $(window).scroll( function() {
	                var docHeight = $(document).height(); // 获取整个页面的高度
	                var winHeight = $(window).height(); // 获取当前窗体的高度
	                var winScrollHeight = $(window).scrollTop(); // 获取滚动条滚动的距离
	                if(docHeight<= winHeight + winScrollHeight){
	                    // 加载更多的数据
	                    query(currentPage+1,status,url);
	                }
	            } );
	    	}
	})
});