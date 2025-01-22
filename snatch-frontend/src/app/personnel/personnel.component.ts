import { Component, OnInit } from '@angular/core';
import { Personnel } from '../models/Personnel';
import { PersonnelService } from '../services/personnel.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-personnel',
  imports: [CommonModule, FormsModule],
  templateUrl: './personnel.component.html',
  styleUrl: './personnel.component.css'
})
export class PersonnelComponent implements OnInit {
  ngOnInit(): void {

    this.loadPersonnels();
  }

  personnels: Personnel[] = [];

  newPersonnel: Personnel = new Personnel(0, '');


  constructor(private personnelService: PersonnelService) { }

  loadPersonnels(): void {
    this.personnelService.getAllPersonnels().subscribe((personnels: Personnel[]) => {
      this.personnels = personnels;
    });
  }

  addPersonnel(): void {
    this.personnelService.addPersonnel(this.newPersonnel).subscribe((personnel: Personnel) => {
      this.personnels.push(personnel);
      this.newPersonnel = new Personnel(0, '');
    });
  }

  deletePersonnel(id: number): void {
    this.personnelService.deletePersonnel(id).subscribe(() => {
      this.loadPersonnels();
    });
  }


}
