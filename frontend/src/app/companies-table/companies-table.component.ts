import {AfterViewInit, Component, inject, OnInit, ViewChild} from '@angular/core';
import {MatSlideToggle} from "@angular/material/slide-toggle";
import {BackendApiService} from "../backend-api/backend-api.service";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {CompanySummaryResponse, Country, emptyCompanySummaryResponse} from "../backend-api/responses";
import {getColorForValue} from "../util/color-range";
import {MatSort, MatSortHeader} from "@angular/material/sort";
import {Router} from "@angular/router";
import {MatButton} from "@angular/material/button";
import {DecimalPipe} from "@angular/common";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatOption, MatSelect, MatSelectChange} from "@angular/material/select";

@Component({
  selector: 'app-companies-table',
  standalone: true,
  imports: [
    MatSlideToggle, MatTableModule, MatSort, MatSortHeader, MatButton, DecimalPipe, MatFormField, MatInput, MatLabel, FormsModule, MatSelect, MatOption
  ],
  templateUrl: './companies-table.component.html',
  styleUrl: './companies-table.component.css'
})

export class CompaniesTableComponent implements OnInit, AfterViewInit {
  companiesSourceMat = new MatTableDataSource(emptyCompanySummaryResponse);
  displayedColumns: string[] = ['companyName', 'operations', 'actualPrice', 'currency', 'dailyIncrease',
    'weekIncrease', 'twoWeekIncrease', 'monthIncrease', 'threeMonthsIncrease', 'yearIncrease']

  protected readonly Country = Country;

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
    this.companiesSourceMat.filterPredicate = function (record, filter) {
      const filterCompanyObject: FilterCompanyObject = JSON.parse(filter);

      const companyNameResult = filterCompanyObject.companyName === null ? true
        : record.companyName.toLocaleLowerCase().includes(filterCompanyObject.companyName)

      const companyCountryResult = filterCompanyObject.country === null ? true
        : record.country === filterCompanyObject.country

      return companyNameResult && companyCountryResult
    }

    const filterCompanyObject: FilterCompanyObject = {companyName: null, country: null}
    this.companiesSourceMat.filter = JSON.stringify(filterCompanyObject)
  }

  protected readonly getColorForValue = getColorForValue;

  manageCompanyClicked(element: CompanySummaryResponse) {
    const newRelativeUrl = this.router.createUrlTree([`company-details/${element.stockCompanyExternalId}`]);
    const baseUrl = window.location.href.replace(this.router.url, '');
    window.open(baseUrl + newRelativeUrl, '_blank');
  }

  companyNameKeyUpEvent(event: KeyboardEvent) {
    const filterCompanyObject: FilterCompanyObject = JSON.parse(this.companiesSourceMat.filter);
    filterCompanyObject.companyName = (event?.target as HTMLInputElement).value
    this.companiesSourceMat.filter = JSON.stringify(filterCompanyObject)
  }

  companyCountryChangeEvent(event: MatSelectChange) {
    const filterCompanyObject: FilterCompanyObject = JSON.parse(this.companiesSourceMat.filter);
    filterCompanyObject.country = event.value === undefined ? null : event.value
    this.companiesSourceMat.filter = JSON.stringify(filterCompanyObject)
  }
}

interface FilterCompanyObject {
  companyName: string | null
  country: Country | null
}

