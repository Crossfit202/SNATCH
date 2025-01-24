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

  constructor(private leaderService: LeaderService) { }

  ngOnInit(): void {
    // Load the list of captains from the backend
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
}
