package br.com.andredeve.transaction.category.service.domain.service.impl;

import br.com.andredeve.transaction.category.service.domain.model.TransactionCategory;
import br.com.andredeve.transaction.category.service.domain.repository.TransactionCategoryRepository;
import br.com.andredeve.transaction.category.service.domain.service.TransactionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionCategoryServiceImpl implements TransactionCategoryService {
    
    private final TransactionCategoryRepository transactionCategoryRepository;
    
    public TransactionCategoryServiceImpl(TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionCategoryRepository = transactionCategoryRepository;
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
        return transactionCategoryRepository.save(transactionCategory);
    }

    @Override
    public void deleteById(UUID id) {
        transactionCategoryRepository.deleteById(id);
    }
}
