import { Crew } from "./Crew";

export class Heist {
    heistId: number;
    description: string;
    location: string;
    isAssigned: boolean;
    status: string;
    crewId: number;

    constructor(heistId: number, description: string, location: string, isAssigned: boolean, status: string, crewId: number) {
        this.heistId = heistId;
        this.description = description;
        this.location = location;
        this.isAssigned = isAssigned;
        this.status = status;
        this.crewId = crewId;

    }
}