package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaStockNoteRepository extends JpaRepository<StockNoteEntity, Long>{

    List<StockNoteEntity> findAllByStockCompanyId(Long companyId);
}
