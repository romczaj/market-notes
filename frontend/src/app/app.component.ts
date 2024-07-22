import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {CompaniesTableComponent} from "./companies-table/companies-table.component";
import {CustomDatetimePipe} from "./custom-datetime-pipe";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CompaniesTableComponent, CustomDatetimePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'market-notes-frontend';
}
