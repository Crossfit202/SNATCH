import { Routes } from '@angular/router';
import { CaptainComponent } from './captain/captain.component';
import { LeaderComponent } from './leader/leader.component';
import { CrewComponent } from './crew/crew.component';
import { LootComponent } from './loot/loot.component';
import { HeistComponent } from './heist/heist.component';
import { PersonnelComponent } from './personnel/personnel.component';
import { SkillComponent } from './skill/skill.component';
import { HomePageComponent } from './home-page/home-page.component';

/**
 * Defines the application's routing configuration.
 * Each route maps a URL path to a specific component.
 */
export const routes: Routes = [

    // Default Route: Redirects to the home page when no path is specified
    {
        path: '',
        component: HomePageComponent,
        pathMatch: 'full' // Ensures that '' only matches the root path
    },

    // Route for viewing and managing leaders
    {
        path: 'leaders',
        component: LeaderComponent
    },

    // Route for viewing and managing captains
    {
        path: 'captains',
        component: CaptainComponent
    },

    // Route for viewing and managing crews
    {
        path: 'crews',
        component: CrewComponent
    },

    // Route for viewing and managing personnel
    {
        path: 'personnels',
        component: PersonnelComponent
    },

    // Route for viewing and managing heists
    {
        path: 'heists',
        component: HeistComponent
    },

    // Route for viewing and managing skills
    {
        path: 'skills',
        component: SkillComponent
    },

    // Route for viewing and managing loot
    {
        path: 'loot',
        component: LootComponent
    },

];
