/**
 * Created by jf on 2015/9/11.
 * Modified by bear on 2016/9/7.
 */
$(function () {
	/** 预加载 **/
    function preload(){
        $(window).on("load", function(){
            var imgList = [
                "img/wx/layers/content.png",
                "img/wx/layers/navigation.png",
                "img/wx/layers/popout.png",
                "img/wx/layers/transparent.gif"
            ];
            for (var i = 0, len = imgList.length; i < len; ++i) {
                new Image().src = imgList[i];
            }
        });
    }
    
    /** 初始化加载框 **/
    function showLoadingToast(){
    	var $loadingToast = $('#loadingToast');
        if ($loadingToast.css('display') != 'none') return;
        $loadingToast.fadeIn(100);
        setTimeout(function () {
            $loadingToast.fadeOut(100);
        }, 2000);
    }
    
    /** 判断传参是否为空 **/
    function isNotNull(val){
    	if(val == 'undefined' || val == ''){
    		return false;
    	}
    	return true;
    }
    
    /** 验证输入框 **/
    function validateInput(id){
    	var eId = '#' + id;
    	if(!isNotNull($(eId).val())){
    		$(eId).parent().parent().addClass('weui-cell_warn');
    	}else{
    		$(eId).parent().parent().removeClass('weui-cell_warn');
    	}
    	return isNotNull($(eId).val());
    }
    
    /** 验证表单信息 **/
    function validateForm(){
    	return validateInput('phoneNum') && validateInput('password');
    }
    
    /** 初始化提交按钮 **/
    function initSubmitBtn(){
    	$('#submitBtn').on('click', function(){
    		if(validateForm()){
    			showLoadingToast();
    		}
    	});
    }

    /** 页面初始化 **/
    function init(){
        preload();
        validateInput();
        initSubmitBtn();
    }
    init();
});