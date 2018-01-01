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

public final class Environment {

	private String name;
	private Map<String, String> environmentSpecification;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getEnvironmentSpecification() {
		
		if (environmentSpecification == null) {
			
			environmentSpecification = new HashMap<String, String>();
		}
		return environmentSpecification;
	}

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