package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.RechargeHistoryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RechargeHistoryJpaRepository extends JpaRepository<RechargeHistoryEntity, Long> {
    Optional<RechargeHistoryEntity> findByUserAccountIdAndRechargeDate(Long userAccountId, LocalDate rechargeDate);
    List<RechargeHistoryEntity> findAllByUserAccountId(Long userAccountId);
}
