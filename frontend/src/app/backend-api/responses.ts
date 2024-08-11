export interface CompaniesSummaryResponse {
  companies: CompanySummaryResponse[];
}

export const emptyCompanySummaryResponse: CompanySummaryResponse[] = []

export interface CompanySummaryResponse {
  id: number;
  stockCompanyExternalId: string;
  country: Country,
  dataProviderSymbol: string;
  companyName: string;
  calculationDate: string;
  actualPrice: number;
  currency: string;
  increasePeriodResults: IncreasePeriodResult[];
  dailyIncrease: number | undefined;
  weekIncrease: number | undefined;
  twoWeekIncrease: number | undefined;
  monthIncrease: number | undefined;
  threeMonthsIncrease: number | undefined;
  yearIncrease: number | undefined;
  twoYearsIncrease: number | undefined;
}

export enum Country{
  POLAND="POLAND",
  USA="USA"
}


export interface CalculationResult {
  todayPrice: number;
  yesterdayPrice: number;
  increasePeriodResults: IncreasePeriodResult[];
}

export interface CompanyDetailSummaryResponse {
  basicSummary: CompanySummaryResponse,
  calculationResult: CalculationResult
}

export interface IncreasePeriodResult {
  increasePeriod: IncreasePeriod;
  increasePercent: number;
  highestPeriodValue: number;
  lowestPeriodValue: number;
  scoreHighestPeak: boolean;
  scoreLowestPeak: boolean;
}

export enum IncreasePeriod {
  DAILY = "DAILY",
  WEEK = "WEEK",
  TWO_WEEKS = "TWO_WEEKS",
  MONTH = "MONTH",
  THREE_MONTHS = "THREE_MONTHS",
  YEAR = "YEAR",
  TWO_YEARS = "TWO_YEARS",
}

export interface Money {
  todayPrice: number;
  currency: string
}

export interface UserCompanyNotesResponse {
  userAccountExternalId: string;
  stockCompanyExternalId: string;
  investGoalResponse: InvestGoalModel;
  comments: CommentResponse[];
  buySellOperationResponses: BuySellOperationResponse[];
}

export interface InvestGoalModel {
  stockCompanyExternalId: string;
  buyStopPrice: number;
  sellStopPrice: number;
  buyLimitPrice: number;
  sellLimitPrice: number;
}

export interface CommentResponse {
  noteDate: Date;
  companyPrice: number;
  noteContent: string;
}

export interface BuySellOperationResponse {
  stockOperation: string;
  operationAmount: number;
  operationCurrency: string;
  operationDate: string;
}
