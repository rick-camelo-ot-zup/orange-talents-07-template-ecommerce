package br.rickcm.mercadolivre.rest.validator.impl;

import br.rickcm.mercadolivre.rest.validator.UniqueValue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {
    private Class<?> klass;
    private String field;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.klass = constraintAnnotation.target();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String query = "SELECT c FROM "+klass.getName()+" c WHERE c."+field+" = '"+o+"'";

        List lista = em.createQuery(query).getResultList();

        return lista.isEmpty();
    }
}
