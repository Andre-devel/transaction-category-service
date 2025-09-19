package br.com.andredevel.transaction.category.service.domain;

import br.com.andredevel.transaction.category.service.config.BaseIntegrationTest;
import br.com.andredevel.transaction.category.service.domain.exception.NameInUseException;
import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.id.UserId;
import br.com.andredevel.transaction.category.service.domain.model.validator.rule.NameRuleValidator;
import br.com.andredevel.transaction.category.service.domain.model.valueobject.Name;
import br.com.andredevel.transaction.category.service.domain.repository.TransactionCategoryRepository;
import br.com.andredevel.transaction.category.service.util.TransactionCategoryTestBuilder;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class NameRuleValidationTest extends BaseIntegrationTest {

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @BeforeEach
    void setUp() {
        transactionCategoryRepository.deleteAll();
    }

    @Test
    void shouldValidateNameUniqueness_WhenNameDoesNotExistForUser() {
        UserId userId = new UserId(UUID.randomUUID());
        TransactionCategory category = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("New Category"))
                .build();

        assertThatCode(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, category))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowException_WhenNameAlreadyExistsForSameUser() {
        UserId userId = new UserId(UUID.randomUUID());
        TransactionCategory existingCategory = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Existing Category"))
                .build();
        transactionCategoryRepository.save(existingCategory);

        TransactionCategory categoryWithDuplicateName = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Existing Category"))
                .build();

        assertThatThrownBy(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, categoryWithDuplicateName))
                .isInstanceOf(NameInUseException.class);
    }

    @Test
    void shouldNotThrowException_WhenNameExistsForSameCategory() {
        UserId userId = new UserId(UUID.randomUUID());
        TransactionCategory existingCategory = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Existing Category"))
                .build();
        TransactionCategory savedCategory = transactionCategoryRepository.save(existingCategory);

        assertThatCode(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, savedCategory))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowException_WhenNameExistsForDifferentUser() {
        UserId user1 = new UserId(UUID.randomUUID());
        UserId user2 = new UserId(UUID.randomUUID());

        TransactionCategory category1 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(user1)
                .name(new Name("Category Name"))
                .build();
        transactionCategoryRepository.save(category1);

        TransactionCategory category2 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(user2)
                .name(new Name("Category Name"))
                .build();

        assertThatCode(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, category2))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldHandleMultipleCategories_AndValidateCorrectly() {
        UserId userId = new UserId(UUID.randomUUID());

        TransactionCategory category1 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 1"))
                .description("First category")
                .build();
        TransactionCategory category2 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 2"))
                .description("Second category")
                .build();
        TransactionCategory category3 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 3"))
                .description("Third category")
                .build();

        TransactionCategory savedCategory1 = transactionCategoryRepository.save(category1);
        TransactionCategory savedCategory2 = transactionCategoryRepository.save(category2);
        TransactionCategory savedCategory3 = transactionCategoryRepository.save(category3);

        assertThatCode(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, savedCategory1))
                .doesNotThrowAnyException();

        TransactionCategory categoryWithDuplicateName = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 1"))
                .description("Category with duplicate name")
                .build();

        assertThatThrownBy(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, categoryWithDuplicateName))
                .isInstanceOf(NameInUseException.class);

        TransactionCategory categoryWithNewName = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("New Unique Category"))
                .description("Category with new name")
                .build();

        assertThatCode(() -> NameRuleValidator.validateNameUniqueForUser(transactionCategoryRepository, categoryWithNewName))
                .doesNotThrowAnyException();
    }
}