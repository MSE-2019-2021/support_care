package uc.dei.mse.supportivecare;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("uc.dei.mse.supportivecare");

        noClasses()
            .that()
            .resideInAnyPackage("uc.dei.mse.supportivecare.service..")
            .or()
            .resideInAnyPackage("uc.dei.mse.supportivecare.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..uc.dei.mse.supportivecare.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
