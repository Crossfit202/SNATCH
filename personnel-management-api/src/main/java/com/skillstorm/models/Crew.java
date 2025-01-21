package com.skillstorm.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	@OneToOne
	@JoinColumn(name = "captain_id", referencedColumnName = "captain_id")
	@JsonIgnoreProperties({"leader", "crew"})
	private Captain captain;
	
	@OneToMany(mappedBy = "crew", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("crew")
	private List<Personnel> personnels;

	public Crew() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Crew(int crewId, String crewName, int maxCapacity, boolean availability, boolean hasCaptain, Captain captain,
			List<Personnel> personnels) {
		super();
		this.crewId = crewId;
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.availability = availability;
		this.hasCaptain = hasCaptain;
		this.captain = captain;
		this.personnels = personnels;
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

	public Captain getCaptain() {
		return captain;
	}

	public void setCaptain(Captain captain) {
		this.captain = captain;
	}

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}

	@Override
	public String toString() {
		return "Crew [crewId=" + crewId + ", crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", availability="
				+ availability + ", hasCaptain=" + hasCaptain + ", captain=" + captain + ", personnels=" + personnels
				+ "]";
	}


	
}
