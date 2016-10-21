package org.apache.taverna.workbench.views.results.saveactions;
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

import static java.lang.System.getProperty;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.FILES_ONLY;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.apache.log4j.Logger.getLogger;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import org.apache.taverna.databundle.DataBundles;
import org.apache.taverna.databundle.DataBundles.ResolveOptions;
import org.apache.taverna.lang.ui.ExtensionFileFilter;
//import org.apache.taverna.platform.execution.impl.local.T2ReferenceConverter;
import org.apache.log4j.Logger;

/**
 * Implementing classes are capable of storing a collection
 * of Paths held in a result map.
 *
 * @author Tom Oinn
 * @author Alex Nenadic
 * @author David Withers
 */
@SuppressWarnings("serial")
public abstract class SaveAllResultsSPI extends AbstractAction {
	protected static final Logger logger = getLogger(SaveAllResultsSPI.class);

	protected Map<String, Path> chosenReferences;
	protected JDialog dialog;

	/**
	 * Returns the save result action implementing this interface. The returned
	 * action will be bound to the appropriate UI component used to trigger the
	 * save action.
	 */
	public abstract AbstractAction getAction();

	/**
	 * The Map passed into this method contains the String -> T2Reference (port
	 * name to reference to value pairs) returned by the current set of results.
	 * The actual listener may well wish to display some kind of dialog, for
	 * example in the case of an Excel export plugin it would be reasonable to
	 * give the user some choice over where the results would be inserted into
	 * the sheet, and also where the generated file would be stored.
	 * <p>
	 * The parent parameter is optional and may be set to null, if not it is assumed to be the
	 * parent component in the UI which caused this action to be created, this allows save dialogs
	 * etc to be placed correctly.
	 */
	public void setChosenReferences(Map<String, Path> chosenReferences) {
		this.chosenReferences = chosenReferences;
	}

	public void setParent(JDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * Get the extension for the filename.
	 * 
	 * @return The extension, in lower case, without any leading "<tt>.</tt>"
	 */
	protected abstract String getFilter();

	protected int getFileSelectionMode() {
		return FILES_ONLY;
	}

	/**
	 * Shows a standard save dialog and dumps the entire result
	 * set to the specified XML file.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(false);

		JFileChooser fc = new JFileChooser();
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		String curDir = prefs.get("currentDir", getProperty("user.home"));
		fc.resetChoosableFileFilters();
		if (getFilter() != null)
			fc.setFileFilter(new ExtensionFileFilter(
					new String[] { getFilter() }));
		fc.setCurrentDirectory(new File(curDir));
		fc.setFileSelectionMode(getFileSelectionMode());

		while (true) {
			if (fc.showSaveDialog(null) != APPROVE_OPTION)
				return;
			prefs.put("currentDir", fc.getCurrentDirectory().toString());
			File file = fc.getSelectedFile();

			/*
			 * If the user did not use the extension for the file, append it to
			 * the file name now
			 */
			if (getFilter() != null
					&& !file.getName().toLowerCase()
							.endsWith("." + getFilter())) {
				String newFileName = file.getName() + "." + getFilter();
				file = new File(file.getParentFile(), newFileName);
			}
			final File finalFile = file;

			if (file.exists()) // File already exists
				// Ask the user if they want to overwrite the file
				if (showConfirmDialog(null, file.getAbsolutePath()
						+ " already exists. Do you want to overwrite it?",
						"File already exists", YES_NO_OPTION) != YES_OPTION)
					continue;

			// File doesn't exist, or user has OK'd overwriting it

			// Do this in separate thread to avoid hanging UI
			new Thread("SaveAllResults: Saving results to " + finalFile) {
				@Override
				public void run() {
					saveDataToFile(finalFile);
				}
			}.start();
			return;
		}
	}

	private void saveDataToFile(final File finalFile) {
		try {
			synchronized (chosenReferences) {
				saveData(finalFile);
			}
		} catch (Exception ex) {
			showMessageDialog(null,
					"Problem saving result data\n" + ex.getMessage(),
					"Save Result Error", ERROR_MESSAGE);
			logger.error("SaveAllResults Error: Problem saving result data", ex);
		}
	}

	protected abstract void saveData(File f) throws IOException;

	protected Object getObjectForName(String name) {
		Object result = null;
		try {
			if (chosenReferences.containsKey(name))
				result = DataBundles.resolve(chosenReferences.get(name), 
						ResolveOptions.STRING, 
						ResolveOptions.REPLACE_ERRORS, 
						ResolveOptions.REPLACE_NULL);
		} catch (IOException e) {
			logger.warn("Error getting value for " + name, e);
		}
		if (result == null)
			result = "(null)";
		return result;
	}

	public Map<String, Path> getChosenReferences() {
		return chosenReferences;
	}

	public JDialog getDialog() {
		return dialog;
	}
}
