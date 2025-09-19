package br.com.andredevel.transaction.category.service.domain.model.valueobject;

import br.com.andredevel.transaction.category.service.domain.model.validator.FieldValidator;
import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String value) {

    public Name {
        FieldValidator.requireValidName(value);
        value = value.trim();
    }

    @Override
    public String toString() {
        return this.value;
    }
}