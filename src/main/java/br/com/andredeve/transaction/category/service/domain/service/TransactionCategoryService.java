package br.com.andredeve.transaction.category.service.domain.service;

import br.com.andredeve.transaction.category.service.domain.model.TransactionCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionCategoryService {

    List<TransactionCategory> findAll();

    Optional<TransactionCategory> findById(UUID id);

    TransactionCategory save(TransactionCategory transactionCategory);

    void deleteById(UUID id);
}
