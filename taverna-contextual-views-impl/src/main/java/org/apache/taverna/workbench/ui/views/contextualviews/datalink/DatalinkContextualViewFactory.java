/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.taverna.workbench.ui.views.contextualviews.datalink;

import java.util.Arrays;
import java.util.List;

import org.apache.taverna.workbench.file.FileManager;
import org.apache.taverna.workbench.ui.views.contextualviews.ContextualView;
import org.apache.taverna.workbench.ui.views.contextualviews.activity.ContextualViewFactory;
import org.apache.taverna.workflowmodel.Datalink;
import org.apache.taverna.scufl2.api.core.DataLink;

/**
 * A factory of contextual views for dataflow's datalinks.
 *
 * @author Alex Nenadic
 */
public class DatalinkContextualViewFactory implements
		ContextualViewFactory<DataLink> {
	private FileManager fileManager;

	@Override
	public boolean canHandle(Object object) {
		return object instanceof Datalink;
	}

	@Override
	public List<ContextualView> getViews(DataLink datalink) {
		return Arrays.asList(new ContextualView[] {
				new DatalinkContextualView(datalink, fileManager)});
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
}
