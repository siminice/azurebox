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
package org.vbossica.azurebox.storage;

import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author vladimir
 */
public class CloudStorageAccountFactoryBean implements FactoryBean<CloudStorageAccount>, InitializingBean {

  private String protocol = "http";
  private String account;
  private String accountKey;

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public void setAccountKey(String accountKey) {
    this.accountKey = accountKey;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.hasLength(protocol, "protocol must set set!");
    Assert.hasLength(account, "account must be set");
    Assert.hasLength(accountKey, "accountKey must be set");
  }

  @Override
  public CloudStorageAccount getObject() throws Exception {
    String storageConnectionString = "DefaultEndpointsProtocol=" + protocol + ";AccountName=" + account + ";AccountKey=" + accountKey;

    return CloudStorageAccount.parse(storageConnectionString);
  }

  @Override
  public Class<?> getObjectType() {
    return CloudStorageAccount.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
