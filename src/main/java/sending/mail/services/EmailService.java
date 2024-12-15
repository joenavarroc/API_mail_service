package sending.mail.services;

import jakarta.mail.MessagingException;
import sending.mail.services.models.EmailDTO;

public interface EmailService {
     
    public void sendMail(EmailDTO emailDto) throws MessagingException;
}
