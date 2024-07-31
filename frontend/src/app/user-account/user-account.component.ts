import {Component, inject, OnInit} from '@angular/core';
import {BackendApiService} from "../backend-api/backend-api.service";

@Component({
  selector: 'app-user-account',
  standalone: true,
  imports: [],
  templateUrl: './user-account.component.html',
  styleUrl: './user-account.component.css'
})
export class UserAccountComponent implements OnInit{

  ngOnInit(): void {
    this.apiBackendService.getCompanyDetailSummary('PXM_WSE')
      .subscribe(
        {
          next: value => this.comp = value.basicSummary.dataProviderSymbol
        }
      )
  }
  private apiBackendService = inject(BackendApiService);

  comp: string | undefined;

}
