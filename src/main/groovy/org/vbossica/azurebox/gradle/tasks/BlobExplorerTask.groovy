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

import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.vbossica.azurebox.gradle.AzureboxPluginConvention

/**
 * @author vladimir
 */
class BlobExplorerTask extends DefaultTask {

  String protocol = "http"
  String targetContainer

  @TaskAction
  def process() {
    AzureboxPluginConvention convention = project.convention.plugins.azurebox as AzureboxPluginConvention

    println "explore container '${targetContainer}'"

    def storageConnectionString = "DefaultEndpointsProtocol=${protocol};AccountName=${convention.accountName};AccountKey=${convention.accountKey}"
    def blobClient = CloudStorageAccount.parse(storageConnectionString).createCloudBlobClient()

    def container = blobClient.getContainerReference(targetContainer)
    if (container == null) {
      println "couldn't find container"
      return
    }
    container.listBlobs().each {
      println it.getUri()
    }
  }

}


