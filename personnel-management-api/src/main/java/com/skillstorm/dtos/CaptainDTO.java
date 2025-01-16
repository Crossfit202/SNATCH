package com.skillstorm.dtos;

public class CaptainDTO {

	private String captainName;
	
	// FOREIGN KEY - MODIFY LATER
	private int leaderId;

	public CaptainDTO(String captainName, int leaderId) {
		super();
		this.captainName = captainName;
		this.leaderId = leaderId;
	}

	public String getCaptainName() {
		return captainName;
	}

	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	@Override
	public String toString() {
		return "CaptainDTO [captainName=" + captainName + ", leaderId=" + leaderId + "]";
	}
	
}
