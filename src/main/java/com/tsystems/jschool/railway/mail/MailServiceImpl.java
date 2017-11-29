package com.tsystems.jschool.railway.mail;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Passenger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService{

    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final FreeMarkerConfig freemarkerConfiguration;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, FreeMarkerConfig freemarkerConfiguration) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public void sendBuyTicketEmail(Passenger passenger, SuitableTripDto ticket) throws ServiceException {
        sendEmail(getMessagePreparator(passenger, ticket));
    }

    private void sendEmail(MimeMessagePreparator messagePreparator) throws ServiceException {
        try {
            mailSender.send(messagePreparator);
        }
        catch (MailException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.EMAIL_EXCEPTION);
        }

    }

    private MimeMessagePreparator getMessagePreparator(Passenger passenger, SuitableTripDto ticket) {
        return new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                helper.setSubject("Your ticket at Railway");
                helper.setFrom("noreply.railway@gmail.com");
                helper.setTo(passenger.getUserInfo().getEmail());

                Map<String, Object> model = new HashMap<>();
                model.put("ticket", ticket);
                model.put("passenger", passenger);

                String template = "templateTicket.html";
                helper.setText(getTemplateContent(template, model), true);
            }
        };
    }

    public String getTemplateContent(String template, Map<String, Object> model) throws ServiceException {
        StringBuilder content = new StringBuilder();
        try{
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getConfiguration().getTemplate(template), model));
            return content.toString();
        } catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.EMAIL_EXCEPTION);
        }
    }
}
