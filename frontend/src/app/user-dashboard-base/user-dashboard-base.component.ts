import {Component} from '@angular/core';
import {BackendApiService} from "../backend-api/backend-api.service";
import {KeycloakService} from "keycloak-angular";

@Component({
  selector: 'app-success-login',
  standalone: true,
  imports: [],
  templateUrl: './user-dashboard-base.component.html',
  styleUrl: './user-dashboard-base.component.css'
})
export class UserDashboardBaseComponent {
}
