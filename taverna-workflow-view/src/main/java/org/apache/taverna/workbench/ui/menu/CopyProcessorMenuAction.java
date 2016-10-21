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
package org.apache.taverna.workbench.ui.menu;

import java.awt.Component;
import java.net.URI;

import javax.swing.Action;

import org.apache.taverna.ui.menu.AbstractContextualMenuAction;
import org.apache.taverna.workbench.ui.actions.CopyProcessorAction;
import org.apache.taverna.scufl2.api.core.Processor;
import org.apache.taverna.scufl2.api.core.Workflow;

public class CopyProcessorMenuAction extends AbstractContextualMenuAction {
	public static final URI editSection = URI
			.create("http://taverna.sf.net/2009/contextMenu/edit");

	public CopyProcessorMenuAction() {
		super(editSection, 20);
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled()
				&& getContextualSelection().getSelection() instanceof Processor;
	}

	@Override
	@SuppressWarnings("unused")
	protected Action createAction() {
		Workflow dataflow = (Workflow) getContextualSelection().getParent();
		Processor processor = (Processor) getContextualSelection()
				.getSelection();
		Component component = getContextualSelection().getRelativeToComponent();
		return new CopyProcessorAction(processor);
	}
}
