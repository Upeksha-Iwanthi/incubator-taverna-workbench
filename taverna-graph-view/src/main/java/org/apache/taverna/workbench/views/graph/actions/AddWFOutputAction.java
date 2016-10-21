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
package org.apache.taverna.workbench.views.graph.actions;

import static java.awt.event.InputEvent.ALT_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_O;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.apache.taverna.ui.menu.DesignOnlyAction;
import org.apache.taverna.workbench.design.actions.AddDataflowOutputAction;
import org.apache.taverna.workbench.edits.EditManager;
import org.apache.taverna.workbench.icons.WorkbenchIcons;
import org.apache.taverna.workbench.selection.SelectionManager;
import org.apache.taverna.scufl2.api.core.Workflow;

/**
 * An action that adds a workflow output.
 * 
 * @author Alex Nenadic
 * @author Alan R Williams
 */
@SuppressWarnings("serial")
public class AddWFOutputAction extends AbstractAction implements
		DesignOnlyAction {
	private final EditManager editManager;
	private final SelectionManager selectionManager;

	public AddWFOutputAction(EditManager editManager,
			SelectionManager selectionManager) {
		super();
		this.editManager = editManager;
		this.selectionManager = selectionManager;
		putValue(SMALL_ICON, WorkbenchIcons.outputIcon);
		putValue(NAME, "Workflow output port");
		putValue(SHORT_DESCRIPTION, "Workflow output port");
		putValue(ACCELERATOR_KEY,
				getKeyStroke(VK_O, SHIFT_DOWN_MASK | ALT_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Workflow workflow = selectionManager.getSelectedWorkflow();
		new AddDataflowOutputAction(workflow, null, editManager,
				selectionManager).actionPerformed(e);
	}
}
