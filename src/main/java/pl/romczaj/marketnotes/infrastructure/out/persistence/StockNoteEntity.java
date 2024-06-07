package pl.romczaj.marketnotes.infrastructure.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.domain.StockNote;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_note")
public class StockNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long companyId;
    private Instant noteDate;
    private String noteContent;

    public static StockNoteEntity fromDomain(StockNote stockNote) {
        return new StockNoteEntity(
                stockNote.id(),
                stockNote.companyId(),
                stockNote.noteDate(),
                stockNote.noteContent()
        );
    }

    public StockNote toDomain() {
        return new StockNote(
                id,
                companyId,
                noteDate,
                noteContent
        );
    }
}