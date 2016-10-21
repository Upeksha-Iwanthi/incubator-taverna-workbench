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
package org.apache.taverna.workbench.views.graph.toolbar;

import static org.apache.taverna.ui.menu.DefaultToolBar.DEFAULT_TOOL_BAR;

import java.net.URI;

import org.apache.taverna.ui.menu.AbstractMenuSection;

/**
 * @author Alex Nenadic
 */
public class GraphEditToolbarSection extends AbstractMenuSection {
	public static final URI GRAPH_EDIT_TOOLBAR_SECTION = URI
			.create("http://taverna.sf.net/2008/t2workbench/menu#graphEditToolbarSection");

	public GraphEditToolbarSection() {
		super(DEFAULT_TOOL_BAR, 30, GRAPH_EDIT_TOOLBAR_SECTION);
	}
}
