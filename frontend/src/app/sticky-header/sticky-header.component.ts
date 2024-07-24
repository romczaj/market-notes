import {Component, inject} from '@angular/core';
import {KeycloakService} from "keycloak-angular";
import {Router} from "@angular/router";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-sticky-header',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './sticky-header.component.html',
  styleUrl: './sticky-header.component.css'
})
export class StickyHeaderComponent {

  private keycloakService = inject(KeycloakService)
  private router = inject(Router)

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn()
  }


  loginOperation() {
    if (this.isLoggedIn()) {
      this.logOut()
    } else {
     this.logIn()
    }
  }

  private logIn(){
    this.keycloakService.login({
      redirectUri:  window.location.origin + this.router.url
    }).then()
  }

  private logOut(){
    this.keycloakService.logout( window.location.origin).then(() => {
      this.keycloakService.clearToken()
    });
  }

  goHome() {
    this.router.navigate(['/home'])
  }
}
