package com.haogrgr.buka.consts;

public interface UserConst {

	public enum UserStatus {

		NORMAL("正常用户", "1"), DEL("无效用户", "-1");

		UserStatus(String desc, String value) {
			this.desc = desc;
			this.value = value;
		}

		private String desc;
		private String value;

		public String getDesc() {
			return desc;
		}

		public String getValue() {
			return value;
		}

	}

}
