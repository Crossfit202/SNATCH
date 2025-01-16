package com.skillstorm.dtos;

public class CrewDTO {
	
	private String crewName;
	
	private int maxCapacity;
	
	private boolean avaiability;
	
	private boolean hasCaptain;
	
	private int captainId;

	public CrewDTO(String crewName, int maxCapacity, boolean avaiability, boolean hasCaptain, int captainId) {
		super();
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.avaiability = avaiability;
		this.hasCaptain = hasCaptain;
		this.captainId = captainId;
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

	public boolean isAvaiability() {
		return avaiability;
	}

	public void setAvaiability(boolean avaiability) {
		this.avaiability = avaiability;
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
		return "CrewDTO [crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", avaiability=" + avaiability
				+ ", hasCaptain=" + hasCaptain + ", captainId=" + captainId + "]";
	}
	
	

	
	

}
