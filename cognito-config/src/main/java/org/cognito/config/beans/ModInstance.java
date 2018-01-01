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

import java.util.Map;

import org.cognito.config.exceptions.InvalidConfigurationException;

/**
 * An object of this type carries information about a mod ({@link TestableMod}) that runs on a specific environment.
 * <br>
 * <i><b>Example:</b> A web service maybe running on 3 servers: development, test and production. Each of these 
 * 3 instances are represented by a mod instance.</i>
 * 
 * @author aditya.karnad
 *
 */
public class ModInstance {
	
	public ModInstance(String modName, String targetEnvironment, String benchmarkEnvironment, 
			CognitoConfiguration configuration) throws InvalidConfigurationException {
		
		this.modName = modName;
		this.targetEnvironment = targetEnvironment;
		this.benchmarkEnvironment = benchmarkEnvironment;
		deriveModSpecification(configuration);
		targetEnvironmentSpecification = deriveEnvironmentSpecification(
				targetEnvironment, configuration);
		if (benchmarkEnvironment != null) {
			
			benchmarkEnvironmentSpecification = deriveEnvironmentSpecification(
					benchmarkEnvironment, configuration);
		}
	}
	
	private String modName;
	private String targetEnvironment;
	private String benchmarkEnvironment;
	private Map<String, String> modSpecification;
	private Map<String, String> targetEnvironmentSpecification;
	private Map<String, String> benchmarkEnvironmentSpecification;

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getTargetEnvironment() {
		return targetEnvironment;
	}

	public void setTargetEnvironment(String environment) {
		this.targetEnvironment = environment;
	}
	
	public Map<String, String> getModSpecification() {
		return modSpecification;
	}

	public void setModSpecification(Map<String, String> modSpecification) {
		this.modSpecification = modSpecification;
	}

	public Map<String, String> getTargetEnvironmentSpecification() {
		return targetEnvironmentSpecification;
	}

	public void setTargetEnvironmentSpecification(Map<String, String> environmentSpecification) {
		this.targetEnvironmentSpecification = environmentSpecification;
	}
	
	public Map<String, String> getBenchmarkEnvironmentSpecification() {
		return benchmarkEnvironmentSpecification;
	}

	public void setBenchmarkEnvironmentSpecification(Map<String, String> benchmarkEnvironmentSpecification) {
		this.benchmarkEnvironmentSpecification = benchmarkEnvironmentSpecification;
	}

	public String getBenchmarkEnvironment() {
		return benchmarkEnvironment;
	}

	public void setBenchmarkEnvironment(String benchmarkEnvironment) {
		this.benchmarkEnvironment = benchmarkEnvironment;
	}

	private Map<String, String> deriveEnvironmentSpecification(String environmentName, 
			CognitoConfiguration configuration) throws InvalidConfigurationException {

		for (TestableMod mod : configuration.getTestableMods()) {

			if (mod.getName().equals(this.modName)) {

				for (Environment environment : mod.getEnvironments()) {

					if (environment.getName().equals(environmentName)) {

						return environment.getEnvironmentSpecification();
					}
				}
			}
		}
		
		throw new InvalidConfigurationException("modInstanceNotFound", this.modName+":"+environmentName);
	}



	private void deriveModSpecification(CognitoConfiguration configuration) {

		for (TestableMod mod : configuration.getTestableMods()) {
			
			if (mod.getName().equals(this.modName)) {
				
				this.modSpecification=mod.getModSpecification();
				break;
			}
		}
	}


	@Override
	public String toString() {
		String participantMod = "MOD-INST:" + getModName();
		participantMod = participantMod + "(" + getTargetEnvironment()+")";
		
		return participantMod;
	}
}