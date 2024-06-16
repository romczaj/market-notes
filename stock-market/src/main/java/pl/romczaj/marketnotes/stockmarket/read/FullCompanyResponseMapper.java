package pl.romczaj.marketnotes.stockmarket.read;

import jakarta.persistence.Tuple;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.AnalyzeResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.CompanyNoteResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.FullCompanyResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.StockCompanyBaseInfoResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockAnalyzeEntity;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockCompanyEntity;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.StockNoteEntity;

import java.util.List;
import java.util.Objects;

class FullCompanyResponseMapper {

    FullCompanyResponse mapToResponse(List<Tuple> tuples) {
        if (tuples.isEmpty()) {
            return null;
        }

        StockCompanyEntity companyEntity = tuples.get(0).get(0, StockCompanyEntity.class);
        StockAnalyzeEntity analyzeEntity = tuples.get(0).get(1, StockAnalyzeEntity.class);

        StockCompanyBaseInfoResponse baseInfo = new StockCompanyBaseInfoResponse(
                companyEntity.getId(),
                companyEntity.getExternalId(),
                companyEntity.getDataProviderSymbol(),
                companyEntity.getCompanyName()
        );

        AnalyzeResponse analyze = new AnalyzeResponse(
                analyzeEntity.getId(),
                analyzeEntity.getDailyIncrease(),
                analyzeEntity.getWeeklyIncrease(),
                analyzeEntity.getMonthlyIncrease(),
                analyzeEntity.getThreeMonthsIncrease(),
                analyzeEntity.getSixMonthsIncrease(),
                analyzeEntity.getYearlyIncrease(),
                analyzeEntity.getTwoYearsIncrease()
        );

        List<CompanyNoteResponse> notes = tuples.stream()
                .map(tuple -> tuple.get(2, StockNoteEntity.class))
                .filter(Objects::nonNull)
                .map(noteEntity -> new CompanyNoteResponse(
                        noteEntity.getId(),
                        noteEntity.getNoteDate(),
                        noteEntity.getPrice(),
                        noteEntity.getNoteContent()
                ))
                .toList();

        return new FullCompanyResponse(baseInfo, notes, analyze);
    }
}
