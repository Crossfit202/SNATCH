import { Crew } from './Crew'; // Adjust the path as necessary
import { Skill } from './Skill'; // Adjust the path as necessary

export class Personnel {
    personnelId: number; // Unique identifier for the personnel
    personnelName: string; // Name of the personnel
    species: string; // Species of the personnel
    profileImg: string; // Profile image URL of the personnel
    isAssigned: boolean; // Indicates if the personnel is assigned
    crew: Crew | null; // Associated crew, nullable if not assigned to a crew
    skills: Skill[]; // List of associated skills

    constructor(
        personnelId: number = 0,
        personnelName: string = '',
        species: string = '',
        profileImg: string = '',
        isAssigned: boolean = false,
        crew: Crew | null = null,
        skills: Skill[] = []
    ) {
        this.personnelId = personnelId;
        this.personnelName = personnelName;
        this.species = species;
        this.profileImg = profileImg;
        this.isAssigned = isAssigned;
        this.crew = crew;
        this.skills = skills;
    }
}
