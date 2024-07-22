package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.BuySellHistoryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BuySellHistoryJpaRepository extends JpaRepository<BuySellHistoryEntity, Long> {
    Optional<BuySellHistoryEntity> findByUserAccountIdAndOperationDateAndStockCompanyExternalId(
            Long userAccountId, LocalDate operationDate, StockCompanyExternalId companyExternalId);

    List<BuySellHistoryEntity> findAllByUserAccountId(Long userAccountId);
    List<BuySellHistoryEntity> findAllByUserAccountIdAndStockCompanyExternalId(Long userAccountId, StockCompanyExternalId stockCompanyExternalId);
}
