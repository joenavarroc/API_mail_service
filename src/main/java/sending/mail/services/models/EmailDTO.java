package sending.mail.services.models;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDTO {
    @Email
    private String destinatario;
    @NotBlank
    private String asunto;
    private String mensaje;
    private List<MultipartFile> archivos;
}