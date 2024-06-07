package pl.romczaj.marketnotes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ApplicationArchitectureTest {

    static final String BASE_PACKAGE = "pl.romczaj.marketnotes";
    static final String ANALYZER_ADAPTER = "AnalyzerAdapter";
    static final String DATA_PROVIDER_ADAPTER = "DataProviderAdapter";
    static final String REST_ADAPTER = "RestAdapter";
    static final String PERSISTENCE_ADAPTER = "PersistenceAdapter";
    static final String JOB_ADAPTER = "JobAdapter";
    static final String APPLICATION = "Application";
    static final String COMMON = "Common";
    static final String DOMAIN = "Domain";
    final JavaClasses javaClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

    @Test
    void shouldValidate() {

        Architectures.LayeredArchitecture layeredArchitecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer(ANALYZER_ADAPTER).definedBy(packageForLayer("infrastructure.out.analyzer"))
                .layer(JOB_ADAPTER).definedBy(packageForLayer("infrastructure.in.job"))
                .layer(DATA_PROVIDER_ADAPTER).definedBy(packageForLayer("infrastructure.out.dataprovider"))
                .layer(REST_ADAPTER).definedBy(packageForLayer("infrastructure.in.rest"))
                .layer(PERSISTENCE_ADAPTER).definedBy(packageForLayer("infrastructure.out.persistence"))
                .layer(APPLICATION).definedBy(packageForLayer("application"))
                .layer(COMMON).definedBy(packageForLayer("common"))
                .layer(DOMAIN).definedBy(packageForLayer("domain"))

                .whereLayer(COMMON).mayOnlyBeAccessedByLayers(ANALYZER_ADAPTER, DATA_PROVIDER_ADAPTER)

                .whereLayer(ANALYZER_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN)

                .whereLayer(JOB_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN)

                .whereLayer(DATA_PROVIDER_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN)

                .whereLayer(REST_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN)

                .whereLayer(PERSISTENCE_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION)

                .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION, PERSISTENCE_ADAPTER)

                .whereLayer(APPLICATION).mayNotBeAccessedByAnyLayer();

        layeredArchitecture.check(javaClasses);
    }

    String packageForLayer(String layer) {
        return BASE_PACKAGE + "." + layer.toLowerCase() + "..";
    }
}