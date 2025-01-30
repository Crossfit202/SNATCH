import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Heist } from '../models/Heist';
import { HeistService } from '../services/heist.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-heist',
  templateUrl: './heist.component.html',
  styleUrls: ['./heist.component.css'],
  standalone: true, // Ensure this is only here if it's a standalone component
  imports: [CommonModule, FormsModule, NavbarComponent],
})
export class HeistComponent implements OnInit {
  heists: Heist[] = [];
  newHeist: Heist = new Heist(0, '', '', '', 0, false);
  editingHeistId: number | null = null;
  selectedHeist: Heist | null = null;

  constructor(private heistService: HeistService) { }

  ngOnInit(): void {
    this.loadHeists();
  }

  loadHeists(): void {
    this.heistService.getAllHeist().subscribe((data) => {
      this.heists = data;
    });
  }

  addHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe((heist) => {
      this.heists.push(heist);
      this.newHeist = new Heist(0, '', '', '', 0, false);
    });
  }

  deleteHeist(id: number): void {
    this.heistService.deleteHeist(id).subscribe(() => {
      this.heists = this.heists.filter((heist) => heist.heistId !== id);
    });
  }

  toggleEdit(heist: Heist): void {
    this.editingHeistId = this.editingHeistId === heist.heistId ? null : heist.heistId;
    this.selectedHeist = { ...heist };
  }

  updateHeist(): void {
    if (!this.selectedHeist) return;

    this.heistService.updateHeist(this.selectedHeist.heistId, this.selectedHeist).subscribe(() => {
      this.loadHeists();
      this.editingHeistId = null;
      this.selectedHeist = null;
    });
  }

  cancelEdit(): void {
    this.editingHeistId = null;
    this.selectedHeist = null;
  }
}
