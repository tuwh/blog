package com.uncub.framework.activitymq;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

public class MessageProductor {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    public MessageProductor(final JmsTemplate jmsTemplate, final Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public void send(final String text) {
        try {
            jmsTemplate.setDefaultDestination(destination);
            jmsTemplate.convertAndSend(text);
            System.out.println("发送消息 : " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
