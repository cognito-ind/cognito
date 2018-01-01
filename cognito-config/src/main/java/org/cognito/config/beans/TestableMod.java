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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cognito.config.util.TextManipulationUtilities;

//TODO Aditya: Javadoc here...
public final class TestableMod {

	public final static byte TYPE_WebService = 1;
	private final static String $TYPE_WebService = "web service";
	public final static byte TYPE_UserInterface = 2;
	private final static String $TYPE_UserInterface = "user interface";
	

	private String name;
	private byte type;
	private List<Environment> environments;
	private Map<String, String> modSpecification;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public List<Environment> getEnvironments() {
		
		if (environments == null) {
			
			environments = new ArrayList<Environment>();
		}
		return environments;
	}
	
	public void setEnvironments(List<Environment> environments) {
		this.environments = environments;
	}
	
	public Map<String, String> getModSpecification() {
		
		if(modSpecification == null) {
			
			modSpecification = new HashMap<String, String>();
		}
		return modSpecification;
	}

	public void setModSpecification(Map<String, String> modSpecification) {
		
		this.modSpecification = modSpecification;
	}
	
	private String resolveType(byte modType) {
		
		if (modType == TYPE_WebService) {
			return $TYPE_WebService;
		}
		else if (modType == TYPE_UserInterface) {
			return $TYPE_UserInterface;
		}
		return "unknown";
	}

	@Override
	public String toString() {
		
		String modText = "\nMOD:" + getName() +"("+ resolveType(getType()) +")";
		
		for (Environment env : getEnvironments()) {
			
			modText += env;
		}
		
		for (String spec : getModSpecification().keySet()) {
			
			modText += "\n\tMOD-SPEC:" + spec + "=" + getModSpecification().get(spec);
		}
		
		return modText;
	}
	
	public void recognizeAndSetType(String typeReadFromXml) {
		
		if (TextManipulationUtilities.isAcceptableEquivalent(
				typeReadFromXml, TestableMod.$TYPE_UserInterface)) {
			
			setType(TestableMod.TYPE_UserInterface);
		}
		else if (TextManipulationUtilities.isAcceptableEquivalent(
				typeReadFromXml, TestableMod.$TYPE_WebService)) {
			
			setType(TestableMod.TYPE_WebService);
		}
	}
}