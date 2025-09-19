package br.com.andredevel.transaction.category.service.domain.repository;

import br.com.andredevel.transaction.category.service.domain.model.entity.TransactionCategory;
import br.com.andredevel.transaction.category.service.domain.model.id.TransactionCategoryId;
import br.com.andredevel.transaction.category.service.domain.model.id.UserId;
import br.com.andredevel.transaction.category.service.domain.model.valueobject.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, TransactionCategoryId> {
    boolean existsByNameAndIdUser(Name name, UserId idUser);
    boolean existsByNameAndIdUserAndIdNot(Name name, UserId idUser, TransactionCategoryId id);
}
