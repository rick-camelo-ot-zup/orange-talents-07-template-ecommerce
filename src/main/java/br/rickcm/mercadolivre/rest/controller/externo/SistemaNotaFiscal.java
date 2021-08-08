package br.rickcm.mercadolivre.rest.controller.externo;

import br.rickcm.mercadolivre.rest.dto.NotaFiscalRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SistemaNotaFiscal {

    @PostMapping("/endpoint-fake-nota-fiscal")
    public void notaFiscal(@RequestBody NotaFiscalRequest request){
        System.out.println("###### RECEBIDA REQUISIÇÃO DE EMISSÃO DE NOTA FISCAL: "+request.toString());
    }
}
