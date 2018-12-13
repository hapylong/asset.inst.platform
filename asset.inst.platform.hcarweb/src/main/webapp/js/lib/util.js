/*****************************************
**函数：字符串到金钱格式（字符串后2位为小数）
**参数：字符串
**返回：金额
******************************************/
function string2moneyplus(indata)
{
	if(Number(indata)==0) return Number(indata);
	else return (Number(indata)/100);
}

/*****************************************
**函数：字符串到金钱格式,有逗号,小数2位（字符串后2位为小数）hubin070919
**参数：字符串
**返回：金额 
******************************************/
function string2money(indata)
{
	var v;
	if(Number(indata)==0) return ("0.00");
	else v=Number(indata)/100
	//以下转为US模式
	var vv=parseFloat(v).toLocaleString();
	for(var i=0;i<vv.length;i++)
	{
		var c = vv.charAt(i);
		if(c=='.')
		{
			i++;
			if(i!=vv.length)
			{
				i++;
				if(i!=vv.length)
				{
					i++;
					vv=vv.substring(0,i);
				}
				else vv=vv.substring(0,i)+"0";	
			}
			else vv=vv.substring(0,i)+"00";
			return vv;
		}
	}
	vv=vv+".00";
	return vv;
}

/*****************************************
 **函数：字符串到金钱浮点型
 **参数：字符串
 **返回：金额 
 ******************************************/
function string2moneyfloat(indata)
{
	var v;
	if(Number(indata)==0) return ("0.00");
	else v=Number(indata)/100
	//以下转为US模式
	var vv=parseFloat(v).toString();
	vv=vv+".00";
	return vv;
}

/*****************************************
**函数：金钱格式到字符串（字符串后2位为小数）hubin070919
**参数：金钱字符串
**返回：字符串
******************************************/
function money2string(str)
{
	var newstr="";
	if(isDec(str)&&!isNum(str))//有小数点
	{
		for (var i = 0; i < str.length; i++)
 		{
  			var c = str.charAt(i);
  			if (c!='.')
  			{
  				newstr+=c;
  				continue;
  			}
  			i++;
  			if(str.charAt(i)!="")
  				newstr+=str.charAt(i);
  			else newstr+="00";
  			i++;
  			if(str.charAt(i)!="")
  				newstr+=str.charAt(i);
  			else newstr+="0";
  			return newstr;
 		}
 	} 
 	if(isDec(str)&&isNum(str))//无小数点
 	{
		return str+"00";	
 	} 
}

/*****************************************
**函数：转换为2位小数hubin070919
**参数：
**返回：
******************************************/
function atod(str)
{
	var newstr="";
	for (var i = 0; i < str.length; i++)
 	{
  		var c = str.charAt(i);
  		newstr+=c;
  		if (c!='.')
  		{
  			continue;
  		}
  		i++;
  		if(str.charAt(i)!="")
  			newstr+=str.charAt(i);
  		else 
  		{
  			newstr+="00";
  			return newstr;
  		}
  		i++;
  		if(str.charAt(i)!="")
  			newstr+=str.charAt(i);
  		else newstr+="0";
  		return newstr;
 	}
 	return newstr;
}

/*****************************************
**函数：验证文本框不为空 hubin070919
**参数：需验证个数,验证文本框数组
**返回：不为空-true
******************************************/
function checkinput(num,arrayArgs)
{
	if(num==1) 
		if((document.getElementById(arrayArgs).value).Rtrim()!="") return true;
		else
		{
			alert("PLEASE INPUT THE FIELDS");
			return false;
		}
	else if(num>1)
	{
		for(var i=0;i<num;i++)
		{
			
			if((document.getElementById(arrayArgs[i]).value).Rtrim()=="") 
			{
				alert("PLEASE INPUT THE FIELDS");
				return false;
			}
		}
		return true;
	}
}

/*****************************************
**函数：验证文本框不为空,换一种写法 liuj
**参数：需验证个数,验证文本框数组
**返回：不为空-true
******************************************/
function checkinput2(num,arrayArgs)
{
	if(num==1) 
		if((arrayArgs).value.Rtrim()!="") return true;
		else
		{
			alert("PLEASE INPUT THE FIELDS");
			return false;
		}
	else if(num>1)
	{
		for(var i=0;i<num;i++)
		{
			if((arrayArgs[i]).value.Rtrim()=="") 
			{
				alert("PLEASE INPUT THE FIELDS");
				return false;
			}
		}
		return true;
	}
}

