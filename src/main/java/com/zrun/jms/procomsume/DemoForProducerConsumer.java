package com.zrun.jms.procomsume;

import com.zrun.jms.util.QueueJms;

import javax.jms.JMSConsumer;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.NamingException;

/**
 * @author arun on 5/31/21
 */
public class DemoForProducerConsumer {

    public static void main(String[] args) throws NamingException {
        QueueJms queueJms = new QueueJms();
        final Queue queue = queueJms.createJMSQueue("sd");

        final JMSProducer producer = queueJms.createProducer();
        //create a message and put in the queue
        producer.send(queue, "No Pain no gain");

        final JMSConsumer consumer = queueJms.createConsumer(queue);
        final String message = consumer.receiveBody(String.class);

        System.out.println("Message received : " + message);
    }
}
