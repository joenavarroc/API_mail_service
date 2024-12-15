package sending.mail.services.impl;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import sending.mail.services.EmailService;
import sending.mail.services.models.EmailDTO;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendMail(EmailDTO emailDto) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDto.getDestinatario());
            helper.setSubject(emailDto.getAsunto());

            // Verifica si hay un mensaje o si debes usar la plantilla
            if (emailDto.getMensaje() != null && !emailDto.getMensaje().isEmpty()) {
                // Enviar solo el texto del mensaje sin plantilla
                helper.setText(emailDto.getMensaje(), false);  // `false` indica texto plano
            } else {
                // Si el mensaje está vacío o nulo, usa la plantilla HTML
                Context context = new Context();
                context.setVariable("mensaje", emailDto.getMensaje());
                String contentHTML = templateEngine.process("formulario", context);
                helper.setText(contentHTML, true);  // `true` indica que es HTML
            }

            // Adjuntar archivos si existen
            List<MultipartFile> archivosAdjuntos = emailDto.getArchivos();
            if (archivosAdjuntos != null && !archivosAdjuntos.isEmpty()) {
                for (MultipartFile archivo : archivosAdjuntos) {
                    if (!archivo.isEmpty()) {
                        helper.addAttachment(archivo.getOriginalFilename(), archivo);
                    }
                }
            }

            // Enviar el mensaje
            javaMailSender.send(message);

        } catch (MessagingException me) {
            throw new RuntimeException("Error al enviar el correo: " + me.getMessage(), me);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
        }
    }
}