/*****************************************
**函数：验证是否纯数字 hubin070919
**参数：字符串
**返回：纯数字-true
******************************************/
function isNum(Str)
{
	if(Str.length==0) return false;
 	var validChar = "0123456789";
 	var i;
	for (i = 0; i < Str.length; i++)
 	{
  		var c = Str.charAt(i);
 	 	if (validChar.indexOf(c) == -1) return false;
 	} 
	 return true;
}


/*****************************************
**函数：验证是否包含字母 hubin070919
**参数：字符串
**返回：包含字母-true
******************************************/
function hasChar(Str)
{
 for (var i = 0; i < Str.length; i++)
 {
  var c = Str.charAt(i);
  if ((c>='A' && c<='Z' )||( c>='a' && c<='z') ) return true;
 } 
 return false;
}

/*****************************************
**函数：验证是否包含数字 hubin070919
**参数：字符串
**返回：包含数字-true
******************************************/
function hasDig(Str)
{
 for (var i = 0; i < Str.length; i++)
 {
  var c = Str.charAt(i);
  if (c>='0' && c<='9' ) return true;
 } 
 return false;
}

/*****************************************
**函数：验证是否为小数 hubin071115
**参数：字符串
**返回：是-true
******************************************/
function isDec(str)
{
	if(str.length==0) return false;
 	var validChar = ".0123456789";
 	var i;
	for (i = 0; i < str.length; i++)
 	{
  		var c = str.charAt(i);
 	 	if (validChar.indexOf(c) == -1) return false;
 	} 
 	return true;
}

/*****************************************
**函数：验证是否符合密码格式(首字符字母,必须为字母数字组合) hubin070929
**参数：字符串
**返回：符合-true
******************************************/
function isPwd(Str)
{
	if(Str.length!=6)
	{
		alert("THE PASSWORD SHOULD BE 6 BITS");
    	return false;
	}
	var c1 = Str.charAt(0);//判断首字母为字母
    	if ((c1<'A' || (c1>'Z'&&c1<'a') || c1>'z') ) 
    	{
    		alert("THE FIRST CHARACTER OF THE PASSWORD SHOULD BE A LETTER");
    		return false;
    	}	
    if	(!hasDig(Str)) 
    {
    	alert("THE PASSWORD SHOULD BE A LETTER-NUMBER COMBINATION");
    	return false;
    }
 	for (var i = 1; i < Str.length; i++)//判断每一位是否为数字字母
 	{
  		var c = Str.charAt(i);
  		if (!((c>='A' && c<='Z' )||( c>='a' && c<='z')||( c>='0' && c<='9')) )
		{
    	alert("THE PASSWORD SHOULD BE A LETTER-NUMBER COMBINATION");
    	return false;
    	}
 	} 
 	return true;
}

/*****************************************
**函数：验证是否符合id格式(大写字母或数字) hubin071129
**参数：字符串
**返回：符合-true
******************************************/
function isId(Str)
{
	if(Str.length==0)
	{
		alert("Please input the ID No.");
    	return false;
	}
	for (var i = 0; i < Str.length; i++)//判断每一位是否为数字字母
 	{
  		var c = Str.charAt(i);
  		if (!(( c>='A' && c<='Z')||( c>='0' && c<='9')) )
		{
    	alert("The ID No. should be number or capital letter");
    	return false;
    	}
 	} 
 	return true;
}

/*****************************************
**函数：验证是否符合卡号格式(16位数字) 
**参数：字符串
**返回：符合-true
******************************************/
function isCardNo(Str)
{
	if(Str.length=="")
	{
		alert("Please input the card No.");
    	return false;
	}
	if(Str.length!=16)
	{
		alert("The card No. should be 16 digits");
    	return false;
	}
	else
	{
		for (var i = 1; i < Str.length; i++)//判断每一位是否为数字
 		{
  			var c = Str.charAt(i);
  			if (!( c>='0' && c<='9') )
			{
    		alert("The Card No. should be 16 digits");
    		return false;
    		}
 		} 
	}
 	return true;
}

