package org.apache.taverna.workbench.loop.comparisons;
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

import org.apache.taverna.workbench.loop.LoopConfigurationPanel;

/**
 * A comparison beanshell template for {@link LoopConfigurationPanel}.
 * <p>
 * A comparison is a template for generating a beanshell that can be used for
 * comparisons in say the {@link Loop} layer.
 * 
 * @author Stian Soiland-Reyes
 * 
 */
public abstract class Comparison {

	public String toString() {
		return getName();
	}

	public abstract String getId();

	public abstract String getName();

	public abstract String getValueType();

	public abstract String getScriptTemplate();
}