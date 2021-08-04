package br.rickcm.mercadolivre.service.impl;

import br.rickcm.mercadolivre.model.ImagemProduto;
import br.rickcm.mercadolivre.model.Produto;
import br.rickcm.mercadolivre.repository.ImagemProdutoRepository;
import br.rickcm.mercadolivre.service.UploadService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("default")
public class UploaderAmazon implements UploadService {

    private ImagemProdutoRepository repository;

    public UploaderAmazon(ImagemProdutoRepository repository) {
        this.repository = repository;
    }
    /**
     * @Param imagens lista de MultiPartFile
     * @Return links para imagens que foram feitos upload.
     **/
    public List<ImagemProduto> envia(List<MultipartFile> imagens, Produto produto) {
        List<ImagemProduto> retorno = imagens.stream().map(multipartFile -> {
            return new ImagemProduto(multipartFile.getOriginalFilename()+"-->S3", produto);
        }).collect(Collectors.toList());
        repository.saveAll(retorno);
        return retorno;
    }
}