/*****************************************
**函数：验证是否符合手机号格式(11位数字) wff 20150707
**参数：字符串
**返回：符合-true
******************************************/
function isTel(Str)
{
	if(Str.length=="")
	{
		alert("Please input the mobile NO.");
    	return false;
	}
	if(Str.length!=11)
	{
		alert("The mobile No. should be 11 digits");
    	return false;
	}
	else
	{
		for (var i = 1; i < Str.length; i++)//判断每一位是否为数字
 		{
  			var c = Str.charAt(i);
  			if (!( c>='0' && c<='9') )
			{
    		alert("The Card No. should be 11 digits");
    		return false;
    		}
 		} 
	}
 	return true;
}

/*****************************************
**函数：比较两日期大小 hubin070919
**参数：date1,date2(8位)
**返回：前者小：-1,等：0,前者大：1 
******************************************/
function datecomp(date1,date2)
{
	var y1=date1.substring(0,4);
	var y2=date2.substring(0,4);
	var m1=date1.substring(4,6);
	var m2=date2.substring(4,6);
	var d1=date1.substring(6,8);
	var d2=date2.substring(6,8);
	if(y2>y1) return -1;
	else if(y2<y1) return 1;
	else if(y2==y1)
	{
		if(m2>m1) return -1;
		else if(m2<m1) return 1;
		else if(m2==m1)
		{
			if(d2>d1) return -1;
			else if(d2<d1) return 1;
			else if(d2==d1) return 0;
		}
	}
}


/*****************************************
**函数：trim 
**参数：str
**返回：str
******************************************/
String.prototype.Trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.LTrim = function()
{
    return this.replace(/(^\s*)/g, "");
}
String.prototype.Rtrim = function()
{
    return this.replace(/(\s*$)/g, "");
}


/*****************************************
**函数：客户身份证件类型初始化 hubin070924
**参数：id of <select>
**返回：
******************************************/
function getCustIdType_CH(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("Customer ID","09");
	sltobj.options[1] = new Option("HK ID","01");
	sltobj.options[2] = new Option("Passport","02");
}
function getCustIdType_EN(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("Customer ID","09");
	sltobj.options[1] = new Option("HK ID","01");
	sltobj.options[2] = new Option("Passport","02");
}
function getCustIdType_CH1(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("身份证18位","01");
	sltobj.options[1] = new Option("身份证15位","02");
	sltobj.options[2] = new Option("军官证","03");
	sltobj.options[3] = new Option("台胞照","04");
	sltobj.options[4] = new Option("护照","05");
	sltobj.options[5] = new Option("国外护照","10");
	sltobj.options[6] = new Option("其他","20");
}
function getCustIdType_EN1(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("身份证18位","01");
	sltobj.options[1] = new Option("身份证15位","02");
	sltobj.options[2] = new Option("军官证","03");
	sltobj.options[3] = new Option("台胞照","04");
	sltobj.options[4] = new Option("护照","05");
	sltobj.options[5] = new Option("国外护照","10");
	sltobj.options[6] = new Option("其他","20");
}

/*****************************************
**函数：币种初始化 hubin090515
**参数：id of <select>
**返回：
******************************************/
function getCurrType(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("CYN","CYN");
	/*elementid.options[elementid.length] = new Option("HKD","344");
	elementid.options[elementid.length] = new Option("MYR","458");*/
}


/*****************************************
**函数：客户身地址地区初始化 hubin071008
**参数：id of <select>
**返回：
******************************************/
function getCustAddrReg_CH(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("Hong Kong","0001");
	sltobj.options[1] = new Option("Kowloon","0002");
	sltobj.options[2] = new Option("New Erritories","0003");
	sltobj.options[3] = new Option("Overseas","0004");
}
function getCustAddrReg_EN(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("Hong Kong","0001");
	sltobj.options[1] = new Option("Kowloon","0002");
	sltobj.options[2] = new Option("New Erritories","0003");
	sltobj.options[3] = new Option("Overseas","0004");
}

/*****************************************
**函数：货币初始化 hubin071008
**参数：id of <select>
**返回：
******************************************/
function getCurr_CH(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("港币","1");
}
function getCurr_EN(elementid)
{
	var sltobj=document.getElementById(elementid);
	sltobj.options[0] = new Option("HK$","01");
}

