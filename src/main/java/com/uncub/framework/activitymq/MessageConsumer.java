package com.uncub.framework.activitymq;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

public class MessageConsumer implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                System.out.println("接收到消息: " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
