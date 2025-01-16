package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "crew")
public class Crew {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "crew_id")
	private int crewId;
	
	@Column(name = "crew_name")
	private String crewName;
	
	@Column(name = "max_capacity")
	private int maxCapacity;
	
	@Column(name = "availability")
	private boolean availability;
	
	@Column(name = "has_captain")
	private boolean hasCaptain;
	
	@Column(name = "captain_id")
	private int captainId;

	public Crew() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Crew(int crewId, String crewName, int maxCapacity, boolean availability, boolean hasCaptain, int captainId) {
		super();
		this.crewId = crewId;
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.availability = availability;
		this.hasCaptain = hasCaptain;
		this.captainId = captainId;
	}

	public int getCrewId() {
		return crewId;
	}

	public void setCrewId(int crewId) {
		this.crewId = crewId;
	}

	public String getCrewName() {
		return crewName;
	}

	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean isHasCaptain() {
		return hasCaptain;
	}

	public void setHasCaptain(boolean hasCaptain) {
		this.hasCaptain = hasCaptain;
	}

	public int getCaptainId() {
		return captainId;
	}

	public void setCaptainId(int captainId) {
		this.captainId = captainId;
	}

	@Override
	public String toString() {
		return "Crew [crewId=" + crewId + ", crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", availability="
				+ availability + ", hasCaptain=" + hasCaptain + ", captainId=" + captainId + "]";
	}
	
	
	
	
	
	
}
