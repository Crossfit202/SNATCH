import { Personnel } from './Personnel'; // Adjust the path as necessary

export class Skill {
    skillId: number; // Unique identifier for the skill
    skillName: string; // Name of the skill
    personnels: Personnel[]; // List of personnel associated with the skill

    constructor(
        skillId: number = 0,
        skillName: string = '',
        personnels: Personnel[] = []
    ) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.personnels = personnels;
    }
}
