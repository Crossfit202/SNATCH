import { Routes } from '@angular/router';
import { CaptainComponent } from './captain/captain.component';
import { LeaderComponent } from './leader/leader.component';
import { CrewComponent } from './crew/crew.component';
import { LootComponent } from './loot/loot.component';
import { HeistComponent } from './heist/heist.component';
import { Personnel } from './models/Personnel';
import { SkillComponent } from './skill/skill.component';
import { PersonnelComponent } from './personnel/personnel.component';
import { HomePageComponent } from './home-page/home-page.component';

// Define and export the application's routing configuration
export const routes: Routes = [
    {
        path: '', component: HomePageComponent, pathMatch: 'full'
    },
    {
        path: 'leaders',
        component: LeaderComponent
    },
    {
        path: 'captains',
        component: CaptainComponent
    },
    {
        path: 'crews',
        component: CrewComponent
    },
    {
        path: 'personnels',
        component: PersonnelComponent
    },
    {
        path: 'heists',
        component: HeistComponent
    },

    {
        path: 'skills',
        component: SkillComponent
    },
    {
        path: 'loot',
        component: LootComponent
    },

];
