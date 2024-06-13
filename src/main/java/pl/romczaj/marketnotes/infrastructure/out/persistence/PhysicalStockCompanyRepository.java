package pl.romczaj.marketnotes.infrastructure.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;
import pl.romczaj.marketnotes.domain.*;
import pl.romczaj.marketnotes.domain.model.StockCompany;
import pl.romczaj.marketnotes.domain.model.StockNote;
import pl.romczaj.marketnotes.domain.model.StockAnalyze;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PhysicalStockCompanyRepository implements StockCompanyRepository {

    private final JpaStockCompanyRepository jpaStockCompanyRepository;
    private final JpaStockSummaryRepository jpaStockSummaryRepository;
    private final JpaStockNoteRepository jpaStockNoteRepository;
    @Override
    public StockCompany saveStockCompany(StockCompany stockCompany) {
        StockCompanyEntity stockCompanyEntity = StockCompanyEntity.fromDomain(stockCompany);
        return jpaStockCompanyRepository.save(stockCompanyEntity).toDomain();
    }

    @Override
    public Optional<StockCompany> findById(Long id) {
        return jpaStockCompanyRepository.findById(id).map(StockCompanyEntity::toDomain);
    }

    @Override
    public List<StockCompany> findAll() {
        return jpaStockCompanyRepository.findAll().stream()
                .map(StockCompanyEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<StockCompany> findByExternalId(StockCompanyExternalId stockCompanyExternalId) {
        return jpaStockCompanyRepository.findByExternalId(stockCompanyExternalId)
                .map(StockCompanyEntity::toDomain);
    }

    @Override
    public StockAnalyze saveSummary(StockAnalyze stockAnalyze) {
        return jpaStockSummaryRepository.save(StockAnalyzeEntity.fromDomain(stockAnalyze)).toDomain();
    }

    @Override
    public Optional<StockAnalyze> findSummaryByStockCompanyId(Long stockCompanyId) {
        return jpaStockSummaryRepository.findByStockCompanyId(stockCompanyId).map(StockAnalyzeEntity::toDomain);
    }

    @Override
    public StockNote saveNote(StockNote stockNote) {
        return jpaStockNoteRepository.save(StockNoteEntity.fromDomain(stockNote)).toDomain();
    }

    @Override
    public List<StockNote> findNotesByStockCompanyId(Long stockCompanyId) {
        return jpaStockNoteRepository.findAllByStockCompanyId(stockCompanyId).stream()
                .map(StockNoteEntity::toDomain)
                .toList();
    }
}
