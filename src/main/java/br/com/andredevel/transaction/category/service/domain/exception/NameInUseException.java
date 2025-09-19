package br.com.andredevel.transaction.category.service.domain.exception;

import static br.com.andredevel.transaction.category.service.domain.exception.ErrorMessage.VALIDATION_NAME_IN_USE;

import java.util.Map;

public class NameInUseException extends DomainException {
    public NameInUseException() {
        super(VALIDATION_NAME_IN_USE);
    }
}
