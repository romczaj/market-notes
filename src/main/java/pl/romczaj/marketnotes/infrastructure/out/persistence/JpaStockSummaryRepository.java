package pl.romczaj.marketnotes.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaStockSummaryRepository extends JpaRepository<StockAnalyzeEntity, Long> {
    Optional<StockAnalyzeEntity> findByStockCompanyId(Long stockCompanyId);
}