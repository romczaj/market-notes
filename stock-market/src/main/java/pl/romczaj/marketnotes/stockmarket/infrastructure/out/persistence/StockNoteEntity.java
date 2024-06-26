package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_company_note")
public class StockNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long stockCompanyId;
    private Instant noteDate;
    private LocalDate priceDate;
    private Double price;
    private String noteContent;

    public static StockNoteEntity fromDomain(StockNote stockNote) {
        return new StockNoteEntity(
                stockNote.id(),
                stockNote.stockCompanyId(),
                stockNote.noteDate(),
                stockNote.priceDate(),
                stockNote.price(),
                stockNote.noteContent()
        );
    }

    public StockNote toDomain() {
        return new StockNote(
                id,
                stockCompanyId,
                noteDate,
                priceDate,
                price,
                noteContent
        );
    }
}