import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LeaderService } from '../services/leader.service';
import { Leader } from '../models/Leader';

@Component({
  selector: 'app-leader',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leader.component.html',
  styleUrl: './leader.component.css'
})
export class LeaderComponent implements OnInit {

  leaders: Leader[] = [];
  newLeader: Leader = new Leader(0, '');

  constructor(private leaderService: LeaderService) { }

  ngOnInit(): void {
    // Load the list of captains from the backend
    this.loadLeaders();
  }

  loadLeaders(): void {
    this.leaderService.getAllLeaders().subscribe(data => {
      this.leaders = data;
    });
  }
}
