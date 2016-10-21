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
package org.apache.taverna.workbench.run.cleanup;

import static java.awt.Color.WHITE;
import static java.awt.Font.BOLD;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.SOUTHEAST;
import static java.awt.GridBagConstraints.SOUTHWEST;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.taverna.workbench.helper.HelpEnabledDialog;

/**
 * Dialog that warns if there are running workflows while the workbench is being
 * shutdown and gives the user a change to cancel.
 * 
 * @author David Withers
 */
@SuppressWarnings("serial")
public class WorkflowRunStatusShutdownDialog extends HelpEnabledDialog {
	private JButton abortButton;
	private JButton cancelButton;
	private boolean confirmShutdown = true;

	public WorkflowRunStatusShutdownDialog(int runningWorkflows, int pausedWorkflows) {
		super((Frame) null, "Workflows still running", true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);

		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		JLabel title = new JLabel("Running or paused workflows detected.");
		title.setFont(title.getFont().deriveFont(BOLD, 14));

		abortButton = new JButton("Shutdown now");
		abortButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showConfirmDialog(
						WorkflowRunStatusShutdownDialog.this,
						"If you close Taverna now all workflows will be cancelled.\n"
						+ "Are you sure you want to close now?",
						"Confirm Shutdown", YES_NO_OPTION, WARNING_MESSAGE) == YES_OPTION)
					setVisible(false);
			}
		});

		cancelButton = new JButton("Cancel shutdown");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmShutdown = false;
				setVisible(false);
			}
		});

		StringBuilder sb = new StringBuilder();
		sb.append("There ");
		if (runningWorkflows > 0) {
			sb.append(runningWorkflows > 1 ? "are " : "is ");
			sb.append(runningWorkflows);
			sb.append(" running ");
			sb.append(runningWorkflows > 1 ? "workflows" : "workflow");
		} else
			sb.append(pausedWorkflows > 1 ? "are " : "is ");
		if (pausedWorkflows > 0) {
			if (runningWorkflows > 0)
				sb.append(" and ");
			sb.append(pausedWorkflows);
			sb.append(" paused ");
			sb.append(pausedWorkflows > 1 ? "workflows" : "workflow");
		}
		JLabel message = new JLabel(sb.toString());

		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(WHITE);

		c.anchor = NORTHWEST;
		c.insets = new Insets(20, 30, 20, 30);
		c.weightx = 1d;
		c.weighty = 0d;
		topPanel.add(title, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.fill = HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		add(topPanel, c);

		c.insets = new Insets(20, 20, 20, 20);
		c.weighty = 1d;
		c.weighty = 1d;
		add(message, c);

		c.fill = NONE;
		c.anchor = SOUTHWEST;
		c.insets = new Insets(10, 20, 10, 20);
		c.weightx = 0.5;
		c.weighty = 0d;
		c.gridx = 0;
		c.gridwidth = 1;
		add(cancelButton, c);

		c.anchor = SOUTHEAST;
		c.gridx = 1;
		add(abortButton, c);

		setSize(400, 230);
	}

	/**
	 * @return <code>true</code> if it's OK to proceed with the shutdown
	 */
	public boolean confirmShutdown() {
		return confirmShutdown;
	}
}
