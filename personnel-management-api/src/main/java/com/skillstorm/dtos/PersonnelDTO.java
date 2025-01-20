package com.skillstorm.dtos;

import com.skillstorm.models.Crew;

public class PersonnelDTO {
	
	private String personnelName;
	
	private String species;
	
	private String profileImg;
	
	private boolean isAssigned;
	
	private Crew crew;

	public PersonnelDTO(String personnelName, String species, String profileImg, boolean isAssigned, Crew crew) {
		super();
		this.personnelName = personnelName;
		this.species = species;
		this.profileImg = profileImg;
		this.isAssigned = isAssigned;
		this.crew = crew;
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

	public Crew getCrew() {
		return crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	@Override
	public String toString() {
		return "PersonnelDTO [personnelName=" + personnelName + ", species=" + species + ", profileImg=" + profileImg
				+ ", isAssigned=" + isAssigned + ", crew=" + crew + "]";
	}
	
	
}
