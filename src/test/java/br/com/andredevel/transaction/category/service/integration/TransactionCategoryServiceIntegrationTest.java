package br.com.andredevel.transaction.category.service.integration;

import br.com.andredevel.transaction.category.service.config.BaseIntegrationTest;
import br.com.andredevel.transaction.category.service.domain.exception.NameInUseException;
import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.id.UserId;
import br.com.andredevel.transaction.category.service.domain.model.valueobject.Name;
import br.com.andredevel.transaction.category.service.domain.repository.TransactionCategoryRepository;
import br.com.andredevel.transaction.category.service.domain.service.TransactionCategoryService;
import br.com.andredevel.transaction.category.service.util.TransactionCategoryTestBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class TransactionCategoryServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TransactionCategoryService transactionCategoryService;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @BeforeEach
    void setUp() {
        transactionCategoryRepository.deleteAll();
    }

    @Test
    void shouldSaveNewTransactionCategory() {
        TransactionCategory category = TransactionCategoryTestBuilder.existingTransactionCategory()
                .name(new Name("Test Category"))
                .description("Test description")
                .build();

        assertThatCode(() -> transactionCategoryService.save(category))
                .doesNotThrowAnyException();

        List<TransactionCategory> categories = transactionCategoryService.findAll();
        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getName().value()).isEqualTo("Test Category");
        assertThat(categories.get(0).getDescription()).isEqualTo("Test description");
    }

    @Test
    void shouldThrowException_WhenSavingCategoryWithDuplicateNameForSameUser() {
        UserId userId = new UserId(UUID.randomUUID());

        TransactionCategory category1 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Duplicate Name"))
                .build();
        transactionCategoryService.save(category1);

        TransactionCategory category2 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Duplicate Name"))
                .build();

        assertThatThrownBy(() -> transactionCategoryService.save(category2))
                .isInstanceOf(NameInUseException.class);
    }

    @Test
    void shouldAllowSameCategoryNameForDifferentUsers() {
        UserId user1 = new UserId(UUID.randomUUID());
        UserId user2 = new UserId(UUID.randomUUID());

        TransactionCategory category1 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(user1)
                .name(new Name("Same Name"))
                .build();

        TransactionCategory category2 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(user2)
                .name(new Name("Same Name"))
                .build();

        assertThatCode(() -> {
            transactionCategoryService.save(category1);
            transactionCategoryService.save(category2);
        }).doesNotThrowAnyException();

        List<TransactionCategory> categories = transactionCategoryService.findAll();
        assertThat(categories).hasSize(2);
    }

    @Test
    void shouldFindCategoryById() {
        TransactionCategory category = TransactionCategoryTestBuilder.existingTransactionCategory()
                .name(new Name("Findable Category"))
                .build();
        TransactionCategory savedCategory = transactionCategoryRepository.save(category);

        Optional<TransactionCategory> found = transactionCategoryService.findById(savedCategory.getId().getValue());

        assertThat(found).isPresent();
        assertThat(found.get().getName().value()).isEqualTo("Findable Category");
        assertThat(found.get().getId()).isEqualTo(savedCategory.getId());
    }

    @Test
    void shouldReturnEmptyOptional_WhenCategoryNotFound() {
        UUID nonExistentId = UUID.randomUUID();

        Optional<TransactionCategory> found = transactionCategoryService.findById(nonExistentId);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindAllCategories() {
        UserId userId = new UserId(UUID.randomUUID());

        TransactionCategory category1 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 1"))
                .build();
        TransactionCategory category2 = TransactionCategoryTestBuilder.existingTransactionCategory()
                .idUser(userId)
                .name(new Name("Category 2"))
                .build();

        transactionCategoryService.save(category1);
        transactionCategoryService.save(category2);

        List<TransactionCategory> categories = transactionCategoryService.findAll();

        assertThat(categories).hasSize(2);
        assertThat(categories)
                .extracting(cat -> cat.getName().value())
                .containsExactlyInAnyOrder("Category 1", "Category 2");
    }

    @Test
    void shouldDeleteCategoryById() {
        TransactionCategory category = TransactionCategoryTestBuilder.existingTransactionCategory()
                .name(new Name("To Be Deleted"))
                .build();
        TransactionCategory savedCategory = transactionCategoryRepository.save(category);

        assertThat(transactionCategoryService.findAll()).hasSize(1);

        transactionCategoryService.deleteById(savedCategory.getId().getValue());

        assertThat(transactionCategoryService.findAll()).isEmpty();
        assertThat(transactionCategoryService.findById(savedCategory.getId().getValue())).isEmpty();
    }

    @Test
    void shouldUpdateExistingCategory() {
        TransactionCategory originalCategory = TransactionCategoryTestBuilder.existingTransactionCategory()
                .name(new Name("Original Name"))
                .description("Original description")
                .build();
        TransactionCategory savedCategory = transactionCategoryRepository.save(originalCategory);

        TransactionCategory updatedCategory = TransactionCategoryTestBuilder.existingTransactionCategory()
                .id(savedCategory.getId())
                .idUser(savedCategory.getIdUser())
                .name(new Name("Updated Name"))
                .description("Updated description")
                .build();

        assertThatCode(() -> transactionCategoryService.save(updatedCategory))
                .doesNotThrowAnyException();

        Optional<TransactionCategory> found = transactionCategoryService.findById(savedCategory.getId().getValue());
        assertThat(found).isPresent();
        assertThat(found.get().getName().value()).isEqualTo("Updated Name");
        assertThat(found.get().getDescription()).isEqualTo("Updated description");
    }
}