import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaderService } from '../services/leader.service';
import { Leader } from '../models/Leader';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-leader', // Selector used for this component
  standalone: true, // Allows this component to be used without being declared in a module
  imports: [CommonModule, FormsModule, NavbarComponent], // Modules required for this component
  templateUrl: './leader.component.html', // HTML template file
  styleUrl: './leader.component.css' // CSS styling file
})
export class LeaderComponent implements OnInit {

  leaders: Leader[] = []; // Stores the list of leaders
  newLeader: Leader = new Leader(0, ''); // Default object for creating a new leader
  editingLeaderId: number | null = null; // Tracks which leader is currently being edited
  selectedLeader: Leader | null = null; // Holds the leader data for editing

  constructor(private leaderService: LeaderService) { }

  // Lifecycle hook that runs when the component initializes
  ngOnInit(): void {
    this.getLeaders(); // Load all leaders from the backend
  }

  /** Fetch all leaders from the backend */
  getLeaders(): void {
    this.leaderService.getLeaders().subscribe(data => {
      this.leaders = data; // Store the retrieved leaders in the `leaders` array
    });
  }

  /** Add a new leader */
  addLeader(): void {
    this.leaderService.addLeader(this.newLeader).subscribe(data => {
      this.leaders.push(data); // Append the new leader to the `leaders` list
      this.newLeader = new Leader(0, ''); // Reset the newLeader object after adding
    });
  }

  /** Delete a leader */
  deleteLeader(id: number): void {
    this.leaderService.deleteLeader(id).subscribe(() => {
      this.leaders = this.leaders.filter(leader => leader.leaderId !== id);
      // Removes the deleted leader from the list
    });
  }

  /** Toggle edit mode for a specific leader */
  toggleEdit(leaderId: number): void {
    const leader = this.leaders.find(l => l.leaderId === leaderId);
    if (leader) {
      this.editingLeaderId = leaderId; // Set the ID of the leader being edited
      this.selectedLeader = { ...leader }; // Create a copy of the leader to avoid modifying the original
    }
  }

  /** Cancel the edit mode */
  cancelEdit(): void {
    this.editingLeaderId = null; // Reset the editing leader ID
    this.selectedLeader = null; // Clear the selected leader
  }

  /** Update leader details */
  updateLeader(): void {
    if (this.selectedLeader) {
      this.leaderService.updateLeader(this.selectedLeader).subscribe(updatedLeader => {
        // Find the leader in the list and update its details
        const index = this.leaders.findIndex(l => l.leaderId === updatedLeader.leaderId);
        if (index !== -1) {
          this.leaders[index] = updatedLeader;
        }
        this.cancelEdit(); // Exit edit mode after saving changes
      });
    }
  }
}
