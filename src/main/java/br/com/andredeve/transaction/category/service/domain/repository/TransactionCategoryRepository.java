package br.com.andredeve.transaction.category.service.domain.repository;

import br.com.andredeve.transaction.category.service.domain.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, UUID> {
}
