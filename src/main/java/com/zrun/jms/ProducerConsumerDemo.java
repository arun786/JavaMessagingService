package com.zrun.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * @author arun on 5/31/21
 */
public class ProducerConsumerDemo {
    public static void main(String[] args) throws NamingException {

        /*
        1. create an initial context.
        2. use the context to look up for the queue
        3. create the connection factory
        4. use the connection factory to create the JMS Context.
        5. use the context to create the producer and consumer.
         */

        InitialContext context = new InitialContext();
        //here the sd is the name of the queue which was defined in the jndi properties
        final Queue queue = (Queue) context.lookup("sd");

        try (
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
                final JMSContext jmsContext = connectionFactory.createContext();
        ) {
            final JMSProducer producer = jmsContext.createProducer();
            producer.send(queue, "This message is sent using jms 2.0");
            System.out.println("Message has been sent");

            final JMSConsumer consumer = jmsContext.createConsumer(queue);
            final String message = consumer.receiveBody(String.class);
            System.out.println("Message received : " + message);
        }
    }
}
