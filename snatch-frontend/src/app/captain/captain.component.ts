import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Leader } from '../models/Leader';
import { Crew } from '../models/Crew';
import { LeaderService } from '../services/leader.service';
import { CrewService } from '../services/crew.service';
import { NavbarComponent } from '../reused-components/navbar.component';

@Component({
  selector: 'app-captain',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './captain.component.html',
  styleUrls: ['./captain.component.css']
})
export class CaptainComponent implements OnInit {
  captains: Captain[] = [];
  newCaptain: Captain = new Captain(0, '', new Leader(1, 'Default Leader'), null); // ✅ Default leader
  leaders: Leader[] = [];
  crews: Crew[] = [];

  editingCaptainId: number | null = null;  // Tracks which captain is being edited
  selectedCaptain: Captain | null = null;  // Holds data of the selected captain

  constructor(
    private captainService: CaptainService,
    private leaderService: LeaderService,
    private crewService: CrewService
  ) { }

  ngOnInit(): void {
    this.loadCaptains();
    this.loadLeaders();
    this.loadCrews();
  }

  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe(data => {
      this.captains = data.map(captain => ({
        ...captain,
        leader: captain.leader
          ? new Leader(captain.leader.leaderId, captain.leader.leaderName || 'Unknown')
          : new Leader(1, 'Unknown'), // ✅ Ensure leader always exists
        crew: captain.crew
          ? new Crew(captain.crew.crewId, captain.crew.crewName || 'Unknown')
          : null
      }));
    });
  }

  loadLeaders(): void {
    this.leaderService.getLeaders().subscribe(data => {
      this.leaders = data;
    });
  }

  loadCrews(): void {
    this.crewService.getAllCrews().subscribe(data => {
      this.crews = data;
    });
  }

  addCaptain(): void {
    if (!this.newCaptain.leader) {
      alert("A leader is required to create a captain.");
      return;
    }

    const captainPayload = {
      captainId: 0, // ✅ Ensure captainId is included in the payload
      captainName: this.newCaptain.captainName,
      leader: {
        leaderId: this.newCaptain.leader.leaderId,
        leaderName: this.newCaptain.leader.leaderName
      }, // ✅ Ensure leader includes leaderName

      crew: this.newCaptain.crew
        ? {
          crewId: this.newCaptain.crew.crewId,
          crewName: this.newCaptain.crew.crewName,
          maxCapacity: this.newCaptain.crew.maxCapacity ?? 0,  // ✅ Required
          availability: this.newCaptain.crew.availability ?? true,  // ✅ Required
          hasCaptain: this.newCaptain.crew.hasCaptain ?? false,  // ✅ Required
          captain: null,  // ✅ Avoid circular reference
          personnels: []  // ✅ Empty array
        }
        : null // ✅ Allow null value
    };

    this.captainService.createCaptain(captainPayload).subscribe((createdCaptain) => {
      this.captains.push(
        new Captain(
          createdCaptain.captainId,
          createdCaptain.captainName,
          new Leader(createdCaptain.leader.leaderId, createdCaptain.leader.leaderName),
          createdCaptain.crew
            ? new Crew(
              createdCaptain.crew.crewId,
              createdCaptain.crew.crewName,
              createdCaptain.crew.maxCapacity,
              createdCaptain.crew.availability,
              createdCaptain.crew.hasCaptain,
              createdCaptain.crew.captain,  // 🔥 Ensure this is correctly typed
              createdCaptain.crew.personnels || []  // 🔥 Provide an empty array if undefined
            )
            : null
        )
      );


      this.newCaptain = new Captain(0, '', new Leader(1, 'Default Leader'), null);
    });
  }



  deleteCaptain(id: number): void {
    this.captainService.deleteCaptain(id).subscribe(() => {
      this.captains = this.captains.filter(c => c.captainId !== id);
    });
  }

  toggleEdit(captainId: number): void {
    if (this.editingCaptainId === captainId) {
      this.cancelEdit();
    } else {
      this.editingCaptainId = captainId;
      this.selectedCaptain = { ...this.captains.find(c => c.captainId === captainId)! };
    }
  }

  cancelEdit(): void {
    this.editingCaptainId = null;
    this.selectedCaptain = null;
  }

  updateCaptain(): void {
    if (this.selectedCaptain) {
      this.captainService.updateCaptain(this.selectedCaptain).subscribe(updatedCaptain => {
        const index = this.captains.findIndex(c => c.captainId === updatedCaptain.captainId);
        if (index !== -1) {
          this.captains[index] = updatedCaptain;
        }
        this.cancelEdit();
      });
    }
  }
}
