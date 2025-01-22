export class Loot {
    lootId: number;
    itemName: string;
    quantity: number;

    constructor(lootId: number = 0, itemName: string = '', quantity: number = 0) {
        this.lootId = lootId;
        this.itemName = itemName;
        this.quantity = quantity;
    }
}