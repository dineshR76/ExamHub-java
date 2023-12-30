package com.ExamHub.ExamHub.utils.helpers;

import com.ExamHub.ExamHub.dto.examRequestDto.MailRequest;
import com.ExamHub.ExamHub.utils.constants.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class MailUtils {

    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final String getEmailFrom;
    @Autowired
    private final TemplateEngine templateEngine;

    @Autowired
    public MailUtils(JavaMailSender mailSender,
                     @Value("${spring.mail.username}") String getEmailFrom,
                     TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.getEmailFrom = getEmailFrom;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(MailRequest mailRequest) {

        SimpleMailMessage SimpleMailMessage = new SimpleMailMessage();

        SimpleMailMessage.setFrom(getEmailFrom);
        SimpleMailMessage.setTo(mailRequest.getTo());
        SimpleMailMessage.setSubject(mailRequest.getSubject());
        SimpleMailMessage.setText(mailRequest.getMessage());
        try {
            this.mailSender.send(SimpleMailMessage);
        } catch (MailSendException e) {
            throw new MailSendException(MessageConstant.Mail_SENDING_FAILED);
        } catch (MailAuthenticationException e) {
            throw new MailSendException(MessageConstant.MAIL_AUTHENTICATION_FAILED);
        }
    }

}
