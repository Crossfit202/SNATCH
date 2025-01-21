import { Routes } from '@angular/router';
import { CaptainComponent } from './captain/captain.component';
import { LeaderComponent } from './leader/leader.component';

// Define and export the application's routing configuration
export const routes: Routes = [
    // Route configuration for the 'captains' path
    {
        path: 'captains', // The URL segment that maps to this route
        component: CaptainComponent // The component to render when this route is accessed
    },
    // Route configuration for the 'leaders' path
    {
        path: 'leaders', // The URL segment that maps to this route
        component: LeaderComponent // The component to render when this route is accessed
    }
];
