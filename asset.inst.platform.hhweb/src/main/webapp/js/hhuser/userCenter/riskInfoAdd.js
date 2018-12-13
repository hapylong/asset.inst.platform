require.config({
	baseUrl: '../../../js/lib',
	paths: {
		'cons': 'cons',
		'json2': 'json2',
		'jquery': 'jquery-1.11.3.min',
		'mobiscroll' : 'mobiscroll.custom-2.17.1.min',
		'base': 'base',
		'formatter': 'formatter',
		'weui': 'weui/js/weui.min',
	},
	shim: {
        'json2' : {
            exports: 'JSON'
        },
        'mobiscroll' : {
            exports: 'MOBISCROLL'
        }
    }
});
require(['json2', 'jquery', 'mobiscroll', 'base', 'formatter'], function(JSON, $, mobiscroll, Base, Formatter){
	$(function(){
		//弹窗
	    function alerts(){
	    	var widths=document.documentElement.clientWidth;
	    	$("#mask").show();
	        $(".maskcontent").css('left',(widths-250)/2).show();
	        $('.qd').click(function () {
	            $("#mask").hide();
	            $(".maskcontent").hide();
	        });
	    }
		//风控信息
		Base.post(Base.url.rootUrl + '/2/queryRiskInfo', {}, function(result){
			if(result.riskInfo != null){
	        	$(".submitss").hide();
	        	$(".btnBox").show();
	        	//赋值
	        	var checkInfo = JSON.parse(result.riskInfo.checkInfo);
	        	$("#mobelPhone").val(checkInfo.phone);
	      	    $("#addProvince").val(checkInfo.addprovince);
	      	    $("#marriedStatus").val(checkInfo.marriedstatus);
	      	    $("#contactName1").val(checkInfo.contactname1);
	      	    $("#contactMobel1").val(checkInfo.contactphone1);
	      	    $("#contactName2").val(checkInfo.contactname2);
	      	    $("#contactMobel2").val(checkInfo.contactphone2);
	      	    $("input").attr("readonly",true);
	      	    $("select").attr("disabled",true);
	      	    //修改风控信息
	      	    $("#revise").click(function(){
		       		$(this).css("display","none");
		       		$("#save-revise").css("display","block");
		       		$("input").removeAttr("readonly");
		       		$("select").removeAttr("disabled");
		       		//下拉选
		       		$('#marriedStatus').mobiscroll().select({
		       	        theme: $('#theme').val(),      
		       	        lang: $('#language').val(),   
		       	        display: $('#display').val(),  
		       	        mode: $('#mode').val(),        
		       	        minWidth: 200
		       	    });
	       	    })
	       	    $("#save-revise").click(function(){
		       		var marriedStatus = $("#marriedStatus_dummy").val();
		       		$("#marriedStatus").val(marriedStatus);
		       		var mobelPhone = $("#mobelPhone").val();
		       		var addProvince = $("#addProvince").val();
		       		var marriedStatus = $("#marriedStatus").val();
		       		var contactName1 = $("#contactName1").val();
		       		var contactMobel1 = $("#contactMobel1").val();
		       		var contactName2 = $("#contactName2").val();
		       		var contactMobel2 = $("#contactMobel2").val();
		       		//提交
		      		$(this).css("display","none");
		       		$("#revise").css("display","block");
		       		$("input").attr("readonly",true);
		        	$("select").attr("disabled",true);
	        	    var checkInfo = {
				              "phone": mobelPhone,
				              "addprovince":addProvince,
				              "marriedstatus":marriedStatus,
				              "contactname1":contactName1,
				              "contactphone1":contactMobel1,
				              "contactname2":contactName2,
				              "contactphone2":contactMobel2,
				    };
					checkInfo = JSON.stringify(checkInfo);
				    Base.post(Base.url.rootUrl + '/2/updateRiskInfo', {'checkInfo': checkInfo}, function(result){
					   //修改风控信息
					   Base.toast('保存成功');
				       window.location.href = 'memberCenter.html';
				    })
	       	 })
	        }else{
	        	//从地址栏带手机号
	        	var url = window.location.href;
	    		if(url.indexOf('regId') >= 0){
	    		   var regId = location.search.match(new RegExp("[\?\&]regId=([^\&]+)","i"))[1];
	    		   $("#mobelPhone").val(regId);
	    		}
	        	$(".submitss").show();
	        	$(".btnBox").hide();
	        	//下拉选
	    		$('#marriedStatus').mobiscroll().select({
	    		    theme: $('#theme').val(),      
	    		    lang: $('#language').val(),   
	    		    display: $('#display').val(),  
	    		    mode: $('#mode').val(),        
	    		    minWidth: 200                  
	    		});
	        	//首次提交风控信息
	        	$(".submitss").click(function(){ 
	        		var mobelPreg = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
	    	    	var mobelPhone=$("#mobelPhone").val();
	    	    	if (mobelPhone == "" || !mobelPreg.test(mobelPhone)) {
	    	    		Base.alertMsg('请填写合法11位手机号码',null,function(){
	    	    			$("#mobelPhone").focus();
	    	    		});
	    				return false; 
	    			} 
	    			var addProvince=$("#addProvince").val();
	    			if (addProvince == "") {
	    				Base.alertMsg('请填写地址',null,function(){
	    	    			$("#addProvince").focus();
	    	    		});
	    				return false; 
	    			}
	    			var marriedStatus = $("#marriedStatus_dummy").val();
	    			$("#marriedStatus").val(marriedStatus);
	    			if($("#marriedStatus").val() != "已婚" && $("#marriedStatus").val() != "未婚"){
	    				Base.alertMsg('请选择婚姻状况',null,function(){
	    					$("#marriedStatus").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactName1=$("#contactName1").val();
	    			if ($("#contactName1").val() == "") {
	    				Base.alertMsg('请填写亲属姓名',null,function(){
	    					$("#contactName1").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactMobel1=$("#contactMobel1").val();
	    			if (contactMobel1 == "" || !mobelPreg.test(contactMobel1)) {
	    				Base.alertMsg('请填写亲属手机号码',null,function(){
	    					$("#contactMobel1").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactName2=$("#contactName2").val();
	    			if ($("#contactName2").val() == "") {
	    				Base.alertMsg('请填写朋友姓名',null,function(){
	    					$("#contactName2").focus();
	    	    		});
	    				return false; 
	    			}
	    			var contactMobel2=$("#contactMobel2").val();
	    			if (contactMobel2 == "" || !mobelPreg.test(contactMobel2)) {
	    				Base.alertMsg('请填写朋友手机号码',null,function(){
	    					$("#contactMobel2").focus();
	    	    		});
	    				return false; 
	    			} 
					var checkInfo = {
				              "phone": mobelPhone,
				              "addprovince":addProvince,
				              "marriedstatus":marriedStatus,
				              "contactname1":contactName1,
				              "contactphone1":contactMobel1,
				              "contactname2":contactName2,
				              "contactphone2":contactMobel2,
				    };
					checkInfo = JSON.stringify(checkInfo);
				    Base.post(Base.url.rootUrl + '/2/insertRiskInfo', {'checkInfo': checkInfo}, function(result){
					   //提交风控信息
				       window.location.href = 'memberCenter.html'
				    })
			    });
	        }
		});
	});	 
});
