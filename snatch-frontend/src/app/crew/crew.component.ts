import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Leader } from '../models/Leader';
import { Crew } from '../models/Crew';
import { CrewService } from '../services/crew.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-crew',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './crew.component.html',
  styleUrl: './crew.component.css'
})
export class CrewComponent implements OnInit {

  crews: Crew[] = [];

  newCrew: Crew = new Crew(0, '');

  captains: Captain[] = [];

  constructor(private crewService: CrewService, private captainService: CaptainService) { }

  ngOnInit(): void {

    this.loadCrews();
    this.loadCaptains();

  }

  loadCrews(): void {

    this.crewService.getAllCrews().subscribe(data => {
      this.crews = data;
    });
  }

  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe(data => {
      this.captains = data; // Assign API response to captains array
      console.log('Loaded Captains:', this.captains); // Debugging: Log captains
    });
  }



  addCrew(): void {
    this.crewService.addCrew(this.newCrew).subscribe(crew => {
      this.crews.push(crew);
    });
  }


  deleteCrew(id: number): void {
    this.crewService.deleteCrew(id).subscribe(() => {
      this.crews = this.crews.filter(crew => crew.crewId !== id);
    });
  }

}
