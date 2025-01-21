package com.skillstorm.dtos;

import com.skillstorm.models.Leader;

public class CaptainDTO {

	private String captainName;
	
	private Leader leader;

	public CaptainDTO(String captainName, Leader leader) {
		super();
		this.captainName = captainName;
		this.leader = leader;
	}

	public String getCaptainName() {
		return captainName;
	}

	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	@Override
	public String toString() {
		return "CaptainDTO [captainName=" + captainName + ", leader=" + leader + "]";
	}


	
}
