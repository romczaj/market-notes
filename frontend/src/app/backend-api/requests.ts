export interface NoteCompanyComment {
  stockCompanyExternalId: string;
  noteContent: string;
}

export interface LoadCompaniesModel {
  companies: CompanyRequestModel[];
}

export interface CompanyRequestModel {
  companyName: string;
  stockSymbol: string;
  stockMarketSymbol: string;
  dataProviderSymbol: string;
}
