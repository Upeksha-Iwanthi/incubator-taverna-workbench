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
package org.apache.taverna.workbench.ui.actions;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_Y;
import static javax.swing.KeyStroke.getKeyStroke;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.copyIcon;
import static org.apache.taverna.workbench.ui.workflowview.WorkflowView.copyProcessor;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;

import org.apache.taverna.lang.observer.Observable;
import org.apache.taverna.lang.observer.Observer;
import org.apache.taverna.lang.observer.SwingAwareObserver;
import org.apache.taverna.ui.menu.DesignOnlyAction;
import org.apache.taverna.workbench.selection.DataflowSelectionModel;
import org.apache.taverna.workbench.selection.SelectionManager;
import org.apache.taverna.workbench.selection.events.DataflowSelectionMessage;
import org.apache.taverna.workbench.selection.events.SelectionManagerEvent;
import org.apache.taverna.workbench.selection.events.WorkflowBundleSelectionEvent;
import org.apache.taverna.scufl2.api.container.WorkflowBundle;
import org.apache.taverna.scufl2.api.core.Processor;

/**
 * An action that copies the selected graph component.
 * 
 * @author Alan R Williams
 * 
 */
@SuppressWarnings("serial")
public class CopyGraphComponentAction extends AbstractAction implements
		DesignOnlyAction {
	/** Current workflow's selection model event observer. */
	private Observer<DataflowSelectionMessage> workflowSelectionObserver = new DataflowSelectionObserver();
	private final SelectionManager selectionManager;

	public CopyGraphComponentAction(SelectionManager selectionManager) {
		super("Copy", copyIcon);
		this.selectionManager = selectionManager;
		putValue(SHORT_DESCRIPTION, "Copy selected component");
		putValue(MNEMONIC_KEY, VK_Y);

		putValue(
				ACCELERATOR_KEY,
				getKeyStroke(VK_C, getDefaultToolkit().getMenuShortcutKeyMask()));
		setEnabled(false);

		selectionManager.addObserver(new SelectionManagerObserver());
	}

	/**
	 * Check if action should be enabled or disabled and update its status.
	 */
	public void updateStatus() {
		WorkflowBundle workflowBundle = selectionManager
				.getSelectedWorkflowBundle();
		DataflowSelectionModel selectionModel = selectionManager
				.getDataflowSelectionModel(workflowBundle);

		// List of all selected objects in the graph view
		Set<Object> selection = selectionModel.getSelection();

		if (selection.isEmpty())
			setEnabled(false);
		else {
			/*
			 * Take the first selected item - we only support single selections
			 * anyway
			 */
			Object selected = selection.toArray()[0];
			setEnabled (selected instanceof Processor);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copyProcessor(selectionManager);
	}

	/**
	 * Observes events on workflow Selection Manager, i.e. when a workflow node
	 * is selected in the graph view, and enables/disables this action
	 * accordingly.
	 */
	private final class DataflowSelectionObserver extends
			SwingAwareObserver<DataflowSelectionMessage> {
		@Override
		public void notifySwing(Observable<DataflowSelectionMessage> sender,
				DataflowSelectionMessage message) {
			updateStatus();
		}
	}

	private final class SelectionManagerObserver extends
			SwingAwareObserver<SelectionManagerEvent> {
		@Override
		public void notifySwing(Observable<SelectionManagerEvent> sender,
				SelectionManagerEvent message) {
			if (message instanceof WorkflowBundleSelectionEvent) {
				WorkflowBundleSelectionEvent event = (WorkflowBundleSelectionEvent) message;
				WorkflowBundle oldFlow = event
						.getPreviouslySelectedWorkflowBundle();
				WorkflowBundle newFlow = event.getSelectedWorkflowBundle();
				// Update the buttons status as current dataflow has changed
				updateStatus();

				/*
				 * Remove the workflow selection model listener from the
				 * previous (if any) and add to the new workflow (if any)
				 */
				if (oldFlow != null)
					selectionManager.getDataflowSelectionModel(oldFlow)
							.removeObserver(workflowSelectionObserver);
				if (newFlow != null)
					selectionManager.getDataflowSelectionModel(newFlow)
							.addObserver(workflowSelectionObserver);
			}
		}
	}
}
