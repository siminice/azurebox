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
package org.vbossica.azurebox.blob;

import com.microsoft.windowsazure.services.blob.client.CloudBlobClient;
import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author vladimir
 */
public class CloudBlobClientFactoryBean implements FactoryBean<CloudBlobClient>, InitializingBean {

  private CloudStorageAccount storageAccount;

  public void setStorageAccount(CloudStorageAccount storageAccount) {
    this.storageAccount = storageAccount;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(storageAccount, "storageAccount must be set!");
  }

  @Override
  public CloudBlobClient getObject() throws Exception {
    return storageAccount.createCloudBlobClient();
  }

  @Override
  public Class<?> getObjectType() {
    return CloudBlobClient.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
