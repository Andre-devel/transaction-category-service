package br.com.andredevel.transaction.category.service.api.controller;

import br.com.andredevel.transaction.category.service.api.model.TransactionCategoryInput;
import br.com.andredevel.transaction.category.service.domain.model.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.TransactionCategoryId;
import br.com.andredevel.transaction.category.service.domain.model.UserId;
import br.com.andredevel.transaction.category.service.domain.service.TransactionCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction-categories")
public class TransactionCategoryController {

    private final TransactionCategoryService transactionCategoryService;

    public TransactionCategoryController(TransactionCategoryService transactionCategoryService) {
        this.transactionCategoryService = transactionCategoryService;
    }

    @GetMapping
    public List<TransactionCategory> getAllTransactionCategories() {
        return transactionCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionCategory> getTransactionCategoryById(@PathVariable UUID id) {
        return transactionCategoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionCategory createTransactionCategory(@RequestBody TransactionCategoryInput transactionCategory) {
        
        TransactionCategory newCategory = TransactionCategory.builder()
                .id(new TransactionCategoryId())
                .name(transactionCategory.getName())
                .description(transactionCategory.getDescription())
                .idUser(new UserId(transactionCategory.getIdUser()))
                .build();
        
        return transactionCategoryService.save(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionCategory> updateTransactionCategory(@PathVariable UUID id, @RequestBody TransactionCategory transactionCategoryDetails) {
        TransactionCategory updatedCategory = TransactionCategory.builder()
                .id(new TransactionCategoryId(id))
                .name(transactionCategoryDetails.getName())
                .description(transactionCategoryDetails.getDescription())
                .build();
        return ResponseEntity.ok(transactionCategoryService.save(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionCategory(@PathVariable UUID id) {
        return transactionCategoryService.findById(id)
                .map(transactionCategory -> {
                    transactionCategoryService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
