import {Component, inject} from '@angular/core';
import {BackendApiService} from "../backend-api/backend-api.service";
import {LoadCompaniesModel} from "../backend-api/requests";
import {ToastrService} from "ngx-toastr";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  private backendApiService = inject(BackendApiService);
  private toastrService = inject(ToastrService)

  selectedFile: File | null = null;
  loadCompaniesModel = {} as LoadCompaniesModel;

  async onFileSelected(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    if (input.files?.length) {
      this.selectedFile = input.files[0];
      const textModel = await this.selectedFile.text();
      this.loadCompaniesModel = await JSON.parse(textModel)
    }
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.backendApiService.postLoadCompanies(this.loadCompaniesModel)
        .subscribe({
          next: () => this.toastrService.success('File uploaded successfully'),
          error: () => this.toastrService.error('File upload error')
        })
    } else {
      this.toastrService.error('Not selected file json')
    }
  }

  refreshCompaniesScheduler() {
    this.backendApiService.postRefreshCompaniesScheduler().subscribe({
      next: () => this.toastrService.success('Companies refreshed successfully'),
    })
  }

  postUserReportScheduler() {
    this.backendApiService.postUserReportScheduler().subscribe({
      next: () => this.toastrService.success('User report scheduler started'),
    })
  }
}
