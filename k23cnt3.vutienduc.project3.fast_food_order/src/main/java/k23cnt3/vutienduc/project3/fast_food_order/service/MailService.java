package k23cnt3.vutienduc.project3.fast_food_order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void guiLienHe(String ten, String email, String noiDung) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("code1den9@gmail.com"); // mail admin nhận
        message.setSubject("📩 Liên hệ từ khách hàng");

        message.setText(
                "Tên: " + ten + "\n" +
                        "Email: " + email + "\n\n" +
                        "Nội dung:\n" + noiDung
        );

        mailSender.send(message);
    }
}
