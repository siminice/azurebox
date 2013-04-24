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
package org.vbossica.azurebox.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.vbossica.azurebox.gradle.tasks.BlobExplorerTask
import org.vbossica.azurebox.gradle.tasks.BlobUploaderTask

/**
 * @author vladimir
 */
class AzureboxPlugin implements Plugin<Project> {

  def void apply(Project project) {
    project.convention.plugins.azurebox = new AzureboxPluginConvention()

    BlobUploaderTask uploaderTask = project.getTasks().add("uploadToBlob", BlobUploaderTask.class)
    uploaderTask.setGroup('Azurebox')
    uploaderTask.setDescription('Upload file to a blob.')

    BlobExplorerTask explorerTask = project.getTasks().add("exploreBlob", BlobExplorerTask.class)
    explorerTask.setGroup('Azurebox')
    explorerTask.setDescription('List the content of a blob.')
  }

}
