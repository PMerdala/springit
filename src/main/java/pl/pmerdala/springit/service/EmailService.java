package pl.pmerdala.springit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import pl.pmerdala.springit.domain.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@Slf4j
public class EmailService {
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final static String BASE_URL="http://localhost:8080";

    public EmailService(JavaMailSender javaMailSender,SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to,String subject,String content,boolean isMultiPart,boolean isHtml){
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
            message.setTo(to);
            message.setFrom("noreply@springit.com");
            message.setSubject(subject);
            message.setText(content,isHtml);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.warn("Email could not be send to user {} : {}", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String subject){
        Locale locale = Locale.getDefault();
        Context context = new Context(locale);
        context.setVariable("user",user);
        context.setVariable("baseURL",BASE_URL);
        String content = templateEngine.process(templateName,context);
        sendEmail(user.getEmail(),subject,content,false,true);
    }

    @Async
    public void sendActivationEmail(User user){
        log.info("Send activation email to {}",user.getEmail());
        sendEmailFromTemplate(user,"email/activation","Springit user activation");
    }

    @Async
    public void sendWelcomeEmail(User user){
        log.info("Send welcome email to {}",user.getEmail());
        sendEmailFromTemplate(user,"email/welcome","Welcome new Springit user");
    }
}
