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
package org.apache.taverna.workbench.design.ui;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.VERTICAL;
import static java.awt.GridBagConstraints.WEST;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * UI for creating/editing dataflow output ports.
 * 
 * @author David Withers
 */
public class DataflowOutputPortPanel extends JPanel {
	private static final long serialVersionUID = -2542858679939965553L;

	private JTextField portNameField;

	public DataflowOutputPortPanel() {
		super(new GridBagLayout());

		portNameField = new JTextField();
 
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.anchor = WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		add(new JLabel("Name:"), constraints);

		constraints.gridx = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.weightx = 1d;
		constraints.fill = HORIZONTAL;
		add(portNameField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = VERTICAL;
		constraints.weighty = 1d;
		add(new JPanel(), constraints);
	}
	
	/**
	 * Returns the portNameField.
	 *
	 * @return the portNameField
	 */
	public JTextField getPortNameField() {
		return portNameField;
	}

	/**
	 * Returns the port name.
	 *
	 * @return the port name
	 */
	public String getPortName() {
		return portNameField.getText();
	}
	
	/**
	 * Sets the port name.
	 *
	 * @param name the name of the port
	 */
	public void setPortName(String name) {
		portNameField.setText(name);
		// Select the text
		if (!name.isEmpty()) {
			portNameField.setSelectionStart(0);
			portNameField.setSelectionEnd(name.length());
		}
	}
}
