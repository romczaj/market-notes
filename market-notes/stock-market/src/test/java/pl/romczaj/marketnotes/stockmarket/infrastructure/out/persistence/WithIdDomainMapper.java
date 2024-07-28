package pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence;

import pl.romczaj.marketnotes.stockmarket.domain.model.CalculationResultHistory;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockCompany;
import pl.romczaj.marketnotes.stockmarket.domain.model.StockNote;

public class WithIdDomainMapper {

    public static StockCompany withId(StockCompany stockCompany, Long id){
        return new StockCompany(
                id,
                stockCompany.stockCompanyExternalId(),
                stockCompany.dataProviderSymbol(),
                stockCompany.companyName(),
                stockCompany.actualPrice()
        );
    }

    public static StockNote withId(StockNote stockNote, Long id){
        return new StockNote(
                id,
                stockNote.stockCompanyId(),
                stockNote.noteDate(),
                stockNote.priceDate(),
                stockNote.price(),
                stockNote.noteContent()
        );
    }

    public static CalculationResultHistory withId(CalculationResultHistory resultHistory, Long id){
        return new CalculationResultHistory(
                id,
                resultHistory.stockCompanyId(),
                resultHistory.calculationDate(),
                resultHistory.calculationResult()
        );
    }

}
