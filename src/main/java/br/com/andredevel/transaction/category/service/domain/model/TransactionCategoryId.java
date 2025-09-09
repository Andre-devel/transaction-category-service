package br.com.andredevel.transaction.category.service.domain.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Embeddable
@EqualsAndHashCode
public class TransactionCategoryId implements Serializable {
    private UUID value;

    public TransactionCategoryId() {
        this(UUID.randomUUID());
    }
    
    public TransactionCategoryId(UUID value) {
        this.value = value;
    }

    public TransactionCategoryId(String value) {
        this.value = UUID.fromString(value);
    }

   

    @Override
    public String toString() {
        return value.toString();
    }
}
