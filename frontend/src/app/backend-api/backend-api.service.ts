import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {
  CompaniesSummaryResponse,
  CompanyDetailSummaryResponse,
  CompanySummaryResponse,
  IncreasePeriod,
  InvestGoalModel,
  UserCompanyNotesResponse
} from "./responses";
import {LoadCompaniesModel, NoteCompanyComment} from "./requests";
import {ConfigService} from "../configuration/config-service";

@Injectable({
  providedIn: 'root'
})
export class BackendApiService {

  constructor(private http: HttpClient,
              private configService: ConfigService) { }

  private apiUrl = this.configService.config.backendApiUrl;

  getCompaniesSummary(): Observable<CompaniesSummaryResponse> {
    return this.http.get<CompaniesSummaryResponse>(`${this.apiUrl}/companies-summary`, {})
      .pipe(
        map(response => {
          response.companies = response.companies.map(company => this.fillCompaniesSummaryResponse(company));
          return response;
        })
      );
  }

  getCompanyDetailSummary(stockCompanyExternalId: string): Observable<CompanyDetailSummaryResponse> {
    return this.http.get<CompanyDetailSummaryResponse>(
      `${this.apiUrl}/company-detail-summary?stockCompanyExternalId=${stockCompanyExternalId}`
    ).pipe(
      map(response => {
        response.basicSummary = this.fillCompaniesSummaryResponse(response.basicSummary);
        return response;
      })
    );
  }

  getUserCompanyNotesResponse(stockCompanyExternalId: string): Observable<UserCompanyNotesResponse> {
    return this.http.get<UserCompanyNotesResponse>(
      `${this.apiUrl}/user-account/company-notes?stockCompanyExternalId=${stockCompanyExternalId}`)
  }

  postCompanyComment(newCompanyCommentRequest: NoteCompanyComment): Observable<any> {
    return this.http.post(`${this.apiUrl}/user-account/company-comment`, newCompanyCommentRequest)
  }

  postInvestGoal(investGoalModel: InvestGoalModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/user-account/company-invest-goal`, investGoalModel)
  }

  postLoadCompanies(loadCompaniesModel: LoadCompaniesModel): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/load-companies`, loadCompaniesModel)
  }

  postRefreshCompaniesScheduler(): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/refresh-companies-scheduler/invoke`, {})
  }

  postUserReportScheduler(): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/user-report-scheduler/invoke`, {})
  }

  getExportCompanies(): Observable<LoadCompaniesModel> {
    return this.http.get<LoadCompaniesModel>(`${this.apiUrl}/admin/export-companies`)
  }

  private fillCompaniesSummaryResponse(company: CompanySummaryResponse): CompanySummaryResponse {
    company.increasePeriodResults.forEach(result => {
      switch (result.increasePeriod) {
        case IncreasePeriod.DAILY:
          company.dailyIncrease = result.increasePercent;
          break;
        case IncreasePeriod.WEEK:
          company.weekIncrease = result.increasePercent;
          break;
        case IncreasePeriod.TWO_WEEKS:
          company.twoWeekIncrease = result.increasePercent;
          break;
        case IncreasePeriod.MONTH:
          company.monthIncrease = result.increasePercent;
          break;
        case IncreasePeriod.THREE_MONTHS:
          company.threeMonthsIncrease = result.increasePercent;
          break;
        case IncreasePeriod.YEAR:
          company.yearIncrease = result.increasePercent;
          break;
        case IncreasePeriod.TWO_YEARS:
          company.twoYearsIncrease = result.increasePercent;
          break;
      }
    });
    return company;
  }

}
