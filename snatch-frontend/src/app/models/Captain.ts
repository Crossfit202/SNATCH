import { Crew } from './Crew';
import { Leader } from './Leader';

export class Captain {
    captainId: number;
    captainName: string;
    leader: Leader | null;
    crew: Crew | null;

    constructor(captainId: number, captainName: string, leader: Leader | null = null, crew: Crew | null = null) {
        this.captainId = captainId;
        this.captainName = captainName;
        this.leader = leader;
        this.crew = crew;
    }
}