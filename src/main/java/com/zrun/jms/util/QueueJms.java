package com.zrun.jms.util;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author arun on 5/31/21
 */
public class QueueJms {

    private final JMSContext jmsContext;

    public QueueJms() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        jmsContext = connectionFactory.createContext();
    }

    /**
     * @param queueName - name of the queue which is defined in jndi.properties
     * @return - Jms Queue
     * @throws NamingException - Exception Naming
     */
    public Queue createJMSQueue(String queueName) throws NamingException {
        InitialContext context = new InitialContext();
        return (Queue) context.lookup(queueName);
    }

    /**
     * it creates a producer
     *
     * @return JMSProducer
     */
    public JMSProducer createProducer() {
        return jmsContext.createProducer();
    }

    /**
     * It creates a Consumer
     *
     * @param queue - queue for which the consumer will listen
     * @return JMSConsumer
     */
    public JMSConsumer createConsumer(Queue queue) {
        return jmsContext.createConsumer(queue);
    }
}
