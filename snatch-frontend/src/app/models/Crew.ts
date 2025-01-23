import { Personnel } from './Personnel';
import { Captain } from './Captain';

export class Crew {
    crewId: number;
    crewName: string;
    maxCapacity: number;
    availability: boolean;
    hasCaptain: boolean;
    captain: Captain | null;
    personnels: Personnel[];

    constructor(
        crewId: number = 0,
        crewName: string = '',
        maxCapacity: number = 0,
        availability: boolean = false,
        hasCaptain: boolean = false,
        captain: Captain | null = null,
        personnels: Personnel[] = []
    ) {
        this.crewId = crewId;
        this.crewName = crewName;
        this.maxCapacity = maxCapacity;
        this.availability = availability;
        this.hasCaptain = hasCaptain;
        this.captain = captain;
        this.personnels = personnels;
    }
}
