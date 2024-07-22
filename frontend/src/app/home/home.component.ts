import {Component, Injectable} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {CompaniesTableComponent} from "../companies-table/companies-table.component";

@Component({
  selector: 'app-user-menu',
  standalone: true,
  imports: [
    CompaniesTableComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
@Injectable({
  providedIn: 'root'
})
export class HomeComponent {

  constructor(private keycloakService: KeycloakService) {
  }

  logIn(): void {
    console.log(`on click logIn()`)
    const keycloakLoginOptions = {
      redirectUri: 'http://localhost:4200/user-dashboard'
    }
    this.keycloakService.login(keycloakLoginOptions).then()
  }

}
