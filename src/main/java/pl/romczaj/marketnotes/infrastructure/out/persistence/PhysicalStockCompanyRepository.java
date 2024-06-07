package pl.romczaj.marketnotes.infrastructure.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.domain.*;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PhysicalStockCompanyRepository implements StockCompanyRepository {

    private final JpaStockCompanyRepository jpaStockCompanyRepository;
    private final JpaStockSummaryRepository jpaStockSummaryRepository;
    @Override
    public StockCompany saveSummary(StockCompany stockCompany) {
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
        return jpaStockCompanyRepository.findByExternalId(stockCompanyExternalId.toDatabaseColumn())
                .map(StockCompanyEntity::toDomain);
    }

    @Override
    public StockSummary saveSummary(StockSummary stockSummary) {
        return jpaStockSummaryRepository.save(StockSummaryEntity.fromDomain(stockSummary)).toDomain();
    }

    @Override
    public Optional<StockSummary> findSummaryByStockCompanyId(Long stockCompanyId) {
        return jpaStockSummaryRepository.findByStockCompanyId(stockCompanyId).map(StockSummaryEntity::toDomain);
    }

    @Override
    public StockNote saveNote(StockNote stockNote) {
        return null;
    }

    @Override
    public List<StockNote> findNotesByStockCompanyId(Long stockCompanyId) {
        return null;
    }
}
