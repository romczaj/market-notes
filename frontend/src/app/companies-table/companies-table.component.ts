import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {BackendApiService} from "../backend-api/backend-api.service";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {CompanySummaryResponse, emptyCompanySummaryResponse} from "../backend-api/responses";
import {getColorForValue} from "../util/color-range";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {Router} from "@angular/router";

@Component({
  selector: 'app-companies-table',
  standalone: true,
  imports: [
    MatSlideToggle, MatTableModule, MatSort, MatSortHeader
  ],
  templateUrl: './companies-table.component.html',
  styleUrl: './companies-table.component.css'
})

export class CompaniesTableComponent implements OnInit, AfterViewInit {
  companiesSourceMat = new MatTableDataSource(emptyCompanySummaryResponse);
  displayedColumns: string[] = ['companyName', 'actualPrice', 'currency', 'dailyIncrease',
    'weekIncrease', 'twoWeekIncrease', 'monthIncrease', 'threeMonthsIncrease', 'yearIncrease']

  @ViewChild(MatSort) sort: MatSort = new MatSort()
  private backendApiService = inject(BackendApiService)
  private router: Router = inject(Router)

  ngAfterViewInit() {
    this.companiesSourceMat.sort = this.sort;
  }

  ngOnInit(): void {
    this.backendApiService.getCompaniesSummary().subscribe(
      r => {
        this.companiesSourceMat.data = r.companies;
      }
    )
  }

  protected readonly getColorForValue = getColorForValue;

  cellClicked(element: CompanySummaryResponse) {
    this.router.navigate([`company-details/${element.stockCompanyExternalId}`])
  }
}

