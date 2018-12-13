require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'weui': 'weui/js/weui.min',
		'jquery': 'jquery-1.11.3.min',	
		'base': 'base',
		'formatter': 'formatter'
	},
	shim: {
        'json2': {
            exports: 'JSON'
        }
    }
});

require(['jquery', 'base', 'formatter'], function($, Base, Formatter){
	
	$(function(){
		//审核中绑定单击事件回调(riskStatus:2)
		$('#tabs').find('a:eq(0)').on('click', function(e){
			window.location.href = Base.url.webUrl + '/views/merchant/order/underReview.html';
		});
		//待支付绑定单击事件回调(riskStatus:4)
		$('#tabs').find('a:eq(1)').on('click', function(e){
			//window.location.href = Base.url.webUrl + '/views/merchant/order/toPay.html';
		});
		//已通过绑定单击事件回调(riskStatus:0)
		$('#tabs').find('a:eq(2)').on('click', function(e){
			window.location.href = Base.url.webUrl + '/views/merchant/order/pass.html';
		});
		//已分期绑定单击事件回调(riskStatus:3)
		$('#tabs').find('a:eq(3)').on('click', function(e){
			window.location.href = Base.url.webUrl + '/views/merchant/order/staged.html';
		});
		//未通过绑定单击事件回调(riskStatus:1)
		$('#tabs').find('a:eq(4)').on('click', function(e){
			window.location.href = Base.url.webUrl + '/views/merchant/order/failed.html';
		});
		
		
		//查询绑定单击事件回调
		$('.searchLogo').on('click', function(e){
			$('#content').empty();
			cache.pageNum = 1;
			cache.queryStr = $.trim($('#searchContent').val());
			request();
		});
		
		//用于定义页面变量(每页数据条数,当前页码,订单状态,查询参数)
		var cache = {pageSize: 10, pageNum: 1, riskStatus: 4, queryStr: ''};
		
		//将返回数据解析成页面可识别的标签格式
	    function dataToHtml(data){
	        var html = '';
	        $.each(data, function(i, n){
	        	html +=	'<section class="section1 clearfix" id="section1">'+
                			'<div class="sr">'+
                				'<a href="' + Base.url.webUrl + '/views/merchant/order/orderDetails.html?orderId=' + n.orderId + '">'+
                				'<div class="main">'+
                					'<div class="maint clearfix">'+
                						'<div id="tran_no">' + n.orderId + '</div>'+
                						'<div id="tran_norr">' + n.orderName + '</div>'+
                					'</div>'+
                					'<ul class="mainb clearfix">'+
                						'<li>'+
                							'<p class="p1">' + n.orderAmt + '</p>'+
                							'<p class="p2">订单金额</p>'+
                						'</li>'+
                						'<li>'+
                							'<p class="p1">' + n.preAmt + '</p>'+
                							'<p class="p2">每期分摊</p>'+
                						'</li>'+
                						'<li>'+
                							'<p class="p1">' + parseInt(n.orderItems) + '</p>' +
                							'<p class="p2">分期期数</p>' +
                						'</li>' +
                					'</ul>' +
                				'</div>' +
                				'</a>' +
                			'</div>' +
                		'</section>' +
                		'<section class="s2">' +
                			'<p>' + Formatter.datastampToString(n.createTime) + '</p>' +
                		'</section>';
        	});
	        return html;
	    }
		
		//查询接口
		function request(option){
			var opts = option || cache;
			Base.post(Base.url.rootUrl + '/merchant/merchantQueryOrder', opts, function(result){
				if(result.list.length > 0){
					cache.pageNum = result.pageNum;
					$('#content').append(dataToHtml(result.list));
				}				
			});
		}
		
		//滚动监听
		function bindScrollEvent(url){
	        //添加滚动监听事件
	        $(window).scroll( function() {
	            var docHeight = $(document).height();//获取整个页面的高度
	            var winHeight = $(window).height();//获取当前窗体的高度
	            var winScrollHeight = $(window).scrollTop();//获取滚动条滚动的距离
	            if(docHeight <= winHeight + winScrollHeight){
	                //加载更多的数据
	            	var option = $.extend({}, cache);
	            	option.pageNum ++; 
	            	request(option);
	            }
	        });
	    }
		
		
		request();
		bindScrollEvent();
	});
	
	
});