package pl.romczaj.marketnotes.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.romczaj.marketnotes.domain.StockCompanyRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaStockCompanyRepository extends JpaRepository<StockCompanyEntity, Long> {
    Optional<StockCompanyEntity> findByExternalId(String externalId);
}