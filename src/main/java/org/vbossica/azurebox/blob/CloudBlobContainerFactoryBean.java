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

import com.microsoft.windowsazure.services.blob.client.BlobContainerPermissions;
import com.microsoft.windowsazure.services.blob.client.BlobContainerPublicAccessType;
import com.microsoft.windowsazure.services.blob.client.CloudBlobClient;
import com.microsoft.windowsazure.services.blob.client.CloudBlobContainer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author vladimir
 */
public class CloudBlobContainerFactoryBean implements FactoryBean<CloudBlobContainer>, InitializingBean {

  private CloudBlobClient blobClient;
  private String address;
  private boolean autoCreate = true;
  private boolean publicAccess = false;

  public void setBlobClient(CloudBlobClient blobClient) {
    this.blobClient = blobClient;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setAutoCreate(boolean autoCreate) {
    this.autoCreate = autoCreate;
  }

  public void setPublic(boolean publicAccess) {
    this.publicAccess = publicAccess;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(blobClient, "blobClient must be set!");
    Assert.hasLength(address, "address must be set!");
  }

  @Override
  public CloudBlobContainer getObject() throws Exception {
    CloudBlobContainer container = blobClient.getContainerReference(address);
    if (autoCreate) {
      container.createIfNotExist();
    }
    if (publicAccess) {
      BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
      containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
      container.uploadPermissions(containerPermissions);
    }
    return container;
  }

  @Override
  public Class<?> getObjectType() {
    return CloudBlobContainer.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
