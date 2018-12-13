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
		Base.post(Base.url.rootUrl + '/about/getUserInfo', {}, function(result){
			$("#realName").val(result.realName);
			$("#phoneNumber").val(result.regId);
			$("#idCard").val(result.idNo);
		})
		//修改风控信息
	      	    $("#revise").click(function(){
		       		$(this).css("display","none");
		       		$("#save-revise").css("display","block");
		       		$("#back").html('取消').attr('href','javascript:;');
		       		$(".toRevise").removeAttr("disabled");
		       		//取消编辑提示信息
	    			$("#back").click(function(){
	    				if($("#back").html() == '取消'){
			       			alerts();
							$('.cb').html('确认取消编辑？');
							$('.qd').click(function () {
								$("#save-revise").css("display","none");
					       		$("#revise").css("display","block");
					       		$("#back").html('返回').attr('href','aboutMe.html');
					       		$(".toRevise").attr("disabled",true);
							})
	    				}
		       		});
	       	    })
	       	    $("#save-revise").click(function(){
	    			$(this).css("display","none");
		       		$("#revise").css("display","block");
		       		$("#back").html('返回').attr('href','aboutMe.html');
		       		$(".toRevise").attr("disabled",true);
		        	var data = {
				              "realName": $('#realName').val(),
				              "idNo":$('#idCard').val(),
				              'regId':$('#phoneNumber').val()
				    };
		        	Base.loadingOn();
				    Base.post(Base.url.rootUrl + '/unIntcpt-req/updateUserInfoByRegId', data, function(result){
					   Base.toast('修改成功');
				       window.location.href = 'aboutMe.html';
				    })
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
	 	        $('.qx').click(function () {
	 	            $("#mask").hide();
	 	            $(".maskcontent").hide();
	 	        });
	 	      }
	})
})