package org.apache.taverna.workbench.views.results.processor;
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

import java.nio.file.Path;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.taverna.workbench.views.results.workflow.WorkflowResultTreeNode.ResultTreeNodeState;

import org.apache.log4j.Logger;

/**
 * A node in the processor result tree - can be a single data item, a list of data items or
 * tree root.
 *
 * @author Alex Nenadic
 * @author David Withers
 */
@SuppressWarnings("serial")
public class ProcessorResultTreeNode extends DefaultMutableTreeNode {
	public enum ProcessorResultTreeNodeState {
		RESULT_TOP, RESULT_LIST, RESULT_REFERENCE
	};

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(ProcessorResultTreeNode.class);

	private ProcessorResultTreeNodeState state;
	private Path reference; // reference to actual data if this node is a data node
	private int listSize; // number of element if this node is a list

	// Create root node
	public ProcessorResultTreeNode() {
		this.state = ProcessorResultTreeNodeState.RESULT_TOP;
	}

	// Create data node
	public ProcessorResultTreeNode(Path reference) {
		this.reference = reference;
		this.state = ProcessorResultTreeNodeState.RESULT_REFERENCE;
	}

	// Create list node
    public ProcessorResultTreeNode(int listSize, Path reference) {
		this.listSize = listSize;
		this.reference = reference;
		this.state = ProcessorResultTreeNodeState.RESULT_LIST;
	}

	public ProcessorResultTreeNodeState getState() {
		return state;
	}

	public void setState(ProcessorResultTreeNodeState state) {
		this.state = state;
	}

	public Path getReference() {
		return reference;
	}

	public void setReference(Path reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		if (state.equals(ProcessorResultTreeNodeState.RESULT_TOP))
			return "Results:";
		if (state.equals(ProcessorResultTreeNodeState.RESULT_LIST)) {
			if (getChildCount() == 0)
				return "Empty list";
			return "List with " + listSize + " values";
		}
		return reference.toString();
	}

	public boolean isState(ProcessorResultTreeNodeState state) {
		return this.state.equals(state);
	}

	public int getValueCount() {
		int result = 0;
		if (isState(ProcessorResultTreeNodeState.RESULT_REFERENCE))
			result = 1;
		else if (isState(ProcessorResultTreeNodeState.RESULT_LIST)) {
			int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				ProcessorResultTreeNode child = (ProcessorResultTreeNode) getChildAt(i);
				result += child.getValueCount();
			}
		}
		return result;
	}

	public int getSublistCount() {
		int result = 0;
		if (isState(ProcessorResultTreeNodeState.RESULT_LIST)) {
			int childCount = this.getChildCount();
			for (int i = 0; i < childCount; i++) {
				ProcessorResultTreeNode child = (ProcessorResultTreeNode) getChildAt(i);
				if (child.isState(ProcessorResultTreeNodeState.RESULT_LIST))
					result++;
			}
		}
		return result;
	}

//	public Object getAsObject() {
//		if (reference != null) {
//		Identified identified = referenceService
//		.resolveIdentifier(reference, null, null);
//		if (identified instanceof ErrorDocument) {
//			ErrorDocument errorDocument = (ErrorDocument) identified;
//			return errorDocument.getMessage();
//		}
//		}
//		if (isState(ProcessorResultTreeNodeState.RESULT_TOP)) {
//			if (getChildCount() == 0) {
//				return null;
//			}
//			else {
//				return ((ProcessorResultTreeNode) getChildAt(0)).getAsObject();
//			}
//		}
//		if (isState(ProcessorResultTreeNodeState.RESULT_LIST)) {
//			List<Object> result = new ArrayList<Object>();
//			for (int i = 0; i < getChildCount(); i++) {
//				ProcessorResultTreeNode child = (ProcessorResultTreeNode) getChildAt(i);
//				result.add (child.getAsObject());
//			}
//			return result;
//		}
//		if (reference == null) {
//			return null;
//		}
////		if (context.getReferenceService() == null) {
////			return null;
////		}
//		try {
//			Object result = referenceService.renderIdentifier(reference, Object.class, null);
//			return result;
//		}
//		catch (Exception e) {
//			// Not good to catch exception but
//			return null;
//		}
//	}

	public boolean isState(ResultTreeNodeState state) {
		return this.state.equals(state);
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getListSize() {
		return listSize;
	}
}
