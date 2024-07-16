package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;

import java.util.Optional;

public interface JpaStockCompanySummaryViewRepository extends JpaRepository<StockCompanySummaryView, Long> {

    Optional<StockCompanySummaryView> findByExternalId(StockCompanyExternalId stockCompanyExternalId);
}
