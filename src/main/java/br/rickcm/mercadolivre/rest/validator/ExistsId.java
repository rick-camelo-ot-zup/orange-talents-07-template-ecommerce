package br.rickcm.mercadolivre.rest.validator;

import br.rickcm.mercadolivre.rest.validator.impl.ExistsIdOrNullValidator;
import br.rickcm.mercadolivre.rest.validator.impl.ExistsIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsIdValidator.class)
public @interface ExistsId {
    String message() default "Recurso nao encontrado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value() default 1;

    Class<?> target();

    String field();
}
