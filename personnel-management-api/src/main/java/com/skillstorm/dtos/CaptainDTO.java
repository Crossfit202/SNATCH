package com.skillstorm.dtos;

import com.skillstorm.models.Crew;
import com.skillstorm.models.Leader;

public class CaptainDTO {

	private String captainName;
	
	private Leader leader;
	
	private Crew crew;

	public CaptainDTO(String captainName, Leader leader, Crew crew) {
		super();
		this.captainName = captainName;
		this.leader = leader;
		this.crew = crew;
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

	public Crew getCrew() {
		return crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	@Override
	public String toString() {
		return "CaptainDTO [captainName=" + captainName + ", leader=" + leader + ", crew=" + crew + "]";
	}

	


	
}
