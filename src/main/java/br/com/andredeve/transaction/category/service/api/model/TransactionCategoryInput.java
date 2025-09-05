package br.com.andredeve.transaction.category.service.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TransactionCategoryInput {
    private UUID id;
    private UUID idUser;

    private String name;
    private String description;
}
