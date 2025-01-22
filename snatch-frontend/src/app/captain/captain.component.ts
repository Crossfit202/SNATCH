import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Leader } from '../models/Leader';
import { Crew } from '../models/Crew';
import { LeaderService } from '../services/leader.service';

// Define the Angular component
@Component({
  selector: 'app-captain', // Defines the HTML tag for this component
  standalone: true, // Indicates that this is a standalone component (doesn't rely on an NgModule)
  imports: [CommonModule, FormsModule], // Includes CommonModule and FormsModule for directives and data binding
  templateUrl: './captain.component.html', // Specifies the HTML file for the component template
  styleUrls: ['./captain.component.css'] // Specifies the CSS file for the component styling
})
export class CaptainComponent implements OnInit {
  // Array to hold the list of captains, initialized as an empty array
  captains: Captain[] = [];

  // Object to represent a new captain to be added, initialized with default values
  newCaptain: Captain = new Captain(0, '');

  // Array to hold the list of leaders for the dropdown, initialized as an empty array
  leaders: Leader[] = [];

  // Array to hold the list of crews for the dropdown, initialized as an empty array
  crews: Crew[] = [];

  // Constructor to inject the CaptainService into the component
  constructor(private captainService: CaptainService, private leaderService: LeaderService) { }

  // Lifecycle hook that gets called once the component is initialized
  ngOnInit(): void {
    // Load the list of captains from the backend
    this.loadCaptains();
    // Load the list of leaders for the dropdown
    this.loadLeaders();
    // // Load the list of crews for the dropdown
    // this.loadCrews();
  }

  // Method to fetch the list of captains from the backend and assign it to the captains array
  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe(data => {
      this.captains = data; // Update the captains array with the data from the API
    });
  }

  // Method to fetch the list of leaders from the backend and assign it to the leaders array
  loadLeaders(): void {
    this.leaderService.getLeaders().subscribe(data => {
      this.leaders = data; // Update the leaders array with the data from the API
    });
  }

  updateCaptain(): void {
    this.captainService.updateCaptain(this.newCaptain).subscribe(updatedCaptain => {
      // Update the local captains array with the updated captain object
      this.captains = this.captains.map(c => c.captainId === updatedCaptain.captainId ? updatedCaptain : c);
    });
  }

  editCaptain(captain: Captain): void {
    this.newCaptain = { ...captain }; // Create a copy of the captain object
  }

  // // Method to fetch the list of crews from the backend and assign it to the crews array
  // loadCrews(): void {
  //   this.captainService.getAllCrews().subscribe(data => {
  //     this.crews = data; // Update the crews array with the data from the API
  //   });
  // }

  // Method to add a new captain using the CaptainService
  addCaptain(): void {
    this.captainService.createCaptain(this.newCaptain).subscribe(captain => {
      this.captains.push(captain); // Add the newly created captain to the captains array
    });
  }

  // Method to delete a captain by ID using the CaptainService
  deleteCaptain(id: number): void {
    this.captainService.deleteCaptain(id).subscribe(() => {
      // Remove the deleted captain from the captains array
      this.captains = this.captains.filter(captain => captain.captainId !== id);
    });
  }
}
