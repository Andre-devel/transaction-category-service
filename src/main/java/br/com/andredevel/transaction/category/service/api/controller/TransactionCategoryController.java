package br.com.andredevel.transaction.category.service.api.controller;

import br.com.andredevel.transaction.category.service.api.model.TransactionCategoryInput;
import br.com.andredevel.transaction.category.service.domain.model.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.TransactionCategoryId;
import br.com.andredevel.transaction.category.service.domain.model.UserId;
import br.com.andredevel.transaction.category.service.domain.service.TransactionCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return transactionCategoryService.findById(id)
                .map(transactionCategory -> {
                    transactionCategory.setName(transactionCategoryDetails.getName());
                    transactionCategory.setDescription(transactionCategoryDetails.getDescription());
                    transactionCategory.setIdUser(transactionCategoryDetails.getIdUser());
                    return ResponseEntity.ok(transactionCategoryService.save(transactionCategory));
                })
                .orElse(ResponseEntity.notFound().build());
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
