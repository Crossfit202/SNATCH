import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HeistService } from '../services/heist.service';
import { Heist } from '../models/Heist';
import { Crew } from '../models/Crew';
import { CrewService } from '../services/crew.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-heist', // Defines the component selector
  standalone: true, // This is a standalone Angular component
  imports: [CommonModule, FormsModule, NavbarComponent], // Import necessary modules
  templateUrl: './heist.component.html', // The HTML template for this component
  styleUrls: ['./heist.component.css'] // The CSS styles for this component
})
export class HeistComponent implements OnInit {
  // List of heists
  heists: any[] = [];

  // New heist object for adding a heist
  newHeist: Heist = new Heist();

  // List of available crews
  crews: Crew[] = [];

  // Tracks which heist is currently being edited
  editingHeistId: number | null = null;

  // Holds the selected heist for editing
  selectedHeist: Heist | null = null;

  constructor(
    private heistService: HeistService, // Injects the heist service
    private crewService: CrewService // Injects the crew service
  ) { }

  ngOnInit(): void {
    // Load all heists and crews on initialization
    this.loadHeists();
    this.loadCrews();
  }

  // Load all heists from the backend
  loadHeists(): void {
    this.heistService.getHeists().subscribe(data => {
      console.log("Heists fetched:", data); // Debugging log
      this.heists = data; // Store full object, including crew details
    }, error => {
      console.error("Error fetching heists:", error);
    });
  }


  // Load all crews from the backend
  loadCrews(): void {
    this.crewService.getAllCrews().subscribe(data => {
      this.crews = data;
    }, error => {
      console.error("Error fetching crews:", error);
    });
  }

  // Add a new heist
  addHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe((createdHeist) => {
      this.newHeist = new Heist(); // Reset form
      this.loadHeists(); // Fetch updated heist list
    }, error => {
      console.error("Error adding heist:", error);
    });
  }



  // Delete a heist
  deleteHeist(heistId: number): void {
    this.heistService.deleteHeist(heistId).subscribe(() => {
      this.loadHeists(); // Fetch updated heist list after deletion
    }, error => {
      console.error("Error deleting heist:", error);
    });
  }


  // Toggle edit mode for a specific heist
  toggleEdit(heistId: number): void {
    const heistToEdit = this.heists.find(h => h.heist.heistId === heistId);
    if (heistToEdit) {
      this.editingHeistId = heistId;
      this.selectedHeist = { ...heistToEdit.heist }; // Clone to avoid modifying original
    } else {
      console.error("Error: Heist not found for editing.");
    }
  }


  // Cancel edit mode
  cancelEdit(): void {
    this.editingHeistId = null;
    this.selectedHeist = null;
  }

  // Update a heist
  updateHeist(): void {
    if (this.selectedHeist && this.selectedHeist.heistId) {
      this.heistService.updateHeist(this.selectedHeist).subscribe(() => {
        this.editingHeistId = null;
        this.selectedHeist = null;
        this.loadHeists();
      }, error => {
        console.error("Error updating heist:", error);
      });
    } else {
      console.error("Error: Heist ID is missing, cannot update.");
    }
  }


}
