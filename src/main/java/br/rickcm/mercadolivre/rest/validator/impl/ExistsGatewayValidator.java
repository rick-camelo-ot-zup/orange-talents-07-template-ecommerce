package br.rickcm.mercadolivre.rest.validator.impl;

import br.rickcm.mercadolivre.enums.GatewayPagamento;
import br.rickcm.mercadolivre.rest.validator.ExistsGateway;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExistsGatewayValidator implements ConstraintValidator<ExistsGateway, Integer> {
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {

        List<GatewayPagamento> collect = Arrays.stream(GatewayPagamento.values()).filter(gatewayPagamento ->
                gatewayPagamento.getIdentificador() == integer
        ).collect(Collectors.toList());
        if(collect.size() == 0){
            return false;
        }
        return true;
    }
}
