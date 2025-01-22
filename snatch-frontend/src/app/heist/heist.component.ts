import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Heist } from '../models/Heist';
import { HeistService } from '../services/heist.service';

@Component({
  selector: 'app-heist',
  imports: [CommonModule, FormsModule],
  templateUrl: './heist.component.html',
  styleUrl: './heist.component.css'
})
export class HeistComponent implements OnInit {

  heists: Heist[] = [];

  newHeist: Heist = new Heist(0, '', '', false, '', 0);

  constructor(private heistService: HeistService) { }

  ngOnInit(): void {
    this.loadHeists();
  }

  loadHeists(): void {
    this.heistService.getAllHeists().subscribe(heists => {
      this.heists = heists;
    });
  }

  addHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe(heist => {
      this.heists.push(heist);
    });
  }

  updateHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe(heist => {
      this.heists.push(heist);
      this.newHeist = new Heist(0, '', '', false, '', 0);
    });
  }

  editHeist(heist: Heist): void {
    this.newHeist = heist;
  }

  deleteHeist(id: number): void {
    this.heistService.deleteHeist(id).subscribe(() => {
      this.heists = this.heists.filter(heist => heist.heistId !== id);
    });
  }
}
