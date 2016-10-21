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
package org.apache.taverna.workbench.ui.servicepanel.tree;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class FilterTreeSelectionModel extends DefaultTreeSelectionModel{
	private static final long serialVersionUID = 3127644524735089630L;
	
	public FilterTreeSelectionModel(){
		super();
		setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	@Override
	public void setSelectionPath(TreePath path) {
		/*
		 * Nothing happens here - only calls to mySetSelectionPath() will have
		 * the effect of a node being selected.
		 */
	}
	
	public void mySetSelectionPath(TreePath path) {
		super.setSelectionPath(path);
	}
}
