package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;
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
    public CalculationResultHistory saveSummary(CalculationResultHistory calculationResultHistory) {
        return jpaStockSummaryRepository.save(CalculationResultHistoryEntity.fromDomain(calculationResultHistory)).toDomain();
    }

    @Override
    public Optional<CalculationResultHistory> findNewestCalculationResult(Long stockCompanyId) {
        return jpaStockSummaryRepository.findTopByStockCompanyIdOrderByIdDesc(stockCompanyId).map(CalculationResultHistoryEntity::toDomain);
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
