import { Component, OnInit } from '@angular/core';
import { Personnel } from '../models/Personnel';
import { Skill } from '../models/Skill';
import { PersonnelService } from '../services/personnel.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../reused-components/navbar.component';
import { SkillService } from '../services/skill.service'; // Service for retrieving skills

@Component({
  selector: 'app-personnel',
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './personnel.component.html',
  styleUrl: './personnel.component.css'
})
export class PersonnelComponent implements OnInit {

  personnels: Personnel[] = [];
  skills: Skill[] = []; // Available skills for dropdown
  newPersonnel: Personnel = new Personnel(0, '', '', '', false, null, []);
  editingPersonnelId: number | null = null;
  selectedPersonnel: Personnel | null = null;

  constructor(private personnelService: PersonnelService, private skillService: SkillService) { }

  ngOnInit(): void {
    this.loadPersonnels();
    this.loadSkills();
  }

  loadPersonnels(): void {
    this.personnelService.getAllPersonnels().subscribe((personnels: Personnel[]) => {
      this.personnels = personnels.map(personnel => ({
        ...personnel,
        isAssigned: personnel.isAssigned
      }));
    });
  }

  loadSkills(): void {
    this.skillService.getAllSkills().subscribe((skills: Skill[]) => {
      this.skills = skills;
    });
  }

  addPersonnel(): void {
    const personnelPayload = {
      personnelId: 0,
      personnelName: this.newPersonnel.personnelName,
      species: this.newPersonnel.species,
      profileImg: this.newPersonnel.profileImg || 'default.png',
      isAssigned: false,
      crew: null,
      skills: this.newPersonnel.skills.map(skill => ({
        skillId: skill.skillId,
        skillName: skill.skillName,
        personnels: [] // Ensure it matches the expected type
      }))
    };

    this.personnelService.addPersonnel(personnelPayload).subscribe((personnel: Personnel) => {
      this.personnels.push(personnel);
      this.newPersonnel = new Personnel(0, '', '', '', false, null, []);
    });
  }




  updatePersonnel(): void {
    if (!this.selectedPersonnel) return;

    const personnelPayload = {
      personnelId: this.selectedPersonnel.personnelId,
      personnelName: this.selectedPersonnel.personnelName,
      species: this.selectedPersonnel.species,
      profileImg: this.selectedPersonnel.profileImg,
      isAssigned: this.selectedPersonnel.isAssigned,
      crew: null,
      skills: this.selectedPersonnel.skills.map(skill => ({
        skillId: skill.skillId,
        skillName: skill.skillName,
        personnels: [] // Ensure it matches the expected type
      }))
    };

    this.personnelService.updatePersonnel(this.selectedPersonnel.personnelId, personnelPayload).subscribe(() => {
      this.loadPersonnels();
      this.cancelEdit();
    });
  }



  updateSelectedSkills(event: Event, personnel: Personnel): void {
    const selectedOptions = (event.target as HTMLSelectElement).selectedOptions;
    const selectedSkillIds = Array.from(selectedOptions).map(option => Number(option.value));

    // Map selected skill IDs to full Skill objects, including the required personnels field
    personnel.skills = this.skills
      .filter(skill => selectedSkillIds.includes(skill.skillId))
      .map(skill => ({
        ...skill,
        personnels: [] // Ensure correct data structure
      }));
  }


  cancelEdit(): void {
    this.editingPersonnelId = null;
    this.selectedPersonnel = null;
  }

  toggleEdit(personnelId: number): void {
    this.editingPersonnelId = this.editingPersonnelId === personnelId ? null : personnelId;
    this.selectedPersonnel = this.personnels.find(p => p.personnelId === personnelId) || null;
  }


  deletePersonnel(id: number): void {
    this.personnelService.deletePersonnel(id).subscribe(() => {
      this.loadPersonnels();
    });
  }
}
