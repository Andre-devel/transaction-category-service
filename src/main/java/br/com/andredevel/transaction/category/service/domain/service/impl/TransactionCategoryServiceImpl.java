package br.com.andredevel.transaction.category.service.domain.service.impl;

import br.com.andredevel.transaction.category.service.domain.model.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.repository.TransactionCategoryRepository;
import br.com.andredevel.transaction.category.service.domain.service.TransactionCategoryService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {
    
    private final TransactionCategoryRepository transactionCategoryRepository;
    private final EntityManager entityManager;
    
    public TransactionCategoryServiceImpl(TransactionCategoryRepository transactionCategoryRepository, EntityManager entityManager) {
        this.transactionCategoryRepository = transactionCategoryRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<TransactionCategory> findAll() {
        return transactionCategoryRepository.findAll();
    }

    @Override
    public Optional<TransactionCategory> findById(UUID id) {
        return transactionCategoryRepository.findById(id);
    }

    @Override
    public TransactionCategory save(TransactionCategory transactionCategory) {
        UUID categoryId = transactionCategory.getId().getValue();
        
        if (categoryId != null) {
            transactionCategoryRepository.findById(categoryId).ifPresentOrElse(
                    existingCategory -> update(existingCategory, transactionCategory),
                    () -> insert(transactionCategory));
        } else {
            insert(transactionCategory);
        }
        
        return transactionCategory;
    }
    
    private void insert(TransactionCategory transactionCategory) {
        transactionCategory = transactionCategoryRepository.saveAndFlush(transactionCategory);
    }
    
    private void update(TransactionCategory existingCategory, TransactionCategory newCategory) {
        TransactionCategory persistenceCategory = merge(existingCategory, newCategory);
        entityManager.detach(persistenceCategory);
        newCategory = transactionCategoryRepository.saveAndFlush(persistenceCategory);
    }   
    
    private TransactionCategory merge(TransactionCategory existingCategory, TransactionCategory newCategory) {
        existingCategory.setName(newCategory.getName());
        existingCategory.setDescription(newCategory.getDescription());
        return existingCategory;
    }   

    @Override
    public void deleteById(UUID id) {
        transactionCategoryRepository.deleteById(id);
    }
}
