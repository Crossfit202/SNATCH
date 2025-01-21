export class Leader {
    leaderId: number;
    leaderName: string;

    constructor(
        leaderId: number = 0,
        leaderName: string = ''
    ) {
        this.leaderId = leaderId;
        this.leaderName = leaderName;
    }
}