/*****************************************
**函数：tab转换函数 hubin071005
**参数：
**返回：
******************************************/
function clearAttribute(idlist){
	var items=[];
	if(items.length==0){
		for(var i=0;i<idlist.length;i++)
		items.push(idlist[i]);
	}
	for(var i=0,n=items.length;i<n;i++){
	   
		var menuItem=document.getElementById(items[i]);
		menuItem.setAttribute("class","jive-tab");
		menuItem.setAttribute("className","jive-tab");
		document.getElementById("div"+items[i]).style.display="none";
	}
}
function setAttribute(id,idlist){
	clearAttribute(idlist);
	var menuItem=document.getElementById(id);
	menuItem.setAttribute("class","jive-tab-active");
	menuItem.setAttribute("className","jive-tab-active");
	document.getElementById("div"+id).style.display="";
}

function changetab(idstr)
{
	setAttribute(idstr,idlist); 
}


/*****************************************
**函数：日期转换函数 hubin071029  日期转换
**参数：日期文本框id
**返回：8位日期
******************************************/
function fixDate(id)
{
		var v=document.getElementById(id).value;
		if(v=="") return "";
  		var yyyy=v.substring(0,4);
  		var mm=v.substring(5,7);
  		var dd=v.substring(8,10);
  		return(yyyy+mm+dd);
}
function antifixDate(v)
{
		if(v=="") return "";
  		var yyyy=v.substring(0,4);
  		var mm=v.substring(4,6);
  		var dd=v.substring(6,8);
  		return(yyyy+"-"+mm+"-"+dd);
}

/*****************************************
**函数：时间转换函数 hubin071226  时间转换
* 输入"hhmmssuu" 返回hh:mm:ss
******************************************/
function getFormatTime(strtime)
{
		if(strtime=="") return "";
  		var rettime;
		rettime=strtime.substring(0,2)+":"+strtime.substring(2,4)+":"+strtime.substring(4,6);
		return rettime;	
}
/*****************************************
**函数：时间转换函数 hubin071226  日期转换
* 输入yyyymmdd 返回yyyy/mm/dd
******************************************/
function getFormatDate(strdate)
{
		if(strdate=="") return "";
		var retdate;
		retdate=strdate.substring(0,4)+"/"+strdate.substring(4,6)+"/"+strdate.substring(6,8);
		return retdate;	
}

/*****************************************
**函数：交易类型描述函数 hubin071226  
* 输入交易代码 返回交易描述
******************************************/
function getTranDesc(strTxnCode)
{
		if(strTxnCode=="") return "";
		var retDesc="OTH";
		if(strTxnCode=="3022"||strTxnCode=="3002"||strTxnCode=="3032") retDesc="NEW";
		else if(strTxnCode=="5005") retDesc="OTH"; //atm查询费
		else if(Number(strTxnCode)>=5000&&Number(strTxnCode)<=6000) retDesc="POS";
		else if(strTxnCode=="2016"||strTxnCode=="2116") retDesc="ADJ";
		else if(strTxnCode=="3025"||strTxnCode=="3001") retDesc="XFR";
		else if(strTxnCode=="3005"||strTxnCode=="3015"||strTxnCode=="4005") retDesc="GVP";
		else if(strTxnCode=="3036") retDesc="LOAD";
		return retDesc;	
}

/*****************************************
**函数：PBOC交易类型描述函数 hubin080815  
* 输入交易代码 返回交易描述
******************************************/
function getPBOCTranDesc(strTxnCode)
{
		if(strTxnCode=="") return "";
		var retDesc="OTH";
		if(strTxnCode=="300") retDesc="脱机消费";
		else if(strTxnCode=="330") retDesc="脱机消费撤销"; 
		else if(strTxnCode=="001") retDesc="指定帐户圈存"; 
		else if(strTxnCode=="002") retDesc="行内非指定帐户圈存";
		else if(strTxnCode=="003") retDesc="现金圈存"; 
		else if(strTxnCode=="004") retDesc="指定帐户圈存冲正"; 
		else if(strTxnCode=="005") retDesc="行内非指定帐户圈存冲正"; 
		else if(strTxnCode=="006") retDesc="现金圈存冲正"; 
		else if(strTxnCode=="007") retDesc="圈提"; 
		else if(strTxnCode=="008") retDesc="圈提撤销";
		else if(strTxnCode=="009") retDesc="现金圈存撤销"; 
		else if(strTxnCode=="010") retDesc="行内非指定账户圈存撤销"; 
		else if(strTxnCode=="011") retDesc="现金圈存撤销冲正"; 
		else if(strTxnCode=="012") retDesc="行内非指定账户圈存撤销冲正";
		else if(strTxnCode=="013") retDesc="跨行非指定账户圈存";/*hubin add 090521*/
		else if(strTxnCode=="014") retDesc="跨行非指定账户圈存充正";/*hubin add 090521*/
		else if(strTxnCode=="015") retDesc="跨行非指定账户圈存撤销";/*hubin add 090521*/
		else if(strTxnCode=="016") retDesc="跨行非指定账户圈存撤销充正";/*hubin add 090521*/
		else if(strTxnCode=="100") retDesc="销户结清";
		return retDesc;	
}

