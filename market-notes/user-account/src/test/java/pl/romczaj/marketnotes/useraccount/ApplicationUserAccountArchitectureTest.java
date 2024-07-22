package pl.romczaj.marketnotes.useraccount;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class ApplicationUserAccountArchitectureTest {

    static final String BASE_PACKAGE = "pl.romczaj.marketnotes.useraccount";
    static final String REST_ADAPTER = "RestAdapter";
    static final String PERSISTENCE_ADAPTER = "PersistenceAdapter";
    static final String APPLICATION = "Application";
    static final String DOMAIN = "Domain";
    static final String JOB = "Job";
    static final String READ = "Read";
    final JavaClasses javaClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

    @Test
    void shouldValidate() {

        Architectures.LayeredArchitecture layeredArchitecture = layeredArchitecture()
                .consideringAllDependencies()

                .layer(REST_ADAPTER).definedBy(packageForLayer("infrastructure.in.rest"))
                .layer(JOB).definedBy(packageForLayer("infrastructure.in.job"))
                .layer(PERSISTENCE_ADAPTER).definedBy(packageForLayer("infrastructure.out.persistence"))
                .layer(APPLICATION).definedBy(packageForLayer("application"))
                .layer(DOMAIN).definedBy(packageForLayer("domain"))
                .layer(READ).definedBy(packageForLayer("read"))

                .whereLayer(REST_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, DOMAIN, READ)
                .whereLayer(JOB).mayOnlyBeAccessedByLayers(APPLICATION)
                .whereLayer(PERSISTENCE_ADAPTER).mayOnlyBeAccessedByLayers(APPLICATION, READ)
                .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(APPLICATION, PERSISTENCE_ADAPTER)
                .whereLayer(APPLICATION).mayNotBeAccessedByAnyLayer();

        layeredArchitecture.check(javaClasses);
    }

    String packageForLayer(String layer) {
        return BASE_PACKAGE + "." + layer.toLowerCase() + "..";
    }
}