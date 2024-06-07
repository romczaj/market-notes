package pl.romczaj.marketnotes.domain;

import java.util.List;
import java.util.Optional;


public interface StockCompanyRepository {
    StockCompany saveSummary(StockCompany stockCompany);

    Optional<StockCompany> findById(Long id);

    List<StockCompany> findAll();

    Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId);

    StockSummary saveSummary(StockSummary stockSummary);
    Optional<StockSummary> findSummaryByStockCompanyId(Long stockCompanyId);
    StockNote saveNote(StockNote stockNote);
    List<StockNote> findNotesByStockCompanyId(Long stockCompanyId);
}