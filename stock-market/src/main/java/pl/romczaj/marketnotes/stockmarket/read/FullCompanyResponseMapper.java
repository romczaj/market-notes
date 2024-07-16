//package pl.romczaj.marketnotes.stockmarket.read;
//
//import jakarta.persistence.Tuple;
//import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.CompanySummaryResponse;
//import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.CalculationResultHistoryEntity;
//import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyEntity;
//
//import java.util.List;
//
//class FullCompanyResponseMapper {
//
//    CompanySummaryResponse mapToResponse(List<Tuple> tuples) {
//        if (tuples.isEmpty()) {
//            return null;
//        }
//
//        StockCompanyEntity companyEntity = tuples.get(0).get(0, StockCompanyEntity.class);
//        CalculationResultHistoryEntity analyzeEntity = tuples.get(0).get(1, CalculationResultHistoryEntity.class);
//
//        return new CompanySummaryResponse(
//                companyEntity.getId(),
//                companyEntity.getExternalId(),
//                companyEntity.getDataProviderSymbol(),
//                companyEntity.getCompanyName(),
//                analyzeEntity.getCalculationDate(),
//                analyzeEntity.getCalculationResult();
//    }
//}
