import { Component, OnInit } from '@angular/core';
import { Loot } from '../models/Loot';
import { LootService } from '../services/loot.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loot',
  imports: [CommonModule, FormsModule],
  templateUrl: './loot.component.html',
  styleUrl: './loot.component.css'
})
export class LootComponent implements OnInit {

  loots: Loot[] = [];
  newLoot: Loot = new Loot(0, '', 0);

  constructor(private lootService: LootService) { }


  ngOnInit(): void {
    this.loadLoot();
  }


  loadLoot(): void {

    this.lootService.getAllLoot().subscribe(data => {
      this.loots = data;
    });
  }

  updateLoot(): void {
    this.lootService.addLoot(this.newLoot).subscribe(data => {
      this.loots.push(data);
      this.newLoot = new Loot(0, '', 0);
    });
  }

  editLoot(loot: Loot): void {
    this.newLoot = { ...loot };
  }



  addLoot(): void {
    this.lootService.addLoot(this.newLoot).subscribe(data => {
      this.loots.push(data);
      this.newLoot = new Loot(0, '', 0);
    });
  }

  deleteLoot(id: number): void {
    this.lootService.deleteLoot(id).subscribe(() => {
      this.loots = this.loots.filter(loot => loot.lootId !== id);
    });
  }
}