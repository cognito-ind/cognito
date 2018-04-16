/*    
 * 				Copyright 2018 Aditya Karnad
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *    
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
*/
package org.cognito.config.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class contains information about a specific environment associated with a testable mod.
 * @author Aditya Karnad
 */
public final class Environment {

	/**
	 * Environment name.
	 */
	private String name;
	
	/**
	 * Environment specification.<br>
	 * <i>Key-value pairs which can hold useful information about the environment.<br>
	 * <b>Example:</b> Web-service URLs, etc.</i>
	 */
	private Map<String, String> environmentSpecification;

	/**
	 * Returns the environment name.
	 * @return environment name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the environment name.
	 * @param name represents the environment name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the environment specifications as defined in the <tt>cognito-cfg.xml</tt> in a {@link Map}.
	 * @return environment specification as a {@link Map}.
	 */
	public Map<String, String> getEnvironmentSpecification() {
		
		if (environmentSpecification == null) {
			
			environmentSpecification = new HashMap<String, String>();
		}
		return environmentSpecification;
	}

	/**
	 * Sets the environment specification.
	 * @param environmentSpecification represents a {@link Map} containing environment specification.
	 */
	public void setEnvironmentSpecification(Map<String, String> environmentSpecification) {
		this.environmentSpecification = environmentSpecification;
	}
	
	@Override
	public String toString() {
		
		String envText = "\n\tENV:"+getName()+"";
		
		for (String spec : getEnvironmentSpecification().keySet()) {
			
			envText += "\n\t\tENV-SPEC:" + spec + "=" + getEnvironmentSpecification().get(spec);
		}
		
		return envText;
	}
}