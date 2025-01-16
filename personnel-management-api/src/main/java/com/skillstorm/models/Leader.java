package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "leader")
public class Leader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leader_id")
	private int leaderId;
	
	@Column(name = "leader_name")
	private String leaderName;
	
	public Leader() {
		super();
	}

	public Leader(int leaderId, String leaderName) {
		super();
		this.leaderId = leaderId;
		this.leaderName = leaderName;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Override
	public String toString() {
		return "Leader [leaderId=" + leaderId + ", leaderName=" + leaderName + "]";
	}
	
	
}
