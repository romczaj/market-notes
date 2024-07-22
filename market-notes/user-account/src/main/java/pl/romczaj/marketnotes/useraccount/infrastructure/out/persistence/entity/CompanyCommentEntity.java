package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.persistance.StockCompanyExternalIdDatabaseConverter;
import pl.romczaj.marketnotes.useraccount.domain.model.CompanyComment;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account_company_comment")
public class CompanyCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long userAccountId;
    @Convert(converter = StockCompanyExternalIdDatabaseConverter.class)
    private StockCompanyExternalId stockCompanyExternalId;
    private Instant noteDate;
    private Double companyPrice;
    private String noteContent;

    public static CompanyCommentEntity fromDomain(CompanyComment companyComment) {
        return new CompanyCommentEntity(
                companyComment.id(),
                companyComment.userAccountId(),
                companyComment.stockCompanyExternalId(),
                companyComment.noteDate(),
                companyComment.companyPrice(),
                companyComment.noteContent()
        );
    }

    public CompanyComment toDomain() {
        return new CompanyComment(
                id,
                userAccountId,
                stockCompanyExternalId,
                noteDate,
                companyPrice,
                noteContent
        );
    }

}
