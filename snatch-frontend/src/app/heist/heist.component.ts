import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Heist } from '../models/Heist';
import { HeistService } from '../services/heist.service';
import { DomElementSchemaRegistry } from '@angular/compiler';


@Component({
  selector: 'app-heist',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './heist.component.html',
  styleUrls: ['./heist.component.css']
})
export class HeistComponent implements OnInit {

  heists: Heist[] = [];
  newHeist: Heist = new Heist(0, '', '', '', 0, false);



  constructor(private heistService: HeistService) { }

  ngOnInit(): void {

    this.loadHeists();


  }

  loadHeists(): void {

    this.heistService.getAllHeist().subscribe(data => {
      this.heists = data;
    });
  }

  addHeist(): void {
    this.heistService.addHeist(this.newHeist).subscribe(heist => {
      this.heists.push(heist);
    });
  }




  deleteHeist(id: number): void {
    this.heistService.deleteHeist(id).subscribe(() => {
      this.heists = this.heists.filter(heist => heist.heistId !== id);
    });
  }

}
