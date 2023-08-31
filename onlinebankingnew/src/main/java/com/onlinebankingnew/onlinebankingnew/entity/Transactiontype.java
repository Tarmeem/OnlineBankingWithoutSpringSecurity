package com.onlinebankingnew.onlinebankingnew.entity;

public enum Transactiontype {

		DEPOSIT("DEPOSIT"),
		WITHDRAW("WITHDRAW");
		
		private final String displayName;

		private Transactiontype(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
		}
		//fhj
		
		
	

}
