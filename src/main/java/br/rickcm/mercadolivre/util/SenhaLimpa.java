package br.rickcm.mercadolivre.util;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class SenhaLimpa {

    private String senhaLimpa;

    public SenhaLimpa(@NotBlank @Length(min = 6) String senhaLimpa) {

        Assert.hasLength(senhaLimpa, "senha não pode ser em branco");
        Assert.isTrue(senhaLimpa.length() >= 6, "senha não pode ser menor que 6 caracteres.");

        this.senhaLimpa = senhaLimpa;
    }

    public String encripta(){
        return new String(Base64.getEncoder().encodeToString(senhaLimpa.getBytes()));
    }
}
