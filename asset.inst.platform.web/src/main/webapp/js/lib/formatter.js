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
			timeformatter3 :function(val){
				if(val){
					val = parseInt(val, 10);
					var date = new Date(val);
					return date.format('yyyy-M-d');
				}
				return '';
			},
			/**
			 * 函数:将时间戳转成字符串
			 * 入参:字符串/数值
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
			//返回元
			moneyTenThousand: function(val){
				if(((val != null) && (val != '')) && (val != 0)){
					var val = val;
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
				return 0;
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
			isNull: function (val){
				if(val!=""&&val!=null){
					return val;
				}
				return "";
			},
			null2Zero: function (val){
				if(val!=""&&val!=null){
					return val;
				}
				return '0.00';
			}
	};	
	return _this;
});

