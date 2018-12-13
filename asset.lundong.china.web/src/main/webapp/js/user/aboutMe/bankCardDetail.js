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
		var bankCardNum = decodeURI(location.search.match(new RegExp("[\?\&]bankCardNum=([^\&]+)","i"))[1]);
		var src = localStorage.getItem("src");
		var limits = limit.split(" ");
		var bankNum = bankNum.replace(/(\w{4})(?=\w)/g,"$1 ");
		//赋值
		$(".detailTitle img").attr("src",src);
		$(".detailTitle span").html(bankTitle);
		$(".detailsOne").html(bankNum);
		$(".detailsTwo").html(limits[0]);
		$(".detailsThree").html(limits[3]);
		var width = $(".bankCard").width()
		$(".bankCard").height(width/1.5);
		if(status == 2){
			//正常卡
			$(".delete").val('删除绑定银行卡');
		}else if(status == 3){
			//激活卡
			$(".bankCard").css({"background":"url(../../../img/bankImg/bankCard1.png) no-repeat 0 0","background-size":"100% 100%"})
			$(".delete").val('解除绑定银行卡');
		}
		//点击
		$(".delete").click(function(){
			if(bankCardNum > 1){
				if($(this).val() == '删除绑定银行卡'){
					alerts();
					$('.cb').html('确认删除该卡片？');
					$('.qd').click(function () {
			            $("#mask").hide();
			            $(".maskcontent").hide();
			            var option = {"bankCardNo" : decodeURI(location.search.match(new RegExp("[\?\&]bankNum=([^\&]+)","i"))[1])};
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
			            var option = {"bankCardNo" : decodeURI(location.search.match(new RegExp("[\?\&]bankNum=([^\&]+)","i"))[1])};
			            Base.loadingOn();
			            Base.post(Base.url.rootUrl + '/pay/unbindBankCard', option, function(result){
			            	Base.alertMsg(result.retMsg, null ,function(){
			            		window.location = 'bankCardList.html';
			            	});
						})
			        });
				}
			}else{
				Base.alertMsg('请添加新的银行卡后再删除');
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