import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {
  CompaniesSummaryResponse,
  CompanyDetailSummaryResponse,
  InvestGoalModel,
  UserCompanyNotesResponse
} from "./responses";
import {NoteCompanyComment} from "./requests";
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
  }

  getCompanyDetailSummary(stockCompanyExternalId: string): Observable<CompanyDetailSummaryResponse> {
    return this.http.get<CompanyDetailSummaryResponse>(
      `${this.apiUrl}/company-detail-summary?stockCompanyExternalId=${stockCompanyExternalId}`)
  }

  getUserCompanyNotesResponse(stockCompanyExternalId: string): Observable<UserCompanyNotesResponse> {
    return this.http.get<UserCompanyNotesResponse>(
      `${this.apiUrl}/user-account/company-notes?stockCompanyExternalId=${stockCompanyExternalId}`)
  }

  postCompanyComment(newCompanyCommentRequest: NoteCompanyComment): Observable<any> {
    return this.http.post(`${this.apiUrl}/user-account/company-comment`, newCompanyCommentRequest)
  }

  postInvestGoal(investGoalMode: InvestGoalModel): Observable<any>{
    return this.http.post(`${this.apiUrl}/user-account/company-invest-goal`, investGoalMode)
  }

}
