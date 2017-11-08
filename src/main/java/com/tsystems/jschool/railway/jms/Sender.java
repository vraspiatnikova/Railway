package com.tsystems.jschool.railway.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;

@Component
public class Sender {

   private final JmsTemplate jmsTemplate;

    @Autowired
    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String msg) throws JMSException{
            jmsTemplate.setTimeToLive(180000);
            jmsTemplate.send(session -> session.createTextMessage(msg));
    }
}
