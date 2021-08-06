package br.rickcm.mercadolivre.rest.validator;

import br.rickcm.mercadolivre.rest.validator.impl.ExistsGatewayValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsGatewayValidator.class)
public @interface ExistsGateway {
    String message() default "Gateway inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long value() default 1;
}
