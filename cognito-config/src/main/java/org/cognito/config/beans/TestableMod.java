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

/**
 * An instance of this class provides information about the Module to be tested and it's configuration as setup in 
 * the <tt>cognito-cfg.xml</tt>.
 * 
 * @author Aditya Karnad
 */
public final class TestableMod {

	/**
	 * Indicates a Web-Service module.
	 */
	public final static byte TYPE_WebService = 1;
	
	/**
	 * Indicates a User Interface module.
	 */
	public final static byte TYPE_UserInterface = 2;
	
	private final static String $TYPE_WebService = "web service";
	private final static String $TYPE_UserInterface = "user interface";
	
	/**
	 * Name of the module which is to be tested.
	 */
	private String name;
	
	/**
	 * Type of the module which is to be tested.
	 * 
	 * @see TestableMod#TYPE_UserInterface
	 * @see TestableMod#TYPE_WebService
	 */
	private byte type;
	
	/**
	 * {@link List} of {@link Environment}s
	 */
	private List<Environment> environments;
	
	/**
	 * Module specification.<br>
	 * <i>Key-value pairs which can hold useful information about the module.</i>
	 */
	private Map<String, String> modSpecification;

	/**
	 * Returns the name of the module.
	 * @return module name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the module.
	 * @param name represents the module name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the type of the module.
	 * @return module type.
	 * @see TestableMod#TYPE_WebService
	 * @see TestableMod#TYPE_UserInterface
	 */
	public byte getType() {
		return type;
	}

	/**
	 * Sets the type of the module.
	 * @param type represents the module type.
	 * @see TestableMod#TYPE_WebService
	 * @see TestableMod#TYPE_UserInterface
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * Returns a {@link List} of all environments configured for the module.
	 * @return list of all environments on which the module is available.
	 */
	public List<Environment> getEnvironments() {
		
		if (environments == null) {
			
			environments = new ArrayList<Environment>();
		}
		return environments;
	}
	
	/**
	 * Sets the environments for the module.
	 * @param environments represents a {@link List} of all environments on which the module is available.
	 */
	public void setEnvironments(List<Environment> environments) {
		this.environments = environments;
	}
	
	/**
	 * Returns the user defined specifications associated with the module.
	 * @return Module specification as a {@link Map};
	 */
	public Map<String, String> getModSpecification() {
		
		if(modSpecification == null) {
			
			modSpecification = new HashMap<String, String>();
		}
		return modSpecification;
	}
	
	/**
	 * Sets the user defined specifications associated with the module.
	 * @param modSpecification Module specification as a {@link Map};
	 */
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
	
	/**
	 * Recognize and set the type of the module based on the information read from the <tt>cognito-cfg.xml</tt>.
	 * @param typeReadFromXml Type of module as read from the configuration file (<tt>cognito-cfg.xml</tt>).
	 */
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