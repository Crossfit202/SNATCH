package com.skillstorm.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	/*
	 * ENTITY RELATIONSHIPS
	 * 	- no FK in Leader table; FK in Captain table
	 * 
	 * FetchType.EAGER
	 * 	- originally added to ensure respective objects are immediately loaded in within a transaction (see Service methods)
	 * 	- may want to test eventually if it is even needed
	 */
	@OneToMany(mappedBy = "leader", fetch = FetchType.EAGER)
	@JsonIgnoreProperties("leader")
	private List<Captain> captains;

	
	// CONSTRUCTORS
	public Leader() {
		super();
	}

	public Leader(int leaderId, String leaderName, List<Captain> captains) {
		super();
		this.leaderId = leaderId;
		this.leaderName = leaderName;
		this.captains = captains;
	}

	
	// GETTERS AND SETTERS
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

	public List<Captain> getCaptains() {
		return captains;
	}

	public void setCaptains(List<Captain> captains) {
		this.captains = captains;
	}

	// TO STRING
	@Override
	public String toString() {
		return "Leader [leaderId=" + leaderId + ", leaderName=" + leaderName + ", captains=" + captains + "]";
	}

}
