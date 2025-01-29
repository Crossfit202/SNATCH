import { Skill } from "./Skill";

export class Personnel {
    personnelId: number;
    personnelName: string;
    species: string;
    profileImg: string;
    isAssigned: boolean; // The existing property
    assigned?: boolean;  // ✅ Add this optional property to match the backend response
    crew: any; // Assuming crew is nullable or has a specific type
    skills: Skill[];

    constructor(
        personnelId: number,
        personnelName: string,
        species: string,
        profileImg: string,
        isAssigned: boolean,
        crew: any,
        skills: Skill[]
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
