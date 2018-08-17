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
 * An instance of this class carries information about a mod ({@link TestableMod}) that runs on a specific environment.
 * <br>
 * <i><b>Example:</b> A web service maybe running on 3 servers: development, test and production. Each of these 
 * 3 instances are represented by a mod instance.</i>
 * 
 * @author Aditya Karnad
 *
 */
public class ModInstance {
	
	/**
	 * Constructs a {@link ModInstance} using the arguments passed.
	 * 
	 * @param modName represents the Module name.
	 * @param targetEnvironment represents the environment on which this instance of the Module runs.
	 * @param benchmarkEnvironment represents an environment which should be considered as a bench mark for regression tests.
	 * @param configuration represents the available instance of the {@link CognitoConfiguration}.
	 * @throws InvalidConfigurationException
	 */
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
	
	/**
	 * Module name.
	 */
	private String modName;
	
	/**
	 * Environment on which this instance of the Module runs.
	 */
	private String targetEnvironment;
	
	/**
	 * Environment which should be considered as a bench mark for regression tests.
	 */
	private String benchmarkEnvironment;
	
	/**
	 * A {@link Map} containing the Module specifications.
	 */
	private Map<String, String> modSpecification;
	
	/**
	 * A {@link Map} containing the <b>Target</b> environment's specifications.
	 */
	private Map<String, String> targetEnvironmentSpecification;
	
	/**
	 * A {@link Map} containing the <b>Benchmark</b> environment's specifications.
	 */
	private Map<String, String> benchmarkEnvironmentSpecification;

	/**
	 * Returns the Module name.
	 * @return Module name.
	 */
	public String getModName() {
		return modName;
	}

	/**
	 * Sets the module name.
	 * @param modName represents the Module name.
	 */
	public void setModName(String modName) {
		this.modName = modName;
	}

	/**
	 * Returns the target environment.
	 * @return target environment. 
	 */
	public String getTargetEnvironment() {
		return targetEnvironment;
	}

	/**
	 * Sets the target environment.
	 * @param environment represents the targetEnvironment.
	 */
	public void setTargetEnvironment(String environment) {
		this.targetEnvironment = environment;
	}
	
	/**
	 * Returns the Module specification packed in a {@link Map}.
	 * @return Module specification packed in a {@link Map}.
	 */
	public Map<String, String> getModSpecification() {
		return modSpecification;
	}
	
	/**
	 * Sets the module specification.
	 * @param modSpecification is a {@link Map} containing module specifications.
	 */
	public void setModSpecification(Map<String, String> modSpecification) {
		this.modSpecification = modSpecification;
	}
	
	/**
	 * Returns the target environment's specification as a {@link Map}.
	 * @return target environment's specification as a {@link Map}.
	 */
	public Map<String, String> getTargetEnvironmentSpecification() {
		return targetEnvironmentSpecification;
	}
	
	/**
	 * Sets the target environment's specifications.
	 * @param environmentSpecification is a {@link Map} containing target environment specifications.
	 */
	public void setTargetEnvironmentSpecification(Map<String, String> environmentSpecification) {
		this.targetEnvironmentSpecification = environmentSpecification;
	}
	
	/**
	 * Returns the benchmark environment's specification as a {@link Map}.
	 * @return benchmark environment's specification as a {@link Map}.
	 */
	public Map<String, String> getBenchmarkEnvironmentSpecification() {
		return benchmarkEnvironmentSpecification;
	}

	/**
	 * Sets the benchmark environment's specifications.
	 * @param environmentSpecification is a {@link Map} containing benchmark environment specifications.
	 */
	public void setBenchmarkEnvironmentSpecification(Map<String, String> environmentSpecification) {
		this.benchmarkEnvironmentSpecification = environmentSpecification;
	}

	/**
	 * Returns the benchmark environment name as defined in configurations.
	 * @return benchmark environment name.
	 */
	public String getBenchmarkEnvironment() {
		return benchmarkEnvironment;
	}
	
	/**
	 * Sets the benchmark environment.
	 * @param environment represents the benchmark environment.
	 */
	public void setBenchmarkEnvironment(String environment) {
		this.benchmarkEnvironment = environment;
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