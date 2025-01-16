package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personnel")
public class Personnel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personnel_id")
	private int personnelId;
	
	@Column(name = "personnel_name")
	private String personnelName;
	
	@Column(name = "species")
	private String species;
	
	@Column(name = "profile_img")
	private String profileImg;
	
	@Column(name = "is_assigned")
	private boolean isAssigned;
	
	@Column(name = "crew_id")
	private int crewId;

	public Personnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Personnel(int personnelId, String personnelName, String species, String profileImg, boolean isAssigned,
			int crewId) {
		super();
		this.personnelId = personnelId;
		this.personnelName = personnelName;
		this.species = species;
		this.profileImg = profileImg;
		this.isAssigned = isAssigned;
		this.crewId = crewId;
	}

	public int getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public int getCrewId() {
		return crewId;
	}

	public void setCrewId(int crewId) {
		this.crewId = crewId;
	}

	@Override
	public String toString() {
		return "Personnel [personnelId=" + personnelId + ", personnelName=" + personnelName + ", species=" + species
				+ ", profileImg=" + profileImg + ", isAssigned=" + isAssigned + ", crewId=" + crewId + "]";
	}
	
	
	
	
}
