import {Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {UserDashboardBaseComponent} from "./user-dashboard-base/user-dashboard-base.component";
import {AuthGuard} from "./auth.guard";
import {CompanyUserDetailsComponent} from "./company-user-details/company-user-details.component";

export const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'user-dashboard', component: UserDashboardBaseComponent, canActivate: [AuthGuard]},
  {path: 'company-details/:stockCompanyExternalId', component: CompanyUserDetailsComponent, canActivate: [AuthGuard]}
];
