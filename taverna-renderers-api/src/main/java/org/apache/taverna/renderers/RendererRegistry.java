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
package org.apache.taverna.renderers;

import java.util.List;

/**
 * Manages the collection of {@linkplain Renderer renderers}.
 * 
 * @author David Withers
 */
public interface RendererRegistry {
	/**
	 * Get all of the available renderers for a specific MIME type. If there is
	 * a problem with one then catch the exception and log the problem but carry
	 * on since there is probably more than one way to render the data
	 * 
	 * @param path
	 * @param mimeType
	 * @return
	 */
	List<Renderer> getRenderersForMimeType(String mimeType);

	/**
	 * Return all the renderers.
	 * 
	 * @return all the renderers
	 */
	List<Renderer> getRenderers();
	
	/**
	 * Return a mime type detector.
	 * <p>
	 * The detector can help guess the mime type of a given binary or String.
	 * <p>
	 * Note that the detector might also detect media types that do not have
	 * a corresponding renderer in {@link #getRenderersForMimeType(String)}.
	 * 
	 * @return A mime type detector
	 */
	MediaTypeDetector getMimeTypeDetector();
}
