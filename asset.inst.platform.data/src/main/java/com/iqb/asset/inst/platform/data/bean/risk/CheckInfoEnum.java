package com.iqb.asset.inst.platform.data.bean.risk;

public enum CheckInfoEnum {
	
	/** 婚姻状况 */
	//未婚
	UNMARRIED{
		public String getName() {
			return "未婚";
		}

		public String getCode() {
			return "UNMARRIED";
		}
		
	},
	
	//已婚，有子女
	MARRIED{
		public String getName() {
			return "已婚，有子女";
		}

		public String getCode() {
			return "MARRIED";
		}
		
	},
	
	//已婚，无子女
	MARRIED_NONE{
		public String getName() {
			return "已婚，无子女";
		}
		
		public String getCode() {
			return "MARRIED_NONE";
		}
		
	},
	
	//离异
	DISSOCIATON{
		public String getName() {
			return "离异";
		}
		
		public String getCode() {
			return "DISSOCIATON";
		}
		
	},
	
	/** 学历类 */
	//小学及以下
	PRIMARY{
		public String getName() {
			return "小学及以下";
		}

		public String getCode() {
			return "PRIMARY";
		}
		
	},
	
	//初中
	MIDDLE{
		public String getName() {
			return "初中";
		}

		public String getCode() {
			return "MIDDLE";
		}
		
	},
	
	//高中
	JUNIOR{
		public String getName() {
			return "高中";
		}

		public String getCode() {
			return "JUNIOR";
		}
		
	},
	
	//中等技术学校
	TECHNOLOGY{
		public String getName() {
			return "中等技术学校";
		}

		public String getCode() {
			return "TECHNOLOGY";
		}
		
	},
	
	//大专
	JC{
		public String getName() {
			return "大专";
		}

		public String getCode() {
			return "JC";
		}
		
	},
	
	//本科
	COLLEGE{
		public String getName() {
			return "本科";
		}

		public String getCode() {
			return "COLLEGE";
		}
		
	},
	
	//硕士及以上
	MASTER{
		public String getName() {
			return "硕士及以上";
		}

		public String getCode() {
			return "MASTER";
		}
	},
	
	/** 职务类 */
	//私营企业主/合作人
	COOPERATOR{
		public String getName() {
			return "私营企业主/合作人";
		}

		public String getCode() {
			return "COOPERATOR";
		}
	},
	
	//企事业高级管理层/法人/大股东
	TOP_MANAGER{
		public String getName() {
			return "企事业高级管理层/法人/大股东";
		}

		public String getCode() {
			return "TOP_MANAGER";
		}
	},
	
	//中层管理人员
	MIDDLE_MANAGER {
		public String getName() {
			return "中层管理人员";
		}

		public String getCode() {
			return "MIDDLE_MANAGER";
		}
	},
	
	//普通员工
	WORKER {
		public String getName() {
			return "普通员工";
		}

		public String getCode() {
			return "WORKER";
		}
	},
	
	//操作层面蓝领
	BLUE_COLLAR {
		public String getName() {
			return "操作层面蓝领";
		}

		public String getCode() {
			return "BLUE_COLLAR";
		}
	},
	
	//操作层面蓝领
	OTHER {
		public String getName() {
			return "其他";
		}

		public String getCode() {
			return "OTHER";
		}
	};
	
	public abstract String getName();
	
	public abstract String getCode();
	
	//插入风控信息时code转为Name
	public static String getCheckInfoName(String code) {
		for (CheckInfoEnum obj : CheckInfoEnum.values()) {
			if (code == null || "".equals(code))
				break;
			if(code.equals(obj.getCode())) {
				return obj.getName();
			}
		}
		return null;
	}
	
	//页面回显时风控信息里的Name转为Code
	public static String getCheckInfoCode(String name) {
		for (CheckInfoEnum obj : CheckInfoEnum.values()) {
			if (name == null || "".equals(name))
				break;
			if(name.equals(obj.getName())) {
				return obj.getCode();
			}
		}
		return null;
	}

}
