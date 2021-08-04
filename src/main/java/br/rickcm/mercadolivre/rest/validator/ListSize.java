package br.rickcm.mercadolivre.rest.validator;

import br.rickcm.mercadolivre.rest.validator.impl.ListSizeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ListSizeValidator.class)
public @interface ListSize{

    String message() default "lista com tamanho não permitido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default 1000;

}
