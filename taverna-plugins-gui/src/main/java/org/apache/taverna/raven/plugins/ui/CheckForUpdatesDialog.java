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
package org.apache.taverna.raven.plugins.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.apache.taverna.workbench.helper.HelpEnabledDialog;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Dialog that lets user know that there are updates available.
 * 
 */
@SuppressWarnings("serial")
public class CheckForUpdatesDialog extends HelpEnabledDialog {
	
	private Logger logger = Logger.getLogger(CheckForUpdatesDialog.class);

	public CheckForUpdatesDialog(){
		super((Frame)null, "Updates available", true);
		initComponents();
	}
	
	// For testing
	public static void main (String[] args){
		CheckForUpdatesDialog dialog = new CheckForUpdatesDialog();
		dialog.setVisible(true);
	}

	private void initComponents() {
		// Base font for all components on the form
		Font baseFont = new JLabel("base font").getFont().deriveFont(11f);
		
		// Message saying that updates are available
		JPanel messagePanel = new JPanel(new BorderLayout());
		messagePanel.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), new EtchedBorder(EtchedBorder.LOWERED)));
		JLabel message = new JLabel(
				"<html><body>Updates are available for some Taverna components. To review and <br>install them go to 'Updates and plugins' in the 'Advanced' menu.</body><html>");
		message.setFont(baseFont.deriveFont(12f));
		message.setBorder(new EmptyBorder(5,5,5,5));
		message.setIcon(UpdatesAvailableIcon.updateIcon);
		messagePanel.add(message, BorderLayout.CENTER);
		
		// Buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton("OK"); // we'll check for updates again in 2 weeks
		okButton.setFont(baseFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				okPressed();
			}
		});
		
		buttonsPanel.add(okButton);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(messagePanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		pack();
		setResizable(false);
		// Center the dialog on the screen (we do not have the parent)
		Dimension dimension = getToolkit().getScreenSize();
		Rectangle abounds = getBounds();
		setLocation((dimension.width - abounds.width) / 2,
				(dimension.height - abounds.height) / 2);
		setSize(getPreferredSize());
	}
	
	protected void okPressed() {
	       try {
	            FileUtils.touch(CheckForUpdatesStartupHook.lastUpdateCheckFile);
	        } catch (IOException ioex) {
	        	logger.error("Failed to touch the 'Last update check' file for Taverna updates.", ioex);
	        }
		closeDialog();		
	}
	
	private void closeDialog() {
		setVisible(false);
		dispose();
	}

}
