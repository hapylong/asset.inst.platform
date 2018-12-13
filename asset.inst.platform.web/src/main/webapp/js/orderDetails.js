require.config({
	baseUrl: '../js/lib',
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
		function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            var html = $("#inputHtml").val();
	            if(html == '1509'){
	            	$("#mask").hide();
		            $(".maskcontent").hide();
	            }else if(html != '1509'){
	            	$(".cb p").html('输入错误，请重新输入！').css('color','#b38a53');;
	            }
	        });
	      }
		alerts();
		
		$(".searchLogo").click(function(){
			$("#preAmtStatus").val('');
			$("#status").val('');
			$("#wfStatus").val('');
			$("#riskStatus").val('');
			$("#orderAmt").val('');
			$("#regId").val('');
			$("#version").val('');
			var orderId = $("#searchContent").val();
			if(orderId == ''){
				Base.alertMsg('请输入订单号',null,function(){
	    			$("#searchContent").focus();
	    		});
			}else{
				//查询订单详情
				var option = {
						'orderId' : orderId
				};
				Base.post(Base.url.rootUrl + '/order/getOrderInfoByOrderId', option, function(result){
					if(result == null){
						Base.alertMsg('无该订单号相关信息',null,function(){});
					}else{
						//赋值
						$("#preAmtStatus").val(result.preAmtStatus);
						$("#status").val(result.status);
						$("#wfStatus").val(result.wfStatus);
						$("#riskStatus").val(result.riskStatus);
						$("#orderAmt").val(result.orderAmt);
						$("#regId").val(result.regId);
						$("#version").val(result.version);
					}
				});
			}
		});
		//保存
		$(".submitss").click(function(){
			var orderId = $("#searchContent").val();
			var preAmtStatus=$("#preAmtStatus").val();
			var status=$("#status").val();
			var wfStatus=$("#wfStatus").val();
			var riskStatus=$("#riskStatus").val();
			var version=$("#version").val();
			if(orderId == ''){
				Base.alertMsg('orderId不能为空',null,function(){
	    			$("#orderId").focus();
	    		});
				return false;
			}
			if(preAmtStatus == ''){
				Base.alertMsg('preAmtStatus不能为空',null,function(){
	    			$("#preAmtStatus").focus();
	    		});
				return false;
			}
			if(status == ''){
				Base.alertMsg('status不能为空',null,function(){
	    			$("#status").focus();
	    		});
				return false;
			}
			/*if(wfStatus == ''){
				Base.alertMsg('wfStatus不能为空',null,function(){
	    			$("#wfStatus").focus();
	    		});
				return false;
			}*/
			if(riskStatus == ''){
				Base.alertMsg('riskStatus不能为空',null,function(){
	    			$("#riskStatus").focus();
	    		});
				return false;
			}
			var option={
					'preAmtStatus':preAmtStatus,
					'status':status,
					'wfStatus':wfStatus,
					'riskStatus':riskStatus,
					'orderId':orderId,
					'version':version
			}
			Base.post(Base.url.rootUrl + '/order/updateOrderByCondition', option, function(result){
				if(result == 'error'){
					Base.alertMsg('订单信息修改失败',null,function(){});
				}else if(result == 'success'){
					Base.alertMsg('订单信息修改成功',null,function(){
						$("#preAmtStatus").val('');
						$("#status").val('');
						$("#wfStatus").val('');
						$("#riskStatus").val('');
						$("#orderAmt").val('');
						$("#regId").val('');
						$("#version").val('');
						$("#searchContent").val('');
					});
				}
			});
		});
	})
});