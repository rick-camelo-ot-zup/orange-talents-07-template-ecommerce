package br.rickcm.mercadolivre.rest.validator.impl;

import br.rickcm.mercadolivre.rest.validator.ListSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class ListSizeValidator implements ConstraintValidator<ListSize, Collection> {

    private int min;
    private int max;

    @Override
    public void initialize(ListSize constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        return collection.size() >= min && collection.size() <= max;
    }
}
