import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaderService } from '../services/leader.service';
import { Leader } from '../models/Leader';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-leader',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './leader.component.html',
  styleUrl: './leader.component.css'
})
export class LeaderComponent implements OnInit {

  leaders: Leader[] = [];
  newLeader: Leader = new Leader(0, '');
  editingLeaderId: number | null = null; // Tracks which leader is being edited
  selectedLeader: Leader | null = null;


  constructor(private leaderService: LeaderService) { }

  ngOnInit(): void {
    this.getLeaders();
  }

  getLeaders(): void {
    this.leaderService.getLeaders().subscribe(data => {
      this.leaders = data;
    });
  }

  addLeader(): void {
    this.leaderService.addLeader(this.newLeader).subscribe(data => {
      this.leaders.push(data);
      this.newLeader = new Leader(0, '');
    });
  }

  deleteLeader(id: number): void {
    this.leaderService.deleteLeader(id).subscribe(() => {
      this.leaders = this.leaders.filter(leader => leader.leaderId !== id);
    });
  }

  // Toggle edit form for a leader
  toggleEdit(leaderId: number): void {
    const leader = this.leaders.find(l => l.leaderId === leaderId);
    if (leader) {
      this.editingLeaderId = leaderId;
      this.selectedLeader = { ...leader }; // Ensures `selectedLeader` is always a valid object
    }
  }

  cancelEdit(): void {
    this.editingLeaderId = null;
    this.selectedLeader = null;
  }

  // Update leader details
  updateLeader(): void {
    if (this.selectedLeader) {
      this.leaderService.updateLeader(this.selectedLeader).subscribe(updatedLeader => {
        // Update the leader in the list
        const index = this.leaders.findIndex(l => l.leaderId === updatedLeader.leaderId);
        if (index !== -1) {
          this.leaders[index] = updatedLeader;
        }
        this.cancelEdit();
      });
    }
  }
}
