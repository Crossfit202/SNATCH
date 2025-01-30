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
  crews: Crew[] = []; // Stores all available crews
  captains: Captain[] = []; // Stores all available captains
  personnels: Personnel[] = []; // Stores all available personnel

  newCrew: Crew = new Crew(); // Stores new crew details for adding
  selectedPersonnelIds: number[] = []; // Stores selected personnel IDs for the new crew
  editingCrewId: number | null = null; // Tracks which crew is being edited
  selectedCrew: Crew | null = null; // Holds data of the crew being edited

  constructor(
    private crewService: CrewService,
    private captainService: CaptainService,
    private personnelService: PersonnelService
  ) { }

  ngOnInit(): void {
    this.loadCrews(); // Fetches the list of all crews
    this.loadCaptains(); // Fetches the list of all captains
    this.loadPersonnels(); // Fetches the list of all personnel
  }

  /**
   * Fetch all crews from the backend and store them in `crews`
   */
  loadCrews(): void {
    this.crewService.getAllCrews().subscribe((data) => {
      this.crews = data.map(crew => ({
        ...crew,
        personnels: crew.personnels || [] // Ensure `personnels` is always an array
      }));
    });
  }

  /**
   * Fetch all captains from the backend and store them in `captains`
   */
  loadCaptains(): void {
    this.captainService.getAllCaptains().subscribe((data) => {
      this.captains = data;
    });
  }

  /**
   * Fetch all personnel from the backend and store them in `personnels`
   */
  loadPersonnels(): void {
    this.personnelService.getAllPersonnels().subscribe((data) => {
      this.personnels = data;
    });
  }

  /** 
   * ADD NEW CREW
   * Creates a new crew object and sends it to the backend
   */
  addCrew(): void {
    const crewPayload: Crew = {
      crewId: 0, // Let backend assign an ID
      crewName: this.newCrew.crewName,
      maxCapacity: this.newCrew.maxCapacity || 5, // Default max capacity to 5
      availability: true, // Default availability to true
      hasCaptain: this.newCrew.captain !== null, // Determine if a captain is assigned
      captain: this.newCrew.captain,
      personnels: this.personnels.filter(p => this.selectedPersonnelIds.includes(p.personnelId)) // Correctly assign personnel
    };

    this.crewService.addCrew(crewPayload).subscribe((crew) => {
      this.loadCrews(); // Refresh crew list
      this.newCrew = new Crew(); // Reset new crew form
      this.selectedPersonnelIds = []; // Reset selected personnel
    });
  }

  /**
   * Handles personnel selection for adding a new crew
   */
  updateNewCrewPersonnel(event: Event): void {
    const selectedOptions = (event.target as HTMLSelectElement).selectedOptions;
    this.selectedPersonnelIds = Array.from(selectedOptions).map(option => Number(option.value));
  }

  /**
   * DELETE CREW
   * Deletes a crew by its ID and updates the UI
   */
  deleteCrew(id: number): void {
    console.log(`Deleting crew with ID: ${id}`); // Debugging log
    this.crewService.deleteCrew(id).subscribe({
      next: () => {
        console.log(`Crew with ID: ${id} deleted successfully`); // Confirm deletion
        this.crews = this.crews.filter((crew) => crew.crewId !== id); // Remove crew from UI
      },
      error: (err) => console.error(`Error deleting crew:`, err) // Log errors if any
    });
  }

  /**
   * TOGGLE EDIT MODE
   * Toggles edit mode for a specific crew
   */
  toggleEdit(crewId: number): void {
    this.editingCrewId = this.editingCrewId === crewId ? null : crewId;
    this.selectedCrew = this.crews.find((crew) => crew.crewId === crewId) || null;
  }

  /**
   * UPDATE SELECTED PERSONNEL
   * Updates the personnel assigned to the selected crew during editing
   */
  updateSelectedPersonnel(event: Event): void {
    if (!this.selectedCrew) return;
    const selectedOptions = (event.target as HTMLSelectElement).selectedOptions;
    const selectedPersonnelIds = Array.from(selectedOptions).map(option => Number(option.value));
    this.selectedCrew.personnels = this.personnels.filter(personnel => selectedPersonnelIds.includes(personnel.personnelId));
  }

  /**
   * CHECK IF PERSONNEL IS SELECTED
   * Used to determine if a personnel is already assigned to the crew
   */
  isPersonnelSelected(personnelId: number): boolean {
    return this.selectedCrew?.personnels.some(p => p.personnelId === personnelId) ?? false;
  }

  /**
   * UPDATE CREW
   * Sends updated crew details to the backend
   */
  updateCrew(): void {
    if (!this.selectedCrew) return;

    const crewPayload: Crew = {
      crewId: this.selectedCrew.crewId,
      crewName: this.selectedCrew.crewName,
      maxCapacity: this.selectedCrew.maxCapacity,
      availability: this.selectedCrew.availability,
      hasCaptain: this.selectedCrew.captain !== null, // Determine if a captain is assigned
      captain: this.selectedCrew.captain,
      personnels: this.selectedCrew.personnels || [] // Ensure `personnels` is an array
    };

    this.crewService.updateCrew(this.selectedCrew.crewId, crewPayload).subscribe(() => {
      this.loadCrews(); // Refresh crews after saving
      this.cancelEdit();
    });
  }

  /**
   * CANCEL EDIT
   * Resets editing mode and clears selected crew
   */
  cancelEdit(): void {
    this.editingCrewId = null;
    this.selectedCrew = null;
  }
}
