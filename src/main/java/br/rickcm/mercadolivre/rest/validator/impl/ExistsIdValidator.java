package br.rickcm.mercadolivre.rest.validator.impl;

import br.rickcm.mercadolivre.rest.validator.ExistsId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {
    private Class<?> klass;
    private String field;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(ExistsId constraintAnnotation) {
        this.klass = constraintAnnotation.target();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String query = "SELECT c FROM "+klass.getName()+" c WHERE c."+field+" = '"+o+"'";

        List lista = em.createQuery(query).getResultList();

        return !lista.isEmpty();
    }
}
