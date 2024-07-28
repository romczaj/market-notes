package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;


import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.repository.InMemoryRepository;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;

import java.util.*;



public class MockStockCompanyRepository implements StockCompanyRepository, InMemoryRepository {

    private final Map<Long, StockCompany> STOCK_COMPANY_DB = new HashMap<>();
    private final Map<Long, CalculationResultHistory> RESULT_HISTORY_DB = new HashMap<>();
    private final Map<Long, StockNote> STOCK_NOTE_DB = new HashMap<>();

    @Override
    public StockCompany saveStockCompany(StockCompany stockCompany) {
        return saveDomainModel(stockCompany, STOCK_COMPANY_DB, WithIdDomainMapper::withId);
    }

    @Override
    public Optional<StockCompany> findById(Long id) {
        return Optional.ofNullable(STOCK_COMPANY_DB.get(id));
    }

    @Override
    public List<StockCompany> findAll() {
        return STOCK_COMPANY_DB.values().stream().toList();
    }

    @Override
    public Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId) {
        return STOCK_COMPANY_DB.values().stream()
                .filter(s -> s.stockCompanyExternalId().equals(stockCompanyExternalId))
                .findFirst();
    }

    @Override
    public CalculationResultHistory saveSummary(CalculationResultHistory resultHistory) {
        return saveDomainModel(resultHistory, RESULT_HISTORY_DB, WithIdDomainMapper::withId);

    }

    @Override
    public Optional<CalculationResultHistory> findNewestCalculationResult(Long stockCompanyId) {
        return RESULT_HISTORY_DB.values().stream()
                .filter(s -> s.stockCompanyId().equals(stockCompanyId))
                .max(Comparator.comparing(CalculationResultHistory::calculationDate));
    }

    @Override
    public StockNote saveNote(StockNote stockNote) {
        return saveDomainModel(stockNote, STOCK_NOTE_DB, WithIdDomainMapper::withId);
    }

    @Override
    public List<StockNote> findNotesByStockCompanyId(Long companyId) {
        return STOCK_NOTE_DB.values().stream()
                .filter(s -> s.stockCompanyId().equals(companyId))
                .toList();
    }
}