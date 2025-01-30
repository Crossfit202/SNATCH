import { Skill } from "./Skill";

export class Personnel {
    personnelId: number;
    personnelName: string;
    species: string;
    profileImg: string;
    isAssigned: boolean;
    assigned?: boolean;
    crew: any;
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
