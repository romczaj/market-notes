package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.BalanceHistoryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceHistoryEntity, Long> {
    Optional<BalanceHistoryEntity> findByUserAccountIdAndBalanceDate(Long id, LocalDate localDate);

    List<BalanceHistoryEntity> findAllByUserAccountId(Long userAccountId);
}
