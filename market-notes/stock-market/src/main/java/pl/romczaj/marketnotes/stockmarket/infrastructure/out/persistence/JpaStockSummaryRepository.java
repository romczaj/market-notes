package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaStockSummaryRepository extends JpaRepository<CalculationResultHistoryEntity, Long> {
    Optional<CalculationResultHistoryEntity> findTopByStockCompanyIdOrderByIdDesc(Long stockCompanyId);
}