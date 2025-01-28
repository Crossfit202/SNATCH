import { Component, OnInit } from '@angular/core';
import { Loot } from '../models/Loot';
import { LootService } from '../services/loot.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-loot',
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './loot.component.html',
  styleUrl: './loot.component.css'
})
export class LootComponent implements OnInit {

  loots: Loot[] = [];
  newLoot: Loot = new Loot();
  editingLootId: number | null = null; // Track which loot is being edited
  selectedLoot: Loot | null = null; // Store loot being edited

  constructor(private lootService: LootService) { }

  ngOnInit(): void {
    this.loadLoot();
  }

  loadLoot(): void {
    this.lootService.getAllLoot().subscribe(data => {
      this.loots = data;
    });
  }

  addLoot(): void {
    this.lootService.addLoot(this.newLoot).subscribe(data => {
      this.loots.push(data);
      this.newLoot = new Loot();
    });
  }

  deleteLoot(id: number): void {
    this.lootService.deleteLoot(id).subscribe(() => {
      this.loots = this.loots.filter(loot => loot.lootId !== id);
    });
  }

  toggleEdit(loot: Loot): void {
    if (this.editingLootId === loot.lootId) {
      this.cancelEdit();
    } else {
      this.editingLootId = loot.lootId;
      this.selectedLoot = { ...loot }; // Create a copy of the loot to avoid reference issues
    }
  }



  // Save changes to loot
  updateLoot(): void {
    if (!this.selectedLoot) return;

    this.lootService.addLoot(this.selectedLoot).subscribe(updatedLoot => {
      const index = this.loots.findIndex(l => l.lootId === updatedLoot.lootId);
      if (index !== -1) {
        this.loots[index] = updatedLoot;
      }
      this.cancelEdit();
    });
  }



  // Cancel edit mode
  cancelEdit(): void {
    this.editingLootId = null;
    this.selectedLoot = new Loot(0, '', 0); // Provide a default object instead of null
  }

}
