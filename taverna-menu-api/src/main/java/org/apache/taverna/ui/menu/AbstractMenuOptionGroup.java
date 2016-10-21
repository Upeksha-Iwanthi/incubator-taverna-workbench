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
package org.apache.taverna.ui.menu;

import java.net.URI;

/**
 * A {@link MenuComponent} of the type {@link MenuType#optionGroup}.
 * <p>
 * Subclass to create an SPI implementation for the {@link MenuManager} of an
 * option group. An option group is similar to a
 * {@linkplain AbstractMenuSection section}, but enforces that only one of the
 * children are selected at any time.
 * <p>
 * Menu components are linked together using URIs, avoiding the need for compile
 * time dependencies between SPI implementations. To add actions or toggles to
 * an option group, use the {@link URI} identifying this section as their parent
 * id.
 * <p>
 * <strong>Note:</strong> To avoid conflicts with other plugins, use a unique
 * URI root that is related to the Java package name, for instance
 * <code>http://cs.university.ac.uk/myplugin/2008/menu</code>, and use hash
 * identifiers for each menu item, for instance
 * <code>http://cs.university.ac.uk/myplugin/2008/menu#run</code> for a "Run"
 * item. Use flat URI namespaces, don't base a child's URI on the parent's URI,
 * as this might make it difficult to relocate the parent menu.
 * <p>
 * You need to list the {@linkplain Class#getName() fully qualified class name}
 * (for example <code>com.example.t2plugin.menu.MyMenu</code>) of the option
 * group implementation in the SPI description resource file
 * <code>/META-INF/services/net.sf.taverna.t2.ui.menu.MenuComponent</code> so
 * that it can be discovered by the {@link MenuManager}. This requirement also
 * applies to parent menu components (except {@link DefaultToolBar} and
 * {@link DefaultMenuBar}, but ensure they are only listed once.
 * 
 * @author Stian Soiland-Reyes
 */
public abstract class AbstractMenuOptionGroup extends AbstractMenuItem {
	/**
	 * Construct an option group.
	 * 
	 * @param parentId
	 *            The {@link URI} of the parent menu component. The parent
	 *            should be of type {@link MenuType#menu} or
	 *            {@link MenuType#toolBar}.
	 * @param positionHint
	 *            The position hint to determine the position of this option
	 *            group among its siblings in the parent menu. For
	 *            extensibility, use BASIC style numbering such as 10, 20, etc.
	 *            (Note that position hints are local to each parent, so each
	 *            option group have their own position hint scheme for their
	 *            children.)
	 * @param id
	 *            The {@link URI} to identify this option group. Use this as the
	 *            parent ID for menu components to appear in this option group.
	 */
	public AbstractMenuOptionGroup(URI parentId, int positionHint, URI id) {
		super(MenuType.optionGroup, parentId, id);
		this.positionHint = positionHint;
	}
}
