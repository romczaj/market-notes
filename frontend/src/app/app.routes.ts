import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {UserDashboardBaseComponent} from "./user-dashboard-base/user-dashboard-base.component";
import {AuthGuard} from "./auth.guard";
import {CompanyUserDetailsComponent} from "./company-user-details/company-user-details.component";
import {AdminDashboardComponent} from "./admin-dashboard/admin-dashboard.component";

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'user-dashboard',
    component: UserDashboardBaseComponent,
    canActivate: [AuthGuard],
    data: {roles: ['user']}
  },
  {
    path: 'company-details/:stockCompanyExternalId',
    component: CompanyUserDetailsComponent,
    canActivate: [AuthGuard],
    data: {roles: ['user']}
  },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard],
    data: {roles: ['admin']}
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  }
];
