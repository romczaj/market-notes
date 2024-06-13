package pl.romczaj.marketnotes.domain;

import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.domain.model.StockNote;
import pl.romczaj.marketnotes.domain.model.StockAnalyze;

import java.util.List;
import java.util.Optional;


public interface StockCompanyRepository {
    StockCompany saveStockCompany(StockCompany stockCompany);

    Optional<StockCompany> findById(Long id);

    List<StockCompany> findAll();

    Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId);

    StockAnalyze saveSummary(StockAnalyze stockAnalyze);
    Optional<StockAnalyze> findSummaryByStockCompanyId(Long stockCompanyId);
    StockNote saveNote(StockNote stockNote);
    List<StockNote> findNotesByStockCompanyId(Long stockCompanyId);
}