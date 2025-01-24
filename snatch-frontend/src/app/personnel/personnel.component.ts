import { Component, OnInit } from '@angular/core';
import { Personnel } from '../models/Personnel';
import { PersonnelService } from '../services/personnel.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-personnel',
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './personnel.component.html',
  styleUrl: './personnel.component.css'
})
export class PersonnelComponent implements OnInit {

  // Array to store personnel data fetched from the API
  personnels: Personnel[] = [];

  // Object to represent a new personnel being added
  newPersonnel: Personnel = new Personnel(0, '');

  // Service Injection
  constructor(private personnelService: PersonnelService) { }

  // Runs while the component is initialized
  ngOnInit(): void {
    this.loadPersonnels();
  }

  // GET ALL
  loadPersonnels(): void {
    this.personnelService.getAllPersonnels().subscribe((personnels: Personnel[]) => {
      this.personnels = personnels;
    });
  }

  // POST
  addPersonnel(): void {
    this.personnelService.addPersonnel(this.newPersonnel).subscribe((personnel: Personnel) => {
      this.personnels.push(personnel);
      this.newPersonnel = new Personnel(0, '');
    });
  }

  // DELETE
  deletePersonnel(id: number): void {
    this.personnelService.deletePersonnel(id).subscribe(() => {
      this.loadPersonnels();
    });
  }
}
