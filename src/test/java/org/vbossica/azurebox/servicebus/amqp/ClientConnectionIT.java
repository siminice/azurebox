/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vbossica.azurebox.servicebus.amqp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Integration test for validating the connection to Apache Qpid broker.
 *
 * @author vladimir
 */
@ContextConfiguration
public class ClientConnectionIT extends AbstractJUnit4SpringContextTests {

  @Autowired
  private JmsTemplate template;
  @Autowired
  @Qualifier("testQueueDestination")
  private Destination destination;
  @Autowired
  @Qualifier("testQueueDestinationName")
  private String destinationName;

  @Test
  public void send_and_receive() {
    template.send(destinationName, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("hello world");
      }
    });
    Message msg = template.receive(destinationName);
    System.out.println(msg.toString());
  }

  @Test
  public void send_and_receive_with_destination() {
    template.send(destination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage("hello world (2)");
      }
    });
    Message msg = template.receive(destination);
    System.out.println(msg.toString());
  }

}
