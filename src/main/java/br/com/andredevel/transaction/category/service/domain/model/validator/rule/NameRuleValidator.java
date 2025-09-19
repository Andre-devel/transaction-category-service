package br.com.andredevel.transaction.category.service.domain.model.validator.rule;

import br.com.andredevel.transaction.category.service.domain.exception.NameInUseException;
import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.repository.TransactionCategoryRepository;

public final class NameRuleValidator {
    
    private NameRuleValidator() {
    }   
    
    public static void validateNameUniqueForUser(TransactionCategoryRepository repository, TransactionCategory transactionCategory) {
        boolean existsByNameAndIdUserAndIdNot = repository.existsByNameAndIdUserAndIdNot(
            transactionCategory.getName(),
            transactionCategory.getIdUser(),
            transactionCategory.getId()
        );
        if (existsByNameAndIdUserAndIdNot) {
            throw new NameInUseException();
        }
    }
    
}
