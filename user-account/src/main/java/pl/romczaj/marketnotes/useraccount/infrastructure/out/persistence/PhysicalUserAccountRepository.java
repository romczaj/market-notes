package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.common.id.UserAccountExternalId;
import pl.romczaj.marketnotes.useraccount.domain.model.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.entity.*;
import pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence.jpa.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PhysicalUserAccountRepository implements UserAccountRepository {

    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;
    private final BuySellHistoryJpaRepository buySellHistoryJpaRepository;
    private final RechargeHistoryJpaRepository rechargeHistoryJpaRepository;
    private final UserAccountJpaRepository userAccountJpaRepository;
    private final CompanyInvestGoalJpaRepository companyInvestGoalJpaRepository;
    private final InvestReportJpaRepository investReportJpaRepository;
    private final CompanyNoteJpaRepository companyNoteJpaRepository;


    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        UserAccountEntity userAccountEntity = UserAccountEntity.fromDomain(userAccount);
        return userAccountJpaRepository.save(userAccountEntity).toDomain();
    }

    @Override
    public Optional<UserAccount> findByExternalId(UserAccountExternalId userAccountExternalId) {
        return userAccountJpaRepository.findByExternalId(userAccountExternalId).map(UserAccountEntity::toDomain);
    }

    @Override
    public void saveRechargeHistory(RechargeHistory rechargeHistory) {
        RechargeHistoryEntity entity = RechargeHistoryEntity.fromDomain(rechargeHistory);
        rechargeHistoryJpaRepository.save(entity);
    }

    @Override
    public void saveBuySellHistory(BuySellHistory buySellHistory) {
        BuySellHistoryEntity entity = BuySellHistoryEntity.fromDomain(buySellHistory);
        buySellHistoryJpaRepository.save(entity);
    }

    @Override
    public CompanyInvestGoal saveCompanyInvestGoal(CompanyInvestGoal companyInvestGoal) {
        CompanyInvestGoalEntity entity = CompanyInvestGoalEntity.fromDomain(companyInvestGoal);
        return companyInvestGoalJpaRepository.save(entity).toDomain();
    }

    @Override
    public InvestReport saveInvestReport(InvestReport investReport) {
        return investReportJpaRepository.save(InvestReportEntity.fromDomain(investReport)).toDomain();
    }

    @Override
    public Optional<RechargeHistory> findRechargeHistory(Long userAccountId, LocalDate rechargeDate) {
        return rechargeHistoryJpaRepository.findByUserAccountIdAndRechargeDate(userAccountId, rechargeDate)
                .map(RechargeHistoryEntity::toDomain);
    }

    @Override
    public Optional<BuySellHistory> findBuySellHistory(Long userAccountId, LocalDate operationDate, StockCompanyExternalId companyExternalId) {
        return buySellHistoryJpaRepository
                .findByUserAccountIdAndOperationDateAndStockCompanyExternalId(userAccountId, operationDate, companyExternalId)
                .map(BuySellHistoryEntity::toDomain);
    }

    @Override
    public Optional<BalanceHistory> findBalanceHistory(Long id, LocalDate localDate) {
        return balanceHistoryJpaRepository.findByUserAccountIdAndBalanceDate(id, localDate)
                .map(BalanceHistoryEntity::toDomain);
    }

    @Override
    public BalanceHistory saveBalanceHistory(BalanceHistory balanceHistory) {
        BalanceHistoryEntity entity = BalanceHistoryEntity.fromDomain(balanceHistory);
        return balanceHistoryJpaRepository.save(entity).toDomain();
    }

    @Override
    public List<UserAccount> findAll() {
        return userAccountJpaRepository.findAll().stream().map(UserAccountEntity::toDomain).toList();
    }

    @Override
    public List<BalanceHistory> findBalanceHistoryByUserAccountId(Long userAccountId) {
        return balanceHistoryJpaRepository.findAllByUserAccountId(userAccountId)
                .stream().map(BalanceHistoryEntity::toDomain).toList();

    }

    @Override
    public List<RechargeHistory> findRechargeHistoryByUserAccountId(Long userAccountId) {
        return rechargeHistoryJpaRepository.findAllByUserAccountId(userAccountId)
                .stream().map(RechargeHistoryEntity::toDomain).toList();
    }

    @Override
    public List<BuySellHistory> findBuySellHistoryByUserAccountId(Long userAccountId) {
        return buySellHistoryJpaRepository.findAllByUserAccountId(userAccountId)
                .stream().map(BuySellHistoryEntity::toDomain).toList();
    }

    @Override
    public List<CompanyInvestGoal> findCompanyInvestGoalByUserAccountId(Long userAccountId) {
        return companyInvestGoalJpaRepository.findAllByUserAccountId(userAccountId)
                .stream().map(CompanyInvestGoalEntity::toDomain).toList();
    }

    @Override
    public List<InvestReport> findInvestReportByUserAccountId(Long userAccountId) {
        return investReportJpaRepository.findAllByUserAccountId(userAccountId)
                .stream().map(InvestReportEntity::toDomain).toList();
    }

    @Override
    public Optional<CompanyInvestGoal> findCompanyInvestGoal(Long userAccountId, StockCompanyExternalId stockCompanyExternalId) {
        return companyInvestGoalJpaRepository.findByUserAccountIdAndStockCompanyExternalId(userAccountId, stockCompanyExternalId)
                .map(CompanyInvestGoalEntity::toDomain);
    }

    @Override
    public void saveCompanyComment(CompanyComment companyComment) {
        CompanyCommentEntity companyCommentEntity = CompanyCommentEntity.fromDomain(companyComment);
        companyNoteJpaRepository.save(companyCommentEntity);
    }

    @Override
    public List<CompanyComment> findCompanyCommentByUserAccountId(Long userAccountId) {
        return companyNoteJpaRepository.findAlLByUserAccountId(userAccountId)
                .stream().map(CompanyCommentEntity::toDomain).toList();
    }
}
