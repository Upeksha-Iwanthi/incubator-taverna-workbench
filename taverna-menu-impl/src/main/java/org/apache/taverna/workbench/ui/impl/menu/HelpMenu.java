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
package org.apache.taverna.workbench.ui.impl.menu;

import static java.awt.event.KeyEvent.VK_H;
import static javax.swing.Action.MNEMONIC_KEY;
import static org.apache.taverna.ui.menu.DefaultMenuBar.DEFAULT_MENU_BAR;

import java.net.URI;

import org.apache.taverna.ui.menu.AbstractMenu;

public class HelpMenu extends AbstractMenu {
	public static final URI HELP_URI = URI
			.create("http://taverna.sf.net/2008/t2workbench/menu#help");

	public HelpMenu() {
		super(DEFAULT_MENU_BAR, 1024, HELP_URI, makeAction());
	}

	public static DummyAction makeAction() {
		DummyAction action = new DummyAction("Help");
		action.putValue(MNEMONIC_KEY, Integer.valueOf(VK_H));
		return action;
	}
}
