package br.rickcm.mercadolivre.service.impl;

import br.rickcm.mercadolivre.model.ImagemProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.repository.ImagemProdutoRepository;
import br.rickcm.mercadolivre.service.UploadService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("dev")
public class UploaderFake implements UploadService {

    private ImagemProdutoRepository repository;

    public UploaderFake(ImagemProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * @Param imagens lista de MultiPartFile
     * @Return links para imagens que foram feitos upload.
     **/
    public List<ImagemProduto> envia(List<MultipartFile> imagens, Produto produto) {
        List<ImagemProduto> retorno = imagens.stream().map(multipartFile -> {
            return new ImagemProduto(multipartFile.getOriginalFilename(), produto);
        }).collect(Collectors.toList());
        retorno = repository.saveAll(retorno);
        return retorno;
    }
}
