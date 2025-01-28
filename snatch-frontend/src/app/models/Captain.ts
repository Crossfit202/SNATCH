import { Crew } from "./Crew";
import { Leader } from "./Leader";

export class Captain {
    captainId: number;
    captainName: string;
    leader: Leader;
    crew: Crew | null;

    constructor(
        captainId: number = 0,
        captainName: string = '',
        leader: Leader = new Leader(1, 'Default Leader'), // ✅ Default Leader
        crew: Crew | null = null
    ) {
        this.captainId = captainId;
        this.captainName = captainName;
        this.leader = leader;
        this.crew = crew;
    }
}