/*****************************************
**函数：浮现层函数 hubin071229 
*
******************************************/
var t_DiglogX,t_DiglogY,t_DiglogW,t_DiglogH;

function gid(id) {
return document.getElementById?document.getElementById(id):null;
}

function gname(name) {
return document.getElementsByTagName?document.getElementsByTagName(name):new Array()
}

function Browser() {
var ua, s, i;
this.isIE = false;
this.isNS = false;
this.isOP = false;
this.isSF = false;
ua = navigator.userAgent.toLowerCase();
s = "opera";
if ((i = ua.indexOf(s)) >= 0) {
  this.isOP = true;return;
}
s = "msie";
if ((i = ua.indexOf(s)) >= 0) {
  this.isIE = true;return;
}
s = "netscape6/";
if ((i = ua.indexOf(s)) >= 0) {
  this.isNS = true;return;
}
s = "gecko";
if ((i = ua.indexOf(s)) >= 0) {
  this.isNS = true;return;
}
s = "safari";
if ((i = ua.indexOf(s)) >= 0) {
  this.isSF = true;return;
}
}

function DialogLoc() {
var dde = document.documentElement;
if (window.innerWidth) {
  var ww = window.innerWidth;
  var wh = window.innerHeight;
  var bgX = window.pageXOffset;
  var bgY = window.pageYOffset;
} else {
  var ww = dde.offsetWidth;
  var wh = dde.offsetHeight;
  var bgX = dde.scrollLeft;
  var bgY = dde.scrollTop;
}
t_DiglogX = (bgX + ((ww - t_DiglogW)/2));
t_DiglogY = (bgY + ((wh - t_DiglogH)/2));
}

function DialogShow(showdata,ow,oh,w,h) {
var objDialog = document.getElementById("DialogMove");
if (!objDialog) objDialog = document.createElement("div");
t_DiglogW = ow;
t_DiglogH = oh;
DialogLoc();
objDialog.id = "DialogMove";
var oS = objDialog.style;
oS.display = "block";
oS.top = t_DiglogY + "px";
oS.left = t_DiglogX + "px";
oS.margin = "0px";
oS.padding = "0px";
oS.width = w + "px";
oS.height = h + "px";
oS.position = "absolute";
oS.zIndex = "5";
oS.background = "#FFF";
oS.border = "solid #000 3px";
objDialog.innerHTML = showdata;
document.body.appendChild(objDialog);
}

function DialogHide() {
ScreenClean();
var objDialog = document.getElementById("DialogMove");
if (objDialog) objDialog.style.display = "none";
}

function ScreenConvert() {
var browser = new Browser();
var objScreen = gid("ScreenOver");
if (!objScreen) var objScreen = document.createElement("div");
var oS = objScreen.style;
objScreen.id = "ScreenOver";
oS.display = "block";
oS.top = oS.left = oS.margin = oS.padding = "0px";
if (document.body.clientHeight)    {
  var wh = document.body.clientHeight + "px";
} else if (window.innerHeight) {
  var wh = window.innerHeight + "px";
} else {
  var wh = "100%";
}
oS.width = "100%";
oS.height = wh;
oS.position = "absolute";
oS.zIndex = "3";
if ((!browser.isSF) && (!browser.isOP)) {
  oS.background = "#181818";
} else {
  oS.background = "#F0F0F0";
}
oS.filter = "alpha(opacity=40)";
oS.opacity = 40/100;
oS.MozOpacity = 40/100;
document.body.appendChild(objScreen);
var allselect = gname("select");
for (var i=0; i<allselect.length; i++) allselect[i].style.visibility = "hidden";
}

