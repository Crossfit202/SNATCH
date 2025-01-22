import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Leader } from '../models/Leader';
import { Crew } from '../models/Crew';
import { LeaderService } from '../services/leader.service';
import { CrewService } from '../services/crew.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-crew',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crew.component.html',
  styleUrl: './crew.component.css'
})
export class CrewComponent implements OnInit {

  crews: Crew[] = [];

  newCrew: Crew = new Crew(0, '');

  constructor(private crewService: CrewService) { }

  ngOnInit(): void {

    this.loadCrews();

  }

  loadCrews(): void {

    this.crewService.getAllCrews().subscribe(data => {
      this.crews = data;
    });
  }


  addCrew(): void {
    this.crewService.addCrew(this.newCrew).subscribe(crew => {
      this.crews.push(crew);
    });
  }

  updateCrew(): void {
    this.crewService.updateCrew(this.newCrew).subscribe(updatedCrew => {
      // Update the local crews array with the updated crew object
      this.crews = this.crews.map(c => c.crewId === updatedCrew.crewId ? updatedCrew : c);

      // Optionally reset the `newCrew` object after update
      this.newCrew = new Crew();
    });
  }

  editCrew(crew: Crew): void {
    this.newCrew = { ...crew };
  }



  deleteCrew(id: number): void {
    this.crewService.deleteCrew(id).subscribe(() => {
      this.crews = this.crews.filter(crew => crew.crewId !== id);
    });
  }

}
