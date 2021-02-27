package com.reddit.service;

import com.reddit.exceptions.MailRedditException;
import com.reddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendEmail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparetor =  mimeMessage ->{
        MimeMessageHelper messageHelper =new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("4d1f718043-5b180d@inbox.mailtrap.io");
        messageHelper.setTo(notificationEmail.getRecipient());
        messageHelper.setSubject(notificationEmail.getSubject());
        messageHelper.setText(notificationEmail.getBody(),true);
        };
        try{

mailSender.send(messagePreparetor);
log.info("Activation Email send");
        }catch(MailException e){
            log.error("Exception ==>" +e.toString());
            throw  new MailRedditException("Error in send email class ==>" + e.toString());
        }

    }
}
