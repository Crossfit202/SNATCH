import { Leader } from './Leader';
import { Crew } from './Crew';

export class Captain {
    captainId: number; // Unique identifier for the captain
    captainName: string; // Name of the captain
    leader: Leader | null; // Reference to the associated leader
    crew: Crew | null; // Reference to the associated crew

    constructor(
        captainId: number = 0,
        captainName: string = '',
        leader: Leader | null = null,
        crew: Crew | null = null
    ) {
        this.captainId = captainId;
        this.captainName = captainName;
        this.leader = leader;
        this.crew = crew;
    }
}
