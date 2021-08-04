package br.rickcm.mercadolivre.rest.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ImagensRequest {

    @Size(min = 1, message = "lista de imagens vazia.")
    @NotNull(message = "lista de imagens vazia.")
    List<MultipartFile> imagens;

    public ImagensRequest(@NotNull @Size(min = 1) List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
}
