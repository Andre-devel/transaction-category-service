package br.com.andredevel.transaction.category.service.domain.model.validator;

import java.util.Objects;

public final class FieldValidator {
    private FieldValidator() {
    }
    
    public static void requireValidName(String name) {
        requireValidName(name, "Name must be between 3 and 100 characters long");
    }

    private static void requireValidName(String name, String invalidName) {
        requireNotBlank(name, invalidName);
        
        if (name.length() < 3 || name.length() > 100) {
            throw new IllegalArgumentException(invalidName);
        }       
    }

    public static void requireNotBlank(String value, String errorMessage) {
        Objects.requireNonNull(value, errorMessage);

        if (value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
