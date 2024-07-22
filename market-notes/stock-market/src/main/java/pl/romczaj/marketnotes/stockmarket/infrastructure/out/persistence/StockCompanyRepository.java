package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;

import java.util.List;
import java.util.Optional;


public interface StockCompanyRepository {
    StockCompany saveStockCompany(StockCompany stockCompany);

    Optional<StockCompany> findById(Long id);

    List<StockCompany> findAll();

    Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId);

    CalculationResultHistory saveSummary(CalculationResultHistory calculationResultHistory);
    Optional<CalculationResultHistory> findNewestCalculationResult(Long stockCompanyId);
    StockNote saveNote(StockNote stockNote);
    List<StockNote> findNotesByStockCompanyId(Long stockCompanyId);
}