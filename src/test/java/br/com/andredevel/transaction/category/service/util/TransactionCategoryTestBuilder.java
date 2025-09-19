package br.com.andredevel.transaction.category.service.util;

import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory.TransactionCategoryBuilder;
import br.com.andredevel.transaction.category.service.domain.model.id.TransactionCategoryId;
import br.com.andredevel.transaction.category.service.domain.model.id.UserId;
import br.com.andredevel.transaction.category.service.domain.model.valueobject.Name;

import java.util.UUID;

public class TransactionCategoryTestBuilder {

    private TransactionCategoryTestBuilder() {}

    public static TransactionCategoryBuilder existingTransactionCategory() {
        return TransactionCategory.builder()
                .id(new TransactionCategoryId(UUID.randomUUID()))
                .idUser(new UserId(UUID.randomUUID()))
                .name(new Name("Default Category"))
                .description("Default description");
    }
}