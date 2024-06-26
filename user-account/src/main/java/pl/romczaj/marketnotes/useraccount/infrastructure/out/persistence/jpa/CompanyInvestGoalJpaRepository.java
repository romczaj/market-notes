package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.CompanyInvestGoalEntity;

import java.util.List;
import java.util.Optional;

public interface CompanyInvestGoalJpaRepository extends JpaRepository<CompanyInvestGoalEntity, Long> {

    List<CompanyInvestGoalEntity> findAllByUserAccountId(Long userAccountId);
    Optional<CompanyInvestGoalEntity> findByUserAccountIdAndStockCompanyExternalId(
            Long userAccountId, StockCompanyExternalId stockCompanyExternalId);
}
