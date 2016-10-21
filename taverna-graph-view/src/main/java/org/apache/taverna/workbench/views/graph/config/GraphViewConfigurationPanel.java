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
package org.apache.taverna.workbench.views.graph.config;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.REMAINDER;
import static java.awt.GridBagConstraints.WEST;
import static javax.swing.SwingConstants.LEFT;
import static org.apache.taverna.workbench.helper.Helper.showHelp;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.allportIcon;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.blobIcon;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.horizontalIcon;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.noportIcon;
import static org.apache.taverna.workbench.icons.WorkbenchIcons.verticalIcon;
import static org.apache.taverna.workbench.views.graph.config.GraphViewConfiguration.ALIGNMENT;
import static org.apache.taverna.workbench.views.graph.config.GraphViewConfiguration.ANIMATION_ENABLED;
import static org.apache.taverna.workbench.views.graph.config.GraphViewConfiguration.ANIMATION_SPEED;
import static org.apache.taverna.workbench.views.graph.config.GraphViewConfiguration.PORT_STYLE;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.apache.taverna.workbench.models.graph.Graph.Alignment;
import org.apache.taverna.workbench.models.graph.GraphController.PortStyle;

/**
 * UI for GraphViewConfiguration.
 * 
 * @author David Withers
 */
public class GraphViewConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 3779779432230124131L;
	private static final int ANIMATION_SPEED_MIN = 100;
	private static final int ANIMATION_SPEED_MAX = 3100;

	private GraphViewConfiguration configuration;
	private JRadioButton noPorts;
	private JRadioButton allPorts;
	private JRadioButton blobs;
	private JRadioButton vertical;
	private JRadioButton horizontal;
	private JCheckBox animation;
	private JLabel animationSpeedLabel;
	private JSlider animationSpeedSlider;

	public GraphViewConfigurationPanel(GraphViewConfiguration configuration) {
		this.configuration = configuration;
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		// Title describing what kind of settings we are configuring here
		JTextArea descriptionText = new JTextArea(
				"Default settings for the workflow diagram");
		descriptionText.setLineWrap(true);
		descriptionText.setWrapStyleWord(true);
		descriptionText.setEditable(false);
		descriptionText.setFocusable(false);
		descriptionText.setBorder(new EmptyBorder(10, 10, 10, 10));

		JLabel defaultLayoutLabel = new JLabel("Service display");

		noPorts = new JRadioButton();
		allPorts = new JRadioButton();
		blobs = new JRadioButton();

		JLabel noPortsLabel = new JLabel("Name only", noportIcon, LEFT);
		JLabel allPortsLabel = new JLabel("Name and ports", allportIcon, LEFT);
		JLabel blobsLabel = new JLabel("No text", blobIcon, LEFT);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(noPorts);
		buttonGroup.add(allPorts);
		buttonGroup.add(blobs);

		JLabel defaultAlignmentLabel = new JLabel("Diagram alignment");

		vertical = new JRadioButton();
		horizontal = new JRadioButton();

		JLabel verticalLabel = new JLabel("Vertical", verticalIcon, LEFT);
		JLabel horizontalLabel = new JLabel("Horizontal", horizontalIcon, LEFT);

		ButtonGroup alignmentButtonGroup = new ButtonGroup();
		alignmentButtonGroup.add(horizontal);
		alignmentButtonGroup.add(vertical);

		animation = new JCheckBox("Enable animation");

		animationSpeedLabel = new JLabel("Animation speed");

		animationSpeedSlider = new JSlider(ANIMATION_SPEED_MIN,
				ANIMATION_SPEED_MAX);
		animationSpeedSlider.setMajorTickSpacing(500);
		animationSpeedSlider.setMinorTickSpacing(100);
		animationSpeedSlider.setPaintTicks(true);
		animationSpeedSlider.setPaintLabels(true);
		animationSpeedSlider.setInverted(true);
		animationSpeedSlider.setSnapToTicks(true);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(new Integer(ANIMATION_SPEED_MIN), new JLabel("Fast"));
		labelTable.put(new Integer(
				((ANIMATION_SPEED_MAX - ANIMATION_SPEED_MIN) / 2)
						+ ANIMATION_SPEED_MIN), new JLabel("Medium"));
		labelTable.put(new Integer(ANIMATION_SPEED_MAX), new JLabel("Slow"));
		animationSpeedSlider.setLabelTable(labelTable);

		animation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean animationEnabled = animation.isSelected();
				animationSpeedLabel.setEnabled(animationEnabled);
				animationSpeedSlider.setEnabled(animationEnabled);
			}
		});

		// Set current configuration values
		setFields(configuration);

		c.anchor = WEST;
		c.gridx = 0;
		c.gridwidth = REMAINDER;
		c.weightx = 1d;
		c.weighty = 0d;
		c.fill = HORIZONTAL;

		add(descriptionText, c);

		c.insets = new Insets(10, 0, 10, 0);
		add(defaultLayoutLabel, c);

		c.insets = new Insets(0, 20, 0, 0);
		c.gridwidth = 1;
		c.weightx = 0d;
		add(noPorts, c);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridx = RELATIVE;
		add(noPortsLabel, c);

		c.insets = new Insets(0, 10, 0, 0);
		add(allPorts, c);
		c.insets = new Insets(0, 5, 0, 0);
		add(allPortsLabel, c);

		c.insets = new Insets(0, 10, 0, 0);
		add(blobs, c);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridwidth = REMAINDER;
		c.weightx = 1d;
		add(blobsLabel, c);

		// alignment
		c.insets = new Insets(20, 0, 10, 0);
		c.gridx = 0;
		add(defaultAlignmentLabel, c);

		c.insets = new Insets(0, 20, 0, 0);
		c.gridx = 0;
		c.gridwidth = 1;
		c.weightx = 0d;
		add(vertical, c);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridx = RELATIVE;
		add(verticalLabel, c);

		c.insets = new Insets(0, 10, 0, 0);
		add(horizontal, c);
		c.insets = new Insets(0, 5, 0, 0);
		c.gridwidth = REMAINDER;
		c.weightx = 1d;
		add(horizontalLabel, c);

		// animation
		c.gridx = 0;
		c.gridwidth = REMAINDER;
		c.insets = new Insets(20, 0, 10, 0);
		add(animation, c);

		c.insets = new Insets(0, 20, 0, 0);
		add(animationSpeedLabel, c);

		c.insets = new Insets(0, 20, 10, 30);
		c.anchor = NORTHWEST;
		c.weighty = 0d;
		add(animationSpeedSlider, c);

		// Buttons
		c.gridx = 0;
		c.insets = new Insets(0, 20, 10, 30);
		c.anchor = NORTHWEST;
		c.weighty = 1d;
		add(createButtonPanel(), c);
	}

	/**
	 * Create the panel with the buttons.
	 */
	@SuppressWarnings("serial")
	private JPanel createButtonPanel() {
		final JPanel panel = new JPanel();

		/**
		 * The helpButton shows help about the current component
		 */
		JButton helpButton = new JButton(new AbstractAction("Help") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showHelp(panel);
			}
		});
		panel.add(helpButton);

		/**
		 * The resetButton changes the property values shown to those
		 * corresponding to the configuration currently applied.
		 */
		JButton resetButton = new JButton(new AbstractAction("Reset") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setFields(configuration);
			}
		});
		panel.add(resetButton);

		/**
		 * The applyButton applies the shown field values to the
		 * {@link HttpProxyConfiguration} and saves them for future.
		 */
		JButton applyButton = new JButton(new AbstractAction("Apply") {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applySettings();
				setFields(configuration);
			}
		});
		panel.add(applyButton);

		return panel;
	}

	/**
	 * Save the currently set field values to the {@link GraphViewConfiguration}
	 * . Also apply those values to the currently running Taverna.
	 */
	private void applySettings() {
		// Service display
		if (noPorts.isSelected()) {
			configuration.setProperty(PORT_STYLE, PortStyle.NONE.toString());
		} else if (allPorts.isSelected()) {
			configuration.setProperty(PORT_STYLE, PortStyle.ALL.toString());
		} else if (blobs.isSelected()) {
			configuration.setProperty(PORT_STYLE, PortStyle.BLOB.toString());
		}

		// Diagram alignment
		if (vertical.isSelected()) {
			configuration.setProperty(ALIGNMENT, Alignment.VERTICAL.toString());
		} else if (horizontal.isSelected()) {
			configuration.setProperty(ALIGNMENT,
					Alignment.HORIZONTAL.toString());
		}

		// Animation and its speed
		if (animation.isSelected()) {
			configuration.setProperty(ANIMATION_ENABLED, String.valueOf(true));
		} else {
			configuration.setProperty(ANIMATION_ENABLED, String.valueOf(false));
		}
		int speed = animationSpeedSlider.getValue();
		configuration.setProperty(ANIMATION_SPEED, String.valueOf(speed));
	}

	/**
	 * Set the shown configuration field values to those currently in use (i.e.
	 * last saved configuration).
	 */
	private void setFields(GraphViewConfiguration configurable) {
		PortStyle portStyle = PortStyle.valueOf(configurable
				.getProperty(PORT_STYLE));
		if (portStyle.equals(PortStyle.NONE)) {
			noPorts.setSelected(true);
		} else if (portStyle.equals(PortStyle.ALL)) {
			allPorts.setSelected(true);
		} else {
			blobs.setSelected(true);
		}

		Alignment alignment = Alignment.valueOf(configurable
				.getProperty(ALIGNMENT));
		if (alignment.equals(Alignment.VERTICAL)) {
			vertical.setSelected(true);
		} else {
			horizontal.setSelected(true);
		}

		boolean animationEnabled = Boolean.parseBoolean(configurable
				.getProperty(ANIMATION_ENABLED));
		animation.setSelected(animationEnabled);

		Integer animationSpeed = Integer.valueOf(configurable
				.getProperty(ANIMATION_SPEED));
		if (animationSpeed > ANIMATION_SPEED_MAX) {
			animationSpeed = ANIMATION_SPEED_MAX;
		} else if (animationSpeed < ANIMATION_SPEED_MIN) {
			animationSpeed = ANIMATION_SPEED_MIN;
		}
		animationSpeedSlider.setValue(animationSpeed);
		animationSpeedSlider.setEnabled(animationEnabled);

		animationSpeedLabel.setEnabled(animationEnabled);
	}

	// for testing only
	public static void main(String[] args) {
		JDialog dialog = new JDialog();
		dialog.add(new GraphViewConfigurationPanel(null));
		dialog.setModal(true);
		dialog.setSize(500, 400);
		dialog.setVisible(true);
		System.exit(0);
	}
}
