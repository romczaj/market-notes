package pl.romczaj.marketnotes.stockmarket.read;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.romczaj.marketnotes.common.id.StockCompanyExternalId;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.CompanyReader;
import pl.romczaj.marketnotes.stockmarket.infrastructure.in.rest.respose.FullCompanyResponse;
import pl.romczaj.marketnotes.stockmarket.infrastructure.out.persistence.JpaStockCompanyRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class DatabaseCompanyReader implements CompanyReader {

    private final JpaStockCompanyRepository jpaStockCompanyRepository;

    private final FullCompanyResponseMapper fullCompanyResponseMapper = new FullCompanyResponseMapper();
    @Override
    public FullCompanyResponse getCompany(StockCompanyExternalId companyId) {
        List<Tuple> resultList = jpaStockCompanyRepository.getCompanyData(companyId);
        return fullCompanyResponseMapper.mapToResponse(resultList);
    }
}
