package pl.romczaj.marketnotes.useraccount.infrastructure.out.persistence;

import pl.romczaj.marketnotes.useraccount.domain.model.*;

public class WithIdDomainMapper {

    public static UserAccount withId(UserAccount userAccount, Long id){
        return new UserAccount(
                id,
                userAccount.externalId(),
                userAccount.username(),
                userAccount.email()
        );
    }

    public static BalanceHistory withId(BalanceHistory balanceHistory, Long id){
        return new BalanceHistory(
                id,
                balanceHistory.userAccountId(),
                balanceHistory.balance(),
                balanceHistory.balanceDate()
        );
    }

    public static BuySellHistory withId(BuySellHistory buySellHistory, Long id){
        return new BuySellHistory(
                id,
                buySellHistory.userAccountId(),
                buySellHistory.stockCompanyExternalId(),
                buySellHistory.stockOperation(),
                buySellHistory.operationPrice(),
                buySellHistory.operationDate()
        );
    }

    public static CompanyComment withId(CompanyComment companyComment, Long id){
        return new CompanyComment(
                id,
                companyComment.userAccountId(),
                companyComment.stockCompanyExternalId(),
                companyComment.noteDate(),
                companyComment.companyPrice(),
                companyComment.noteContent()
        );
    }

    public static CompanyInvestGoal withId(CompanyInvestGoal companyInvestGoal, Long id){
        return new CompanyInvestGoal(
                id,
                companyInvestGoal.userAccountId(),
                companyInvestGoal.stockCompanyExternalId(),
                companyInvestGoal.buyStopPrice(),
                companyInvestGoal.sellStopPrice(),
                companyInvestGoal.buyLimitPrice(),
                companyInvestGoal.sellLimitPrice()
        );
    }

    public static InvestReport withId(InvestReport investReport, Long id){
        return new InvestReport(
                id,
                investReport.userAccountId(),
                investReport.sendDate(),
                investReport.companyUserNotifications(),
                investReport.successSend(),
                investReport.emailMessage(),
                investReport.emailSubject()
        );
    }

    public static RechargeHistory withId(RechargeHistory rechargeHistory, Long id){
        return new RechargeHistory(
                id,
                rechargeHistory.userAccountId(),
                rechargeHistory.chargedMoney(),
                rechargeHistory.rechargeDate()
        );
    }
}
