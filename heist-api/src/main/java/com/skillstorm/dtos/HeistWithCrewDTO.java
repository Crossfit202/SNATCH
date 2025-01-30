package com.skillstorm.dtos;

import com.skillstorm.models.Heist;

public class HeistWithCrewDTO {
    private Heist heist;
    private CrewDTO crew;
    
	public HeistWithCrewDTO(Heist heist, CrewDTO crew) {
		super();
		this.heist = heist;
		this.crew = crew;
	}

	public Heist getHeist() {
		return heist;
	}

	public void setHeist(Heist heist) {
		this.heist = heist;
	}

	public CrewDTO getCrew() {
		return crew;
	}

	public void setCrew(CrewDTO crew) {
		this.crew = crew;
	}

	@Override
	public String toString() {
		return "HeistWithCrewDTO [heist=" + heist + ", crew=" + crew + "]";
	}
    
    
}
