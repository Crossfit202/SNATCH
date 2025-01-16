package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "captain")
public class Captain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "captain_id")
	private int captainId;
	
	@Column(name = "captain_name")
	private String captainName;
	
	// FOREIGN KEY - MODIFY LATER
	@Column(name = "leader_id")
	private int leaderId;
	
	public Captain() {
		super();
	}

	public Captain(int captainId, String captainName, int leaderId) {
		super();
		this.captainId = captainId;
		this.captainName = captainName;
		this.leaderId = leaderId;
	}

	public int getCaptainId() {
		return captainId;
	}

	public void setCaptainId(int captainId) {
		this.captainId = captainId;
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
		return "Captain [captainId=" + captainId + ", captainName=" + captainName + ", leaderId=" + leaderId + "]";
	}
	
}