function ScreenClean() {
var objScreen = document.getElementById("ScreenOver");
if (objScreen) objScreen.style.display = "none";
var allselect = gname("select");
for (var i=0; i<allselect.length; i++) allselect[i].style.visibility = "visible";
}


/*****************************************
**函数：数字转字符串函数
* 参数:字符串,长度
* 主要防止以0开头的数字字符转8进制
******************************************/
function parseString(str,len)
{
	str=str.toString();
	var str1=parseInt(str,10);
	var lennow=str1.toString().length;
	for(;lennow<len;lennow++)
	{
		str1="0"+str1;
	}
	return str1;
}

/********************************
 * 判断币种代码转换描述
 * add by xiewenxuan
 *********************************/
function currExchange(currValue){
	var currId;

	if(currValue=="840"){
		currId="USD";
	}else if (currValue=="344"){
		currId="HKD";
	}else if (currValue=="458"){
		currId="MYR";
	}else if (currValue=="051"){
		currId="AMD";
	}else if (currValue=="156"){
		currId="CNY";
	}else if (currValue=="702"){
		currId="SGD";
	}else if (currValue=="764"){
		currId="THB";
	}else if (currValue=="446"){
		currId="MOP";
	}else if (currValue=="608"){
		currId="PHP";
	}else if (currValue=="410"){
		currId="KRW";
	}else if (currValue=="392"){
		currId="JPY";
	}else if (currValue=="704"){
		currId="VND";
	}else{
		currId=currValue;
	}
	return currId;
}


/**
 * 将描述符转换为币种代码
 * add by xwx 20090521
 */
function currDecToNum(currValue){
	var currId;

	if(currValue=="USD"){
		currId="840";
	}else if (currValue=="HKD"){
		currId="344";
	}else if (currValue=="MYR"){
		currId="458";
	}else if (currValue=="AMD"){
		currId="051";
	}else if (currValue=="CNY"){
		currId="156";
	}else if (currValue=="SGD"){
		currId="702";
	}else if (currValue=="THB"){
		currId="764";
	}else if (currValue=="MOP"){
		currId="446";
	}else if (currValue=="PHP"){
		currId="608";
	}else if (currValue=="KRW"){
		currId="410";
	}else if (currValue=="JPY"){
		currId="392";
	}else if (currValue=="VND"){
		currId="704";
	}else{
		currId=currValue;
	}
	return currId;
}

/**
 * 判断是否是金额的格式
 * @param data
 * @returns {Boolean}
 */
function isMoney(data){
	/*var a=/^[0-9]*(\.[0-9]{1,2})?$/;*/
	var a= /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if(!a.test(data))
	{
		alert("资金格式不正确");
		return false;
	}
	return true;
}

/**
 * 左补0
 * @param num
 * @param n
 * @returns {String}
 */
function pad(num, n) {
	num = Math.round(num);
    var len = num.toString().length;  
    while(len < n) {  
        num = "0" + num;
        len++;
    }  
    return num;
}  


function isNull(str){
	if(str == null || str == "" || str == undefined ){
		alert("请完善数据");
		return true;
	}
	return false;
}

/**
 * 函数：转换交易撤销标志
 */
function revInd2string(str){
	var revInd;
	if(str=="O"){
		revInd = "正常";
	}else if(str=="R"){
		revInd = "撤销";
	}else{
		revInd = "";
	}
	return revInd;
}

/**
 * 函数：身份证代码转换身份证描述，例如，将01转为身份证18位
 * 参数：身份证类型的标识码
 * 返回：身份证类型的描述
 */
function changeIdType(str){
	var idType = "";
	if(str == "01"){
		idType = "身份证18位";
	}else if(str == "02"){
		idType = "身份证15位";
	}else if(str == "03"){
		idType = "军官证";
	}else if(str == "04"){
		idType = "台胞证";
	}else if(str == "05"){
		idType = "护照";
	}else if(str == "10"){
		idType = "国外护照";
	}else if(str == "20"){
		idType = "其他";
	}else{
		idType = "";
	}
	return idType;
}

/**
 * 函数：性别代码转换性别描述，例如，将M转为男
 * 参数：性别的标识码
 * 返回：性别的描述
 */
function changeGender(str){
	var gender = "";
	if(str == "M"){
		gender = "男";
	}else if(str == "F"){
		gender = "女";
	}else{
		gender = "";
	}
	return gender;
}

