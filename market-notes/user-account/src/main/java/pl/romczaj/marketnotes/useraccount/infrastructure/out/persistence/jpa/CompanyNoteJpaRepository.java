package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.CompanyCommentEntity;

import java.util.List;

public interface CompanyNoteJpaRepository extends JpaRepository<CompanyCommentEntity, Long> {

    List<CompanyCommentEntity> findAlLByUserAccountId(Long userAccountId);

    List<CompanyCommentEntity> findByUserAccountIdAndStockCompanyExternalId(
            Long accountId, StockCompanyExternalId stockCompanyExternalId);

}
