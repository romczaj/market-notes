package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.UserAccountEntity;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByExternalId(UserAccountExternalId externalId);
}
