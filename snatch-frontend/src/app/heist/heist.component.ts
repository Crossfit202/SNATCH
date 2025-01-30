import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Heist } from '../models/Heist';
import { HeistService } from '../services/heist.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-heist', // Defines the component selector for use in templates
  templateUrl: './heist.component.html', // Links to the HTML template file
  styleUrls: ['./heist.component.css'], // Links to the component's styles
  standalone: true, // Ensures the component is independent (only include if using standalone components)
  imports: [CommonModule, FormsModule, NavbarComponent], // Modules and components imported for use
})
export class HeistComponent implements OnInit {

  heists: Heist[] = []; // Stores the list of heists retrieved from the backend
  newHeist: Heist = new Heist(0, '', '', '', 0, false); // Represents a new heist object for the form
  editingHeistId: number | null = null; // Tracks which heist is currently being edited
  selectedHeist: Heist | null = null; // Stores the selected heist for editing

  constructor(private heistService: HeistService) { } // Injects the HeistService for API calls

  // Lifecycle hook that runs when the component initializes
  ngOnInit(): void {
    this.loadHeists(); // Load heists from the backend when the component initializes
  }

  // Fetches all heists from the backend and stores them in the `heists` array
  loadHeists(): void {
    this.heistService.getAllHeist().subscribe((data) => {
      this.heists = data;
    });
  }

  // Adds a new heist using the values from `newHeist`
  addHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe((heist) => {
      this.heists.push(heist); // Add the new heist to the displayed list
      this.newHeist = new Heist(0, '', '', '', 0, false); // Reset form fields after submission
    });
  }

  // Deletes a heist by its ID
  deleteHeist(id: number): void {
    this.heistService.deleteHeist(id).subscribe(() => {
      this.heists = this.heists.filter((heist) => heist.heistId !== id); // Remove the deleted heist from the list
    });
  }

  // Toggles edit mode for a specific heist
  toggleEdit(heist: Heist): void {
    this.editingHeistId = this.editingHeistId === heist.heistId ? null : heist.heistId; // Toggle edit state
    this.selectedHeist = { ...heist }; // Create a copy of the heist for editing
  }

  // Updates the selected heist with new values
  updateHeist(): void {
    if (!this.selectedHeist) return; // Exit if no heist is selected

    this.heistService.updateHeist(this.selectedHeist.heistId, this.selectedHeist).subscribe(() => {
      this.loadHeists(); // Refresh the list after updating
      this.editingHeistId = null; // Exit edit mode
      this.selectedHeist = null; // Reset selected heist
    });
  }

  // Cancels editing and resets selection
  cancelEdit(): void {
    this.editingHeistId = null;
    this.selectedHeist = null;
  }
}
