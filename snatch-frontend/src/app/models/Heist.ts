export class Heist {
    heistId: number; // Unique identifier for the heist
    description: string; // Description of the heist
    location: string; // Location where the heist will occur
    isAssigned: boolean; // Indicates if the heist has been assigned to a crew
    status: string; // Status of the heist (e.g., "Planned", "In Progress", "Completed")
    crewId: number; // ID of the crew assigned to the heist

    constructor(
        heistId: number = 0,
        description: string = '',
        location: string = '',
        isAssigned: boolean = false,
        status: string = '',
        crewId: number = 0
    ) {
        this.heistId = heistId;
        this.description = description;
        this.location = location;
        this.isAssigned = isAssigned;
        this.status = status;
        this.crewId = crewId;
    }
}