/**
 * 函数：交易状态转换描述，例如，0-正常；1-授权未请款
 * 参数：交易状态的标识码
 * 返回：交易状态的描述
 */
function changeStatus(str){
	var status = "";
	if(str == "0"){
		status = "正常";
	}else if(str == "1"){
		status = "授权未请款";
	}else{
		status = "";
	}
	return status;
}

/**
 * 函数：账户状态转换描述，例如，00-正常；01-冻结;10-销户.
 * 参数：账户状态的标识码
 * 返回：账户状态的描述
 */
function changeAcctStatusv(str){
	var acctStatus = "";
	if(str == "00"){
		acctStatus = "正常";
	}else if(str == "01"){
		acctStatus = "冻结";
	}else if(str == "10"){
		acctStatus = "销户";
	}else{
		acctStatus = "";
	}
	return acctStatus;
}

/**
 * 函数：如果登录网点代码为空，需要用户下拉选择登录网点代码
 * 参数：DIV的名称
 * 返回：插入下拉列表框
 */
function addOption(divName,optionName){
	document.getElementById(divName).style.display = "block";
	document.getElementById(divName).innerHTML = "<select id="+optionName+">" +
			"<option value=-1>--请选择登录网点--</option>" +
			"<option value=Gamedo>掌上纵横</option>" +
			"<option value=00010001>爱钱帮</option>" +
			"</select>";
}

/**
 * 函数：日期格式化函数
 * 参数：json返回日期，例如（201703,20170305）
 * 返回：格式化之后的日期  （2017/03,2017/03/05）
 */
function billDateFormat(dateStr){
	var ret = "";
	var len = dateStr.length;
	var year = dateStr.substring(0,4);
	var month = dateStr.substring(4,6);
	var day = dateStr.substring(6,8);
	if(len > 6){
		ret = year+'/'+month+'/'+day;
	}else if (len > 4) {
		ret = year+'/'+month;
	}
	return ret;
}

/**
 * 函数：获取上一个月
 * 参数：日期
 * 返回：日期
 */
function getLastMonth(t){
    var year =t.substring(0,4);            //获取当前日期的年
    var month = t.substring(4,6);              //获取当前日期的月
 
    var year2 = year;
    var month2 = parseInt(month)-1;
    if(month2==0) {
        year2 = parseInt(year2)-1;
        month2 = 12;
    }
    
    if(month2<10) {
        month2 = '0'+month2;
    }
    var m = year2.toString();
    var n= month2.toString();
    var t2 = m+n;
    return t2;
}

/**
 * 函数：获取下一个月
 * 参数：日期
 * 返回：日期
 */
function getNextMonth(t){
	var year =t.substring(0,4);            //获取当前日期的年
	var month = t.substring(4,6);              //获取当前日期的月
	
	var year2 = year;
	var month2 = parseInt(month)+1;
	if(month2==13) {
		year2 = parseInt(year2)+1;
		month2 = 1;
	}
	
	if(month2<10) {
		month2 = '0'+month2;
	}
	var m = year2.toString();
	var n= month2.toString();
	var t2 = m+n;
	return t2;
}

/**
 * 函数：日期加减
 * 参数：日期
 * 返回：日期
 */
function getNextNDay(date, day){
	var a = new Date(date);
	a = a.valueOf();
	a = a + day * 24 * 60 * 60 * 1000;
	a = new Date(a).format('yyyy/MM/dd');
	return a;
}

/**
 * 日期格式化
 */
Date.prototype.format =function(format)
{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

//1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
// 3.将加法和加上校验位能被 10 整除。
function luhmCheck(bankno){
	if (bankno.length < 16 || bankno.length > 19) {
		//$("#banknoInfo").html("银行卡号长度必须在16到19之间");
		return false;
	}
	var num = /^\d*$/;  //全数字
	if (!num.exec(bankno)) {
		//$("#banknoInfo").html("银行卡号必须全为数字");
		return false;
	}
	//开头6位
	var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
	if (strBin.indexOf(bankno.substring(0, 2))== -1) {
		//$("#banknoInfo").html("银行卡号开头6位不符合规范");
		return false;
	}
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    
    //计算Luhm值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    
    if(lastNum==luhm){
    return true;
    }
    else{
    return false;
    }        
}
