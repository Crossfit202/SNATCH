package com.skillstorm.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	
	
	@ManyToOne
	@JoinColumn(name = "crew_id", referencedColumnName = "crew_id")
	@JsonIgnoreProperties({"personnels", "hasCaptain", "availability", "maxCapacity"})
	private Crew crew;
	
	@ManyToMany
	@JoinTable(name = "personnel_skill",
			   joinColumns = @JoinColumn(name = "personnel_id"),
			   inverseJoinColumns = @JoinColumn(name = "skill_id"))
	@JsonIgnoreProperties("personnels")
	private List<Skill> skills;

	public Personnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Personnel(int personnelId, String personnelName, String species, String profileImg, boolean isAssigned,
			Crew crew, List<Skill> skills) {
		super();
		this.personnelId = personnelId;
		this.personnelName = personnelName;
		this.species = species;
		this.profileImg = profileImg;
		this.isAssigned = false;
		this.crew = crew;
		this.skills = skills;
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

	public void setIsAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Crew getCrew() {
		return crew;
	}

	public void setCrew(Crew crew) {
		this.crew = crew;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Personnel [personnelId=" + personnelId + ", personnelName=" + personnelName + ", species=" + species
				+ ", profileImg=" + profileImg + ", isAssigned=" + isAssigned + ", crew=" + crew + ", skills=" + skills
				+ "]";
	}

	
}
