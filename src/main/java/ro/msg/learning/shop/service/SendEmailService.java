package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.domain.Order;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SendEmailService {
    @Value("${mail.template}")
    private String emailTemplateFile;
    @Value("${mail.sentFrom}")
    private String mailSender;

    @Autowired
    private JavaMailSender emailSender;

    public void sendConfirmationMail(Order order) {
        sendSimpleMessage(
                order.getCustomer().getEmailAddress(),
                String.format("confirmation email for order no. %d", order.getId()),
                generateBody(order));
    }

    public void sendSimpleMessage(String to, String subject, String text) {
//TODO: switch between SimpleMail and MimeMessage based on file extension or something

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailSender);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);

        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailSender);
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String generateBody(Object templateContext) {
        try {
            var file = Path.of(new ClassPathResource(emailTemplateFile).getFile().getPath());
            var template = new String(Files.readAllBytes(file));
            var expression = new SpelExpressionParser().parseExpression(template,
                    new TemplateParserContext());
            var context = new StandardEvaluationContext(templateContext);
            return (String) expression.getValue(context);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("failed generating mail from template");
        }
    }
}
