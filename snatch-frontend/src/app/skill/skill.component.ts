import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Skill } from '../models/Skill';
import { SkillService } from '../services/skill.service';

@Component({
  selector: 'app-skill',
  imports: [CommonModule, FormsModule],
  templateUrl: './skill.component.html',
  styleUrl: './skill.component.css'
})
export class SkillComponent implements OnInit {
  ngOnInit(): void {
    this.loadSkills();
  }

  skills: Skill[] = [];

  constructor(private skillService: SkillService) { }

  loadSkills(): void {
    this.skillService.getAllSkills().subscribe(skills => {
      this.skills = skills;
    });
  }

  deleteSkills(id: number): void {
    this.skillService.deleteSkill(id).subscribe(() => {
      this.loadSkills();
    });
  }
}
