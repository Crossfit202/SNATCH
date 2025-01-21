export class Personnel {
    personnelId: number;
    name: string;
    role: string;

    constructor(
        personnelId: number = 0,
        name: string = '',
        role: string = ''
    ) {
        this.personnelId = personnelId;
        this.name = name;
        this.role = role;
    }
}
