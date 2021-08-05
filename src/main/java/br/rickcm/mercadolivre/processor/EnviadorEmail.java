package br.rickcm.mercadolivre.processor;

import br.rickcm.mercadolivre.model.PerguntaProduto;

public interface EnviadorEmail {
    void envia(String email, String mensagem);
}
