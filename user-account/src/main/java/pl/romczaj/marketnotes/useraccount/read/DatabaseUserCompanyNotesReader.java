package pl.romczaj.marketnotes.useraccount.read;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.internalapi.AuthenticationRetriever;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.UserCompanyNotesReader;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse.BuySellOperationResponse;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse.CommentResponse;
import pl.romczaj.marketnotes.useraccount.infrastructure.in.rest.response.UserCompanyNotesResponse.InvestGoalResponse;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.BuySellHistoryEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.CompanyCommentEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.CompanyInvestGoalEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.UserAccountEntity;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa.BuySellHistoryJpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa.CompanyInvestGoalJpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa.CompanyNoteJpaRepository;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa.UserAccountJpaRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DatabaseUserCompanyNotesReader implements UserCompanyNotesReader {

    private final AuthenticationRetriever authenticationRetriever;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final BuySellHistoryJpaRepository buySellHistoryJpaRepository;
    private final CompanyInvestGoalJpaRepository companyInvestGoalJpaRepository;
    private final CompanyNoteJpaRepository companyNoteJpaRepository;


    @Override
    public UserCompanyNotesResponse getCompanyNotes(StockCompanyExternalId stockCompanyExternalId) {
        UserAccountExternalId userAccountExternalId = authenticationRetriever.userAccountExternalId();
        UserAccountEntity userAccountEntity = userAccountJpaRepository.findByExternalId(userAccountExternalId).orElseThrow();

        InvestGoalResponse investGoalResponse = companyInvestGoalJpaRepository
                .findByUserAccountIdAndStockCompanyExternalId(userAccountEntity.getId(), stockCompanyExternalId)
                .map(this::toResponse)
                .orElse(null);

        List<CommentResponse> commentResponses = companyNoteJpaRepository
                .findByUserAccountIdAndStockCompanyExternalId(userAccountEntity.getId(), stockCompanyExternalId)
                .stream().map(this::toResponse).toList();

        List<BuySellOperationResponse> sellOperationResponses = buySellHistoryJpaRepository
                .findAllByUserAccountIdAndStockCompanyExternalId(userAccountEntity.getId(), stockCompanyExternalId)
                .stream().map(this::toResponse).toList();

        return new UserCompanyNotesResponse(
                userAccountExternalId,
                stockCompanyExternalId,
                investGoalResponse,
                commentResponses,
                sellOperationResponses
        );
    }

    private CommentResponse toResponse(CompanyCommentEntity companyCommentEntity) {
        return new CommentResponse(
                companyCommentEntity.getNoteDate(),
                companyCommentEntity.getCompanyPrice(),
                companyCommentEntity.getNoteContent()
        );
    }

    private InvestGoalResponse toResponse(CompanyInvestGoalEntity companyInvestGoalEntity) {
        return new InvestGoalResponse(
                companyInvestGoalEntity.getBuyStopPrice(),
                companyInvestGoalEntity.getSellStopPrice(),
                companyInvestGoalEntity.getBuyLimitPrice(),
                companyInvestGoalEntity.getSellLimitPrice()
        );
    }

    private BuySellOperationResponse toResponse(BuySellHistoryEntity buySellHistoryEntity) {
        return new BuySellOperationResponse(
                buySellHistoryEntity.getStockOperation(),
                buySellHistoryEntity.getOperationAmount(),
                buySellHistoryEntity.getOperationCurrency(),
                buySellHistoryEntity.getOperationDate()
        );
    }


}
