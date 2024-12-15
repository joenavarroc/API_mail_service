package sending.mail.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import sending.mail.services.EmailService;
import sending.mail.services.models.EmailDTO;

@Controller
@RequestMapping
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/formulario")
    public String mostrarFormulario() {
        
        return "formulario";
    }

    @PostMapping(value = "/sendemail", consumes = { "multipart/form-data" })
    public ResponseEntity<String> sendEmail(
            @RequestPart("email") String destinatario,
            @RequestPart("asunto") String asunto,
            @RequestPart("mensaje") String mensaje,
            @RequestPart(value = "file", required = false) List<MultipartFile> archivos) throws MessagingException {
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setDestinatario(destinatario);
            emailDTO.setAsunto(asunto);
            emailDTO.setMensaje(mensaje);
            emailDTO.setArchivos(archivos);

            emailService.sendMail(emailDTO);
            return new ResponseEntity<>("Email enviado correctamente", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
