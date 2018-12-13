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
		var bankTitle = decodeURI(location.search.match(new RegExp("[\?\&]bankTitle=([^\&]+)","i"))[1]);
		var bankNum = decodeURI(location.search.match(new RegExp("[\?\&]bankNum=([^\&]+)","i"))[1]);
		var status = decodeURI(location.search.match(new RegExp("[\?\&]status=([^\&]+)","i"))[1]);
		var limit = decodeURI(location.search.match(new RegExp("[\?\&]limit=([^\&]+)","i"))[1]);
		var src = localStorage.getItem("src");
		var limits = limit.split(" ");
		//赋值
		$(".detailTitle img").attr("src",src);
		$(".detailTitle span").html(bankTitle);
		$(".detailsOne").html('银行卡号  '+ bankNum);
		$(".detailsTwo").html(limits[0]);
		$(".detailsThree").html(limits[3]);
		if(status == 2){
			//正常卡
			$(".delete").val('删除绑定银行卡');
		}else if(status == 3){
			//激活卡
			$(".delete").val('解除绑定银行卡');
		}
		//点击
		$(".delete").click(function(){
			if($(this).val() == '删除绑定银行卡'){
				alerts();
				$('.cb').html('确认删除该卡片？');
				$('.qd').click(function () {
		            $("#mask").hide();
		            $(".maskcontent").hide();
		            var option = {"bankCardNo" : bankNum};
		            Base.loadingOn();
		            Base.post(Base.url.rootUrl + '/pay/removeBankCard', option, function(result){
		            	Base.alertMsg(result.retMsg, null ,function(){
		            		window.location = 'bankCardList.html';
		            	});
					})
		        });
			}else if($(this).val() == '解除绑定银行卡'){
				alerts();
				$('.cb').html('确认解除绑定该卡片？');
				$('.qd').click(function () {
		            $("#mask").hide();
		            $(".maskcontent").hide();
		            var option = {"bankCardNo" : bankNum};
		            Base.loadingOn();
		            Base.post(Base.url.rootUrl + '/pay/unbindBankCard', option, function(result){
		            	Base.alertMsg(result.retMsg, null ,function(){
		            		window.location = 'bankCardList.html';
		            	});
					})
		        });
			}
		});
		function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	        $('.qx').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	      }
	})  
})