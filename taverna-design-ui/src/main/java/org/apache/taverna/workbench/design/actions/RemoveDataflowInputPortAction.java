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
package org.apache.taverna.workbench.design.actions;

import static org.apache.taverna.workbench.icons.WorkbenchIcons.deleteIcon;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.taverna.workbench.edits.CompoundEdit;
import org.apache.taverna.workbench.edits.Edit;
import org.apache.taverna.workbench.edits.EditException;
import org.apache.taverna.workbench.edits.EditManager;
import org.apache.taverna.workbench.selection.SelectionManager;
import org.apache.taverna.workflow.edits.RemoveDataLinkEdit;
import org.apache.taverna.workflow.edits.RemoveWorkflowInputPortEdit;

import org.apache.log4j.Logger;

import org.apache.taverna.scufl2.api.core.DataLink;
import org.apache.taverna.scufl2.api.core.Workflow;
import org.apache.taverna.scufl2.api.port.InputWorkflowPort;

/**
 * Action for removing an input port from the dataflow.
 * 
 * @author David Withers
 */
@SuppressWarnings("serial")
public class RemoveDataflowInputPortAction extends DataflowEditAction {
	private static Logger logger = Logger
			.getLogger(RemoveDataflowInputPortAction.class);

	private InputWorkflowPort port;

	public RemoveDataflowInputPortAction(Workflow dataflow,
			InputWorkflowPort port, Component component,
			EditManager editManager, SelectionManager selectionManager) {
		super(dataflow, component, editManager, selectionManager);
		this.port = port;
		putValue(SMALL_ICON, deleteIcon);
		putValue(NAME, "Delete workflow input port");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			dataflowSelectionModel.removeSelection(port);
			List<DataLink> datalinks = scufl2Tools.datalinksFrom(port);
			if (datalinks.isEmpty())
				editManager.doDataflowEdit(dataflow.getParent(),
						new RemoveWorkflowInputPortEdit(dataflow, port));
			else {
				List<Edit<?>> editList = new ArrayList<>();
				for (DataLink datalink : datalinks)
					editList.add(new RemoveDataLinkEdit(dataflow, datalink));
				editList.add(new RemoveWorkflowInputPortEdit(dataflow, port));
				editManager.doDataflowEdit(dataflow.getParent(),
						new CompoundEdit(editList));
			}
		} catch (EditException e1) {
			logger.debug("Delete workflow input port failed", e1);
		}
	}
}
