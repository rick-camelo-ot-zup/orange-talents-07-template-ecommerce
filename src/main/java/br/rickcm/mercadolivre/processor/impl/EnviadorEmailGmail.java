package br.rickcm.mercadolivre.processor.impl;

import br.rickcm.mercadolivre.processor.EnviadorEmail;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class EnviadorEmailGmail implements EnviadorEmail {
    @Override
    public void envia(String email, String mensagem) {
        System.out.println("ENVIANDO EMAIL PARA: <"+email+"> com a mensagem: <"+mensagem+">");
    }
}
