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
      this.heists.push({ heist: createdHeist, crew: null }); // Ensure correct structure
      this.newHeist = new Heist();
    }, error => {
      console.error("Error adding heist:", error);
    });
  }


  // Delete a heist
  deleteHeist(id: number): void {
    this.heistService.deleteHeist(id).subscribe(() => {
      this.heists = this.heists.filter(h => h.heistId !== id);
    }, error => {
      console.error("Error deleting heist:", error);
    });
  }

  // Toggle edit mode for a specific heist
  toggleEdit(heistId: number): void {
    if (this.editingHeistId === heistId) {
      this.cancelEdit(); // Cancel if clicked again
    } else {
      this.editingHeistId = heistId;
      this.selectedHeist = { ...this.heists.find(h => h.heistId === heistId)! };
    }
  }

  // Cancel edit mode
  cancelEdit(): void {
    this.editingHeistId = null;
    this.selectedHeist = null;
  }

  // Update a heist
  updateHeist(): void {
    if (this.selectedHeist) {
      this.heistService.updateHeist(this.selectedHeist).subscribe(updatedHeist => {
        const index = this.heists.findIndex(h => h.heist.heistId === updatedHeist.heistId);
        if (index !== -1) {
          this.heists[index].heist = updatedHeist; // Update only the heist part
        }
        this.cancelEdit();
      }, error => {
        console.error("Error updating heist:", error);
      });
    }
  }

}
