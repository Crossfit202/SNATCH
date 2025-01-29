import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CaptainService } from '../services/captain.service';
import { Captain } from '../models/Captain';
import { Crew } from '../models/Crew';
import { CrewService } from '../services/crew.service';
import { Personnel } from '../models/Personnel';
import { PersonnelService } from '../services/personnel.service';
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
  captains: Captain[] = [];
  personnels: Personnel[] = [];

  newCrew: Crew = new Crew();
  selectedPersonnelIds: number[] = []; // Store selected personnel IDs for the new crew
  editingCrewId: number | null = null;
  selectedCrew: Crew | null = null;

  constructor(
    private crewService: CrewService,
    private captainService: CaptainService,
    private personnelService: PersonnelService
  ) { }

  ngOnInit(): void {
    this.loadCrews();
    this.loadCaptains();
    this.loadPersonnels();
  }

  loadCrews(): void {
    this.crewService.getAllCrews().subscribe((data) => {
      this.crews = data.map(crew => ({
        ...crew,
        personnels: crew.personnels || [] // Ensure `personnels` is always an array
      }));
    });
  }

  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe((data) => {
      this.captains = data;
    });
  }

  loadPersonnels(): void {
    this.personnelService.getAllPersonnels().subscribe((data) => {
      this.personnels = data;
    });
  }

  /** ADD NEW CREW */
  addCrew(): void {
    const crewPayload: Crew = {
      crewId: 0, // Let backend assign ID
      crewName: this.newCrew.crewName,
      maxCapacity: this.newCrew.maxCapacity || 5,
      availability: true,
      hasCaptain: this.newCrew.captain !== null,
      captain: this.newCrew.captain,
      personnels: this.personnels.filter(p => this.selectedPersonnelIds.includes(p.personnelId)) // ✅ Correctly map personnel
    };

    this.crewService.addCrew(crewPayload).subscribe((crew) => {
      this.loadCrews(); // ✅ Ensure UI updates correctly
      this.newCrew = new Crew();
      this.selectedPersonnelIds = []; // Reset personnel selection
    });
  }



  /** HANDLE PERSONNEL SELECTION FOR NEW CREW */
  updateNewCrewPersonnel(event: Event): void {
    const selectedOptions = (event.target as HTMLSelectElement).selectedOptions;
    this.selectedPersonnelIds = Array.from(selectedOptions).map(option => Number(option.value));
  }

  /** DELETE CREW */
  /** DELETE CREW */
  deleteCrew(id: number): void {
    console.log(`Deleting crew with ID: ${id}`); // ✅ Debugging log
    this.crewService.deleteCrew(id).subscribe({
      next: () => {
        console.log(`Crew with ID: ${id} deleted successfully`); // ✅ Check if deletion succeeds
        this.crews = this.crews.filter((crew) => crew.crewId !== id);
      },
      error: (err) => console.error(`Error deleting crew:`, err) // ✅ Log any errors
    });
  }


  /** TOGGLE EDIT MODE */
  toggleEdit(crewId: number): void {
    this.editingCrewId = this.editingCrewId === crewId ? null : crewId;
    this.selectedCrew = this.crews.find((crew) => crew.crewId === crewId) || null;
  }

  /** UPDATE SELECTED PERSONNEL */
  updateSelectedPersonnel(event: Event): void {
    if (!this.selectedCrew) return;
    const selectedOptions = (event.target as HTMLSelectElement).selectedOptions;
    const selectedPersonnelIds = Array.from(selectedOptions).map(option => Number(option.value));
    this.selectedCrew.personnels = this.personnels.filter(personnel => selectedPersonnelIds.includes(personnel.personnelId));
  }

  /** CHECK IF PERSONNEL IS SELECTED */
  isPersonnelSelected(personnelId: number): boolean {
    return this.selectedCrew?.personnels.some(p => p.personnelId === personnelId) ?? false;
  }

  /** UPDATE CREW */
  updateCrew(): void {
    if (!this.selectedCrew) return;

    const crewPayload: Crew = {
      crewId: this.selectedCrew.crewId,
      crewName: this.selectedCrew.crewName,
      maxCapacity: this.selectedCrew.maxCapacity,
      availability: this.selectedCrew.availability,
      hasCaptain: this.selectedCrew.captain !== null,
      captain: this.selectedCrew.captain,
      personnels: this.selectedCrew.personnels || [] // Ensure `personnels` is an array
    };

    this.crewService.updateCrew(this.selectedCrew.crewId, crewPayload).subscribe(() => {
      this.loadCrews(); // Refresh crews after saving
      this.cancelEdit();
    });
  }

  cancelEdit(): void {
    this.editingCrewId = null;
    this.selectedCrew = null;
  }
}
