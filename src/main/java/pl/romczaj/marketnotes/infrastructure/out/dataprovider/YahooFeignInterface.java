package pl.romczaj.marketnotes.infrastructure.out.dataprovider;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import feign.Param;
import feign.RequestLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

public interface YahooFeignInterface {
//https://query1.finance.yahoo.com/v7/finance/download/ELT.WA?period1=1686068941&period2=1717691341&interval=1d&events=history&includeAdjustedClose=true
    @RequestLine(value = "GET /v7/finance/download/{companyShortcut}?period1={period1}&period2={period2}&interval={interval}&events=history&includeAdjustedClose=true")
    List<HistoricDataFeignResponse> getCompanyHistoryData(
            @Param("companyShortcut") String companyShortcut,
            @Param("period1") long dateFrom,
            @Param("period2") long dateTo,
            @Param("interval") String interval);

    @NoArgsConstructor
    @Getter
    @Setter
    @Accessors(fluent = true)
    class HistoricDataFeignResponse {
        @CsvBindByName(column = "Data")
        @CsvDate(value = "yyyy-MM-dd")
        private LocalDate date;
        @CsvBindByName(column = "Otwarcie")
        private double openPrice;
        @CsvBindByName(column = "Zamkniecie")
        private double closePrice;
    }
}
