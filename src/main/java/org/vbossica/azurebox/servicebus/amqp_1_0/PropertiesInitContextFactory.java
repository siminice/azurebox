/*
 * Copyright 2012-2013 the original author or authors.
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
package org.vbossica.azurebox.servicebus.amqp_1_0;

import org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Connection factory extending the standard QPid {@link PropertiesFileInitialContextFactory} and letting the JNDI
 * configuration file be located on the classpath or the configuration values be directly defined in the JNDI
 * environment.
 *
 * @author vladimir
 */
public class PropertiesInitContextFactory extends PropertiesFileInitialContextFactory {

  @Override
  public Context getInitialContext(Hashtable environment) throws NamingException {
    try {
      if (environment.containsKey(Context.PROVIDER_URL)) {
        String fileName = (String) environment.get(Context.PROVIDER_URL);
        File file = new File(fileName);
        if (!file.isAbsolute()) {
          Properties p = new Properties();
          p.load(this.getClass().getClassLoader().getResourceAsStream(fileName));

          for (Map.Entry entry : p.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            environment.put(key, value);
            if (System.getProperty(key) == null) {
              System.setProperty(key, value);
            }
          }
          return processEnvironment(environment);
        }
    } else {
      for (Object entry : environment.entrySet()) {
        Map.Entry<String, String> tmp = (Map.Entry<String, String>) entry;
        if (System.getProperty(tmp.getKey()) == null) {
          System.setProperty(tmp.getKey(), tmp.getValue());
        }
      }
      return processEnvironment(environment);
    }
    } catch (IOException ex) {
      NamingException ne = new NamingException("Exception while initializing context factory");
      ne.setRootCause(ex);
    }
    return super.getInitialContext(environment);
  }

  private Context processEnvironment(Hashtable environment) throws NamingException {
    Map data = new ConcurrentHashMap();
    try {
      createConnectionFactories(data, environment);
    } catch (MalformedURLException e) {
      NamingException ne = new NamingException();
      ne.setRootCause(e);
      throw ne;
    }
    createDestinations(data, environment);
    createQueues(data, environment);
    createTopics(data, environment);

    return createContext(data, environment);
  }

}
