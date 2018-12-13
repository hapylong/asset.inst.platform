/**
 * 主要用于定义工具类函数,如格式化金额,相关函数均可在此定义
 * 引入:jQuery库
 * */
define(['jquery'], function($){
	var _this = {
			/**
			 * 函数:字符串到金钱浮点型
			 * 入参:字符串
			 * 回参:金额
			 * */
			string2moneyfloat: function(indata){
				var v;
				if(Number(indata) == 0) return ('0.00');
				else v = Number(indata)
				//以下转为US模式
				var vv = parseFloat(v).toString();
				vv = vv + '.00';
				return vv;
			},
			//continue to expand...
			/**
			 * 函数:字符串到金钱浮点型
			 * 入参:字符串
			 * 回参:金额(分;返回不带.00)
			 * */
			money : function(indata)
			{
				var v;
				if(Number(indata)==0) {return ("0")};
				if(Number(indata)==-1){return ("无限制")}
				else v=Number(indata/100)
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
				vv=vv;
				return vv;
			},
			timeformatter :function(val){
				if(val){
					val = parseInt(val, 10);
					var date = new Date(val);
					return date.format('yyyy年M月d日 hh:mm:ss');
				}
				return '';
			},
			timeformatter2 :function(val){
				if(val){
					val = parseInt(val, 10);
					var date = new Date(val);
					return date.format('yyyy/M/d');
				}
				return '';
			},
			/**
			 * 函数:时间戳格式化
			 * 入参:数值
			 * 回参:字符串(2016/12/06 12:00:00)
			 * */
			datastampToString: function(datastamp){
				if(datastamp){
					datastamp = parseInt(datastamp, 10);
					var date = new Date(datastamp);
					return date.format('yyyy/MM/dd hh:mm:ss');
				}
				return '';
			},
			/**
			 * 函数:金额国际格式化
			 * 入参:数值
			 * 回参:字符串
			 * */
			formatterMoney: function(val){
				if(val != null){
					var n = 2;
					val = parseFloat((val + '').replace(/[^\d\.-]/g, '')).toFixed(n)
							+ '';
					var l = val.split('.')[0].split('').reverse();
					var r = val.split('.')[1];
					var t = '';
					for(var i = 0; i < l.length; i++){
						t += l[i]
								+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? ',' : '');
					}
					return t.split('').reverse().join('') + '.' + r;
				}
				return '0.00';
			},
			/**
			 * 函数:订单状态格式化
			 * 入参:字符串/数值
			 * 回参:字符串
			 * */
			formatterOrderStatus: function(orderStatus){
				if(orderStatus  == 0){
					return '通过';
				}else if(orderStatus  == 1){
					return '拒绝';
				}else if(orderStatus  == 2){
					return '审核中';
				}else if(orderStatus  == 3){
					return '已分期';
				}else if(orderStatus  == 4){
					return '待支付';
				}else if(orderStatus  == 5){
					return '医美';
				}else{
					return '';
				}
			},
			/**
			 * 函数:婚姻状况格式化
			 * 入参:字符串
			 * 回参:字符串
			 * */
			formatterMarriedStatus: function(marriedStatus){
				if(marriedStatus  == 'UNMARRIED'){
					return '未婚';
				}else if(marriedStatus  == 'MARRIED'){
					return '已婚，有子女';
				}else if(marriedStatus  == 'MARRIED_NONE'){
					return '已婚，无子女';
				}else if(marriedStatus  == 'DISSOCIATON'){
					return '离异';
				}else{
					return '';
				}
			},
			/**
			 * 函数:文化水平格式化
			 * 入参:字符串
			 * 回参:字符串
			 * */
			formatterCulturalLevel: function(culturalLevel){
				if(culturalLevel  == 'PRIMARY'){
					return '小学及以下';
				}else if(culturalLevel  == 'MIDDLE'){
					return '初中';
				}else if(culturalLevel  == 'JUNIOR'){
					return '高中';
				}else if(culturalLevel  == 'TECHNOLOGY'){
					return '中等技术学校';
				}else if(culturalLevel  == 'JC'){
					return '大专';
				}else if(culturalLevel  == 'COLLEGE'){
					return '本科';
				}else if(culturalLevel  == 'MASTER'){
					return '硕士及以上';
				}else{
					return '';
				}
			}
			
	};	
	return _this;
});

