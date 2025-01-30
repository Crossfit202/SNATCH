import { Component, OnInit } from '@angular/core';
import { Loot } from '../models/Loot'; // Import Loot model
import { LootService } from '../services/loot.service'; // Import LootService for API calls
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-loot', // Component selector used in templates
  imports: [CommonModule, FormsModule, NavbarComponent], // Import necessary modules
  templateUrl: './loot.component.html', // Reference to the HTML template
  styleUrl: './loot.component.css' // Reference to the CSS file
})
export class LootComponent implements OnInit {

  loots: Loot[] = []; // Stores all loot items
  newLoot: Loot = new Loot(); // Stores new loot being added
  editingLootId: number | null = null; // Track which loot item is being edited
  selectedLoot: Loot | null = null; // Stores the loot item being edited

  // Inject the LootService for API interactions
  constructor(private lootService: LootService) { }

  // Lifecycle hook: runs when the component is initialized
  ngOnInit(): void {
    this.loadLoot(); // Load loot data from API
  }

  // Fetch all loot from the API
  loadLoot(): void {
    this.lootService.getAllLoot().subscribe(data => {
      this.loots = data; // Assign response data to `loots` array
    });
  }

  // Add a new loot item
  addLoot(): void {
    this.lootService.addLoot(this.newLoot).subscribe(data => {
      this.loots.push(data); // Add new loot to the list
      this.newLoot = new Loot(); // Reset form after adding
    });
  }

  // Delete a loot item
  deleteLoot(id: number): void {
    this.lootService.deleteLoot(id).subscribe(() => {
      this.loots = this.loots.filter(loot => loot.lootId !== id); // Remove deleted loot from list
    });
  }

  // Toggle edit mode for a specific loot item
  toggleEdit(loot: Loot): void {
    if (this.editingLootId === loot.lootId) {
      this.cancelEdit(); // If clicking same loot, cancel edit mode
    } else {
      this.editingLootId = loot.lootId; // Set the loot being edited
      this.selectedLoot = { ...loot }; // Create a copy of the loot to avoid reference issues
    }
  }

  // Save changes to an edited loot item
  updateLoot(): void {
    if (!this.selectedLoot) return; // Ensure a loot item is selected before updating

    this.lootService.addLoot(this.selectedLoot).subscribe(updatedLoot => {
      const index = this.loots.findIndex(l => l.lootId === updatedLoot.lootId);
      if (index !== -1) {
        this.loots[index] = updatedLoot; // Update the loot list with the modified loot
      }
      this.cancelEdit(); // Exit edit mode after saving
    });
  }

  // Cancel edit mode and reset selectedLoot
  cancelEdit(): void {
    this.editingLootId = null; // Reset editing state
    this.selectedLoot = new Loot(0, '', 0); // Provide a default object instead of null
  }

}
