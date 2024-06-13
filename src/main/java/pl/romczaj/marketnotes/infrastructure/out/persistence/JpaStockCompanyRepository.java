package pl.romczaj.marketnotes.infrastructure.out.persistence;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.romczaj.marketnotes.common.StockCompanyExternalId;

import java.util.List;
import java.util.Optional;

public interface JpaStockCompanyRepository extends JpaRepository<StockCompanyEntity, Long> {
    Optional<StockCompanyEntity> findByExternalId(StockCompanyExternalId externalId);

    @Query("""
            select c, a, n
            from StockCompanyEntity c
            join StockAnalyzeEntity a on c.id = a.stockCompanyId
            left join StockNoteEntity n on c.id = n.stockCompanyId  
            where c.externalId = :externalId
            """)
    List<Tuple> getCompanyData(@Param("externalId") StockCompanyExternalId externalId);
}