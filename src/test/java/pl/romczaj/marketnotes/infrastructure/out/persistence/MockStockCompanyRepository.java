package pl.romczaj.marketnotes.infrastructure.out.persistence;

import pl.romczaj.marketnotes.domain.*;

import java.util.*;

public class MockStockCompanyRepository implements StockCompanyRepository {
    private final Map<Long, StockCompanyEntity> DATABASE_STOCK_COMPANY = new HashMap<>();
    private final Map<Long, StockSummaryEntity> DATABASE_STOCK_SUMMARY = new HashMap<>();
    private final Map<Long, StockNoteEntity> DATABASE_STOCK_NOTE = new HashMap<>();
    private Long idCounter = 0L;

    @Override
    public StockCompany saveSummary(StockCompany stockCompany) {
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
                .filter(c -> c.getExternalId().equalsIgnoreCase(stockCompanyExternalId.toDatabaseColumn()))
                .map(StockCompanyEntity::toDomain)
                .findFirst();
    }

    @Override
    public StockSummary saveSummary(StockSummary stockSummary) {
        StockSummaryEntity stockSummaryEntity = StockSummaryEntity.fromDomain(stockSummary);
        if (stockSummary.id() == null) {
            stockSummaryEntity.setId(++idCounter);
        }
        DATABASE_STOCK_SUMMARY.put(stockSummaryEntity.getId(), stockSummaryEntity);
        return stockSummaryEntity.toDomain();
    }

    @Override
    public Optional<StockSummary> findSummaryByStockCompanyId(Long stockCompanyId) {
        return DATABASE_STOCK_SUMMARY.values().stream()
                .filter(s -> s.getStockCompanyId().equals(stockCompanyId))
                .map(StockSummaryEntity::toDomain)
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
                .filter(n -> n.getCompanyId().equals(companyId))
                .map(StockNoteEntity::toDomain)
                .toList();
    }
}