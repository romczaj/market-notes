package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;



import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.StockCompanyRepository;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockAnalyze;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;

import java.util.*;

public class MockStockCompanyRepository implements StockCompanyRepository {
    private final Map<Long, StockCompanyEntity> DATABASE_STOCK_COMPANY = new HashMap<>();
    private final Map<Long, StockAnalyzeEntity> DATABASE_STOCK_SUMMARY = new HashMap<>();
    private final Map<Long, StockNoteEntity> DATABASE_STOCK_NOTE = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public StockCompany saveStockCompany(StockCompany stockCompany) {
        StockCompanyEntity stockCompanyEntity = StockCompanyEntity.fromDomain(stockCompany);
        if (stockCompany.id() == null) {
            stockCompanyEntity.setId(++idCounter);
        }
        DATABASE_STOCK_COMPANY.put(stockCompanyEntity.getId(), stockCompanyEntity);
        return stockCompanyEntity.toDomain();
    }

    @Override
    public Optional<StockCompany> findById(Long id) {
        return DATABASE_STOCK_COMPANY.values().stream()
                .filter(c -> c.getId().equals(id))
                .map(StockCompanyEntity::toDomain)
                .findFirst();
    }

    @Override
    public List<StockCompany> findAll() {
        return DATABASE_STOCK_COMPANY.values().stream()
                .map(StockCompanyEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId) {
        return DATABASE_STOCK_COMPANY.values().stream()
                .filter(c -> c.getExternalId().equals(stockCompanyExternalId))
                .map(StockCompanyEntity::toDomain)
                .findFirst();
    }

    @Override
    public StockAnalyze saveSummary(StockAnalyze stockAnalyze) {
        StockAnalyzeEntity stockAnalyzeEntity = StockAnalyzeEntity.fromDomain(stockAnalyze);
        if (stockAnalyze.id() == null) {
            stockAnalyzeEntity.setId(++idCounter);
        }
        DATABASE_STOCK_SUMMARY.put(stockAnalyzeEntity.getId(), stockAnalyzeEntity);
        return stockAnalyzeEntity.toDomain();
    }

    @Override
    public Optional<StockAnalyze> findSummaryByStockCompanyId(Long stockCompanyId) {
        return DATABASE_STOCK_SUMMARY.values().stream()
                .filter(s -> s.getStockCompanyId().equals(stockCompanyId))
                .map(StockAnalyzeEntity::toDomain)
                .findFirst();
    }

    @Override
    public StockNote saveNote(StockNote stockNote) {
        StockNoteEntity stockNoteEntity = StockNoteEntity.fromDomain(stockNote);
        if (stockNote.id() == null) {
            stockNoteEntity.setId(++idCounter);
        }
        DATABASE_STOCK_NOTE.put(stockNoteEntity.getId(), stockNoteEntity);
        return stockNoteEntity.toDomain();
    }

    @Override
    public List<StockNote> findNotesByStockCompanyId(Long companyId) {
        return DATABASE_STOCK_NOTE.values().stream()
                .filter(n -> n.getStockCompanyId().equals(companyId))
                .map(StockNoteEntity::toDomain)
                .toList();
    }
}