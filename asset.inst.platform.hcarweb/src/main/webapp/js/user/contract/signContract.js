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
require(['json2','jquery', 'base', 'formatter'], function(JSON, $, Base, Formatter){
	$(function(){
		var orderId = localStorage.getItem("orderId");
		//查询订单详情
		var option = {
				'orderId' : orderId
		};
		Base.post(Base.url.rootUrl + '/contract/get_contracts', option, function(result){
			var recode = result.recode;
			if(recode == 1){
				//成功
				var list = result.ecList;
				if(list.length == 0){
					//无合同列表时
					$('.main').hide();
					$('.quitbox').hide();
					$('.iconBlock').show();
				}else{
					$('.main').show();
					$('.quitbox').show();
					//合同列表
					for(var i=0;i<list.length;i++){
						switch (Number(list[i].status)){
			            case 0: 
			            	var listHtml = "";
			            	listHtml += "<div class='main1 clearfix' link="+list[i].ecSignerList[0].ecSignerSignUrl+" status='0'><div class='mainr clearfix'>"+  
							  	             '<div class="planContent-item-left">' +
									                '<input type="radio" id="' + i + '"' + '" name="radio-1-set" class="regular-radio"/><label for="' + i + '">' +
								                 '</div>' +
											 '<div class="planContent-item-right">' +
											   	'<span class="planContent-item-right-left">' + list[i].ecName + '</span>' +
											   	'<span class="planContent-item-right-right">未签约</span>' +
											 '</div>' +
			              	           "</div></div>"
			                $(".main").append(listHtml);
			                break;
			            case 1: 
			            	var listHtml = "";
			            	listHtml += "<div class='main1 clearfix' link="+list[i].ecViewUrl+" status='1'><div class='mainr clearfix'>"+  
							  	             '<div class="planContent-item-left">' +
									                '<input type="radio" id="' + i + '"' + '" name="radio-1-set" class="regular-radio"/><label for="' + i + '">' +
								                 '</div>' +
											 '<div class="planContent-item-right">' +
											   	'<span class="planContent-item-right-left">' + list[i].ecName + '</span>' +
											   	'<span class="planContent-item-right-right">已签约</span>' +
											 '</div>' +
			              	           "</div></div>"
			                $("section").append(listHtml);
			                break;
			                default:break
			            }
					}
				}
			}else if(recode == 0){
				//失败（无合同列表时）
				$('.main').hide();
				$('.quitbox').hide();
				$('.iconBlock').show();
			}
		});
		//点击合同
		$(".main").delegate(".main1","click",function(){
			var currentHref = $(this).attr('link');
			var status = $(this).attr('status');
			$('.planContent-item-left').find('input').prop('checked', false);
			$(this).find('input').prop('checked', true);
			if(status == '1'){
				$('.quit').html('立即查看合同');
			}else{
				$('.quit').html('立即签署合同');
			}
			//给按钮附链接
			$('.quit').attr('href',currentHref);
		});
	})
});