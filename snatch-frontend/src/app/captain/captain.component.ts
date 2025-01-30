import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Leader } from '../models/Leader';
import { Crew } from '../models/Crew';
import { LeaderService } from '../services/leader.service';
import { CrewService } from '../services/crew.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-captain', // Defines the component selector
  standalone: true, // This component is a standalone Angular component
  imports: [CommonModule, FormsModule, NavbarComponent], // Importing necessary modules
  templateUrl: './captain.component.html', // The HTML template for this component
  styleUrls: ['./captain.component.css'] // The CSS styles for this component
})
export class CaptainComponent implements OnInit {
  // Holds the list of captains
  captains: Captain[] = [];

  // Default value for creating a new captain
  newCaptain: Captain = new Captain(0, '', new Leader(1, 'Default Leader'), null);

  // Holds the list of available leaders
  leaders: Leader[] = [];

  // Holds the list of available crews
  crews: Crew[] = [];

  // Tracks the captain currently being edited
  editingCaptainId: number | null = null;

  // Holds the data of the selected captain for editing
  selectedCaptain: Captain | null = null;

  constructor(
    private captainService: CaptainService, // Injects the captain service
    private leaderService: LeaderService, // Injects the leader service
    private crewService: CrewService // Injects the crew service
  ) { }

  ngOnInit(): void {
    // Load data when the component initializes
    this.loadCaptains();
    this.loadLeaders();
    this.loadCrews();
  }

  // Loads all captains from the backend
  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe(data => {
      this.captains = data.map(captain => ({
        ...captain,
        leader: captain.leader
          ? new Leader(captain.leader.leaderId, captain.leader.leaderName || 'Unknown')
          : new Leader(1, 'Unknown'), // Ensures leader always exists
        crew: captain.crew
          ? new Crew(captain.crew.crewId, captain.crew.crewName || 'Unknown')
          : null
      }));
    });
  }

  // Loads all leaders from the backend
  loadLeaders(): void {
    this.leaderService.getLeaders().subscribe(data => {
      this.leaders = data;
    });
  }

  // Loads all crews from the backend
  loadCrews(): void {
    this.crewService.getAllCrews().subscribe(data => {
      this.crews = data;
    });
  }

  // Adds a new captain to the database
  addCaptain(): void {
    if (!this.newCaptain.leader) {
      alert("A leader is required to create a captain.");
      return;
    }

    // Constructs the payload object to send to the backend
    const captainPayload = {
      captainId: 0, // Ensures captainId is initialized
      captainName: this.newCaptain.captainName,
      leader: {
        leaderId: this.newCaptain.leader.leaderId,
        leaderName: this.newCaptain.leader.leaderName
      }, // Ensures leader details are included

      crew: this.newCaptain.crew
        ? {
          crewId: this.newCaptain.crew.crewId,
          crewName: this.newCaptain.crew.crewName,
          maxCapacity: this.newCaptain.crew.maxCapacity ?? 0,  // Ensures required fields are set
          availability: this.newCaptain.crew.availability ?? true,
          hasCaptain: this.newCaptain.crew.hasCaptain ?? false,
          captain: null,  // Avoids circular reference
          personnels: []  // Empty array to prevent issues
        }
        : null // Allows null value for crew
    };

    // Sends the request to the backend to create a new captain
    this.captainService.createCaptain(captainPayload).subscribe((createdCaptain) => {
      // Adds the newly created captain to the list
      this.captains.push(
        new Captain(
          createdCaptain.captainId,
          createdCaptain.captainName,
          new Leader(createdCaptain.leader.leaderId, createdCaptain.leader.leaderName),
          createdCaptain.crew
            ? new Crew(
              createdCaptain.crew.crewId,
              createdCaptain.crew.crewName,
              createdCaptain.crew.maxCapacity,
              createdCaptain.crew.availability,
              createdCaptain.crew.hasCaptain,
              createdCaptain.crew.captain,  // Ensures this is correctly typed
              createdCaptain.crew.personnels || []  // Provides an empty array if undefined
            )
            : null
        )
      );

      // Resets the form for adding a new captain
      this.newCaptain = new Captain(0, '', new Leader(1, 'Default Leader'), null);
    });
  }

  // Deletes a captain from the list
  deleteCaptain(id: number): void {
    this.captainService.deleteCaptain(id).subscribe(() => {
      // Filters out the deleted captain from the list
      this.captains = this.captains.filter(c => c.captainId !== id);
    });
  }

  // Toggles the edit mode for a specific captain
  toggleEdit(captainId: number): void {
    if (this.editingCaptainId === captainId) {
      this.cancelEdit(); // Cancels editing if the same captain is clicked again
    } else {
      this.editingCaptainId = captainId;
      // Finds the selected captain and makes a copy of its data
      this.selectedCaptain = { ...this.captains.find(c => c.captainId === captainId)! };
    }
  }

  // Cancels the edit mode
  cancelEdit(): void {
    this.editingCaptainId = null;
    this.selectedCaptain = null;
  }

  // Updates the details of a captain
  updateCaptain(): void {
    if (this.selectedCaptain) {
      this.captainService.updateCaptain(this.selectedCaptain).subscribe(updatedCaptain => {
        // Finds the index of the updated captain in the list
        const index = this.captains.findIndex(c => c.captainId === updatedCaptain.captainId);
        if (index !== -1) {
          this.captains[index] = updatedCaptain; // Updates the captain in the list
        }
        this.cancelEdit(); // Exits edit mode
      });
    }
  }
}
