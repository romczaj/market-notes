package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.InvestReportEntity;

import java.util.List;

public interface InvestReportJpaRepository extends JpaRepository<InvestReportEntity, Long> {
    List<InvestReportEntity> findAllByUserAccountId(Long userAccountId);
}
