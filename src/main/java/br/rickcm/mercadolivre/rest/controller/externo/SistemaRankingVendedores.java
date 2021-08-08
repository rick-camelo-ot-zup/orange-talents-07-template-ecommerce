package br.rickcm.mercadolivre.rest.controller.externo;

import br.rickcm.mercadolivre.rest.dto.RankingVendedoresRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SistemaRankingVendedores {

    @PostMapping("/endpoint-fake-ranking-vendedores")
    public void atualizaRanking(@RequestBody RankingVendedoresRequest request){
        System.out.println("\n###### RECEBIDA REQUISIÇÃO DE ATUALIZAÇÃO DO RANKING: "+request.toString());
    }
}
