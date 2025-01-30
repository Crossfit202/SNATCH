package com.skillstorm.dtos;

public class LeaderDTO {

	private String leaderName;

	public LeaderDTO(String leaderName) {
		super();
		this.leaderName = leaderName;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Override
	public String toString() {
		return "LeaderDTO [leaderName=" + leaderName + "]";
	}
	
	
}
