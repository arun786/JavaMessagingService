# Java messaging service

Prerequisites

## Download Apache Active MQ Artemis

step 1 : download the ZIP version of the Active MQ

    https://activemq.apache.org/components/artemis/download/

step 2 : unzip the folder and go to bin/artemis

![folder structure](https://github.com/arun786/SpringJMS/blob/main/src/images/activemq%20folder%20structure.png)

step 3 : create a broker (artemisBroker)

![create a broker](https://github.com/arun786/SpringJMS/blob/main/src/images/broker.png)

![broker folder structure](https://github.com/arun786/SpringJMS/blob/main/src/images/artmeis%20broker%20folder%20structure.png)

step 4 : Once broker is created, go to the folder of artemisBroker/bin

![start a broker](https://github.com/arun786/SpringJMS/blob/main/src/images/artemisServer.png)

run the below command

    ./artemis run

the server will start on port 8161

step 5 : when you go to localhost:1861

![server](https://github.com/arun786/SpringJMS/blob/main/src/images/screenshot%20of%20server.png)

    username and password will be the same as provided while creating a broker.

# Create a gradle Project

add the below dependency

    implementation 'org.apache.activemq:artemis-jms-client-all:2.17.0'

# JNDI Properties

create a jndi.properties and add below properties

    1. java.naming.factory.initial=org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory

this is the way to get into initial context of jndi

    2. connectionFactory.ConnectionFactory=tcp://localhost:61616

this is the default location where the jndi server runs.

    3. queue.sd=noc

Name of the queue which will be dynamically created

# First program of producer and consumer where message is dropped in the queue by producer and read by the consumer

        package com.zrun.jms;

        import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
        
        import javax.jms.*;
        import javax.naming.InitialContext;
        import javax.naming.NamingException;


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


## Util class for creating Queue, Producer and Consumer

            package com.zrun.jms.util;
            
            import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
            
            import javax.jms.JMSConsumer;
            import javax.jms.JMSContext;
            import javax.jms.JMSProducer;
            import javax.jms.Queue;
            import javax.naming.InitialContext;
            import javax.naming.NamingException;
                    
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

## use of util class


            package com.zrun.jms.procomsume;

            import com.zrun.jms.util.QueueJms;
            
            import javax.jms.JMSConsumer;
            import javax.jms.JMSProducer;
            import javax.jms.Queue;
            import javax.naming.NamingException;
            
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



    
    
    
