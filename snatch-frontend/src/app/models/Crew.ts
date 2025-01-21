import { Captain } from './Captain';

export class Crew {
    crewId: number;
    crewName: string;
    captain: Captain | null;
    hasCaptain: boolean;
    personnels: any[]; // Adjust type if there's a Personnel model

    constructor(
        crewId: number = 0,
        crewName: string = '',
        captain: Captain | null = null,
        hasCaptain: boolean = false,
        personnels: any[] = []
    ) {
        this.crewId = crewId;
        this.crewName = crewName;
        this.captain = captain;
        this.hasCaptain = hasCaptain;
        this.personnels = personnels;
    }
}
