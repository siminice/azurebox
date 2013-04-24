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
package org.vbossica.azurebox.gradle.tasks

import com.microsoft.windowsazure.services.blob.client.BlobContainerPermissions
import com.microsoft.windowsazure.services.blob.client.BlobContainerPublicAccessType
import com.microsoft.windowsazure.services.blob.client.CloudBlockBlob
import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.vbossica.azurebox.gradle.AzureboxPluginConvention

/**
 * @author vladimir
 */
class BlobUploaderTask extends DefaultTask {

  protected final Logger logger = LoggerFactory.getLogger(getClass())

  File source

  String protocol = "http"
  String targetContainer
  String targetBlobName
  boolean autoCreate = true
  boolean isContainerPublic = false

  @TaskAction
  def process() {
    AzureboxPluginConvention convention = project.convention.plugins.azurebox as AzureboxPluginConvention

    println "uploading ${source} onto ${targetContainer}"

    def storageConnectionString = "DefaultEndpointsProtocol=${protocol};AccountName=${convention.accountName};AccountKey=${convention.accountKey}"
    def blobClient = CloudStorageAccount.parse(storageConnectionString).createCloudBlobClient()

    def container = blobClient.getContainerReference(targetContainer)
    if (autoCreate) {
      container.createIfNotExist()
    }
    if (isContainerPublic) {
      def containerPermissions = new BlobContainerPermissions()
      containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER)
      container.uploadPermissions(containerPermissions)
    }

    def blob = container.getBlockBlobReference(targetBlobName)
    blob.upload(new FileInputStream(source), source.length())
  }

}