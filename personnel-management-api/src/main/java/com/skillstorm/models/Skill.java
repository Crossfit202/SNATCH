package com.skillstorm.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "skill")
public class Skill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private int skillId;
	
	@Column(name = "skill_name")
	private String skillName;
	
	@ManyToMany(mappedBy = "skills")
	@JsonIgnoreProperties({"skills", "crew"})
	private List<Personnel> personnels;

	public Skill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Skill(int skillId, String skillName, List<Personnel> personnels) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
		this.personnels = personnels;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}

	@Override
	public String toString() {
		return "Skill [skillId=" + skillId + ", skillName=" + skillName + ", personnels=" + personnels + "]";
	}
	
	
}
