package com.skillstorm.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "leader_id", referencedColumnName = "leader_id")
	@JsonIgnoreProperties("captains")
	private Leader leader;
	
	@OneToOne(mappedBy = "captain", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"captain", "hasCaptain", "personnels"})
	private Crew crew;

	public Captain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Captain(int captainId, String captainName, Leader leader, Crew crew) {
		super();
		this.captainId = captainId;
		this.captainName = captainName;
		this.leader = leader;
		this.crew = crew;
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
		return "Captain [captainId=" + captainId + ", captainName=" + captainName + ", leader=" + leader + ", crew="
				+ crew + "]";
	}


	
}
