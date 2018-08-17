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
package org.cognito.config.parsers;

import java.io.File;

import org.apache.log4j.Logger;
import org.cognito.config.beans.CognitoConfiguration;
import org.cognito.config.beans.TestableMod;
import org.cognito.config.exceptions.CognitoConfigurationException;
import org.cognito.config.exceptions.InvalidConfigurationException;

/**
 * The sub-classes of this class are capable of reading various formats of application specific configuration and 
 * build the {@link CognitoConfiguration} object from it.<br>
 * <i><b>Known Sub-classes:</b> {@link XmlConfigurationBuilder}</i>
 * 
 * @author Aditya Karnad
 */
public abstract class ConfigurationBuilder {
	
	static {
		logger = Logger.getLogger(ConfigurationBuilder.class);
	}
	
	private static Logger logger;
	private static boolean referenceExists = false;
	protected CognitoConfiguration configuration;
	
	
	/**
	 * Instantiates a {@link ConfigurationBuilder}.
	 */
	public ConfigurationBuilder() {
		
		if(referenceExists) {
			
			logger.warn("A reference to the configuration is already available!" + System.lineSeparator());
		}
		else {
			referenceExists=true;
		}
	}
	
	/**
	 * Builds and then returns the {@link CognitoConfiguration} instance after validating it.
	 * @return A valid configuration ({@link CognitoConfiguration} instance)
	 * @throws CognitoConfigurationException
	 */
	public final CognitoConfiguration buildConfiguration() throws CognitoConfigurationException {
		
		logger.debug("Building Configuration.");
		readConfiguration();
		validateConfiguration();
		logger.info("Building Configuration... Complete");
		return configuration;
	}
	
	/**
	 * Reads the configuration.
	 * @throws CognitoConfigurationException
	 */
	protected abstract void readConfiguration() throws CognitoConfigurationException;
	
	
	/**
	 * Validates the configuration which was read.
	 * @throws InvalidConfigurationException Thrown when configuration validation fails.
	 */
	private void validateConfiguration() throws InvalidConfigurationException {
		logger.debug("Validating Configuration.");
		
		verifyTestableMods();
		validateReportGenPath();
		
		/** At least 1 Testable-mod(name n type) should be present. Else Exception should be thrown
		 *  At least 1 Testable-mod environment(name and spec) is mandatory. Else throw Exception
		 *  (Mod-spec are not mandatory)
		 *  
		 *  PATH cannot be empty for report generation
		 *  Path of the report generation should be a valid directory
		 *  
		 *  Regression Test can be done on any one predefined test-mod
		 *  (Name of the Mod in regression test should be any one of the defined Testable-Mods)
		 * 
		 *  Test-Scopes can't be left blank - scenarios 
		 *  (applies to all types of test configurations - regression, system & integration)
		 *  
		 *  Integratable-mod TestMod-1-"Name" >  env-name="Testable Mode-1 > Environment Names (Any one of all environments)
		 *  
		 * 	target n benchmark environments should be from one Testable Mod
		 * 
		 *  There must be 2 or more participating-mods for Integration Tests.
		 *  Both Participating-mods cannot be same
		 *  
		 */
		 logger.info("Validating Configuration... Complete");
	}
	
	private void validateReportGenPath() throws InvalidConfigurationException {
		logger.debug("Validating Report Generation Path.");
		
		if(configuration.getReportGenerationConfiguration().getReportGenerationPath()==""){
			throw new InvalidConfigurationException("NoConfigPath", " thrown at org.cognito.config.parsers."
					+ "ConfigurationBuilder.validateReportGenPath"
					+ "(CognitoConfiguration config) throws InvalidConfigurationException");
		}
			File folder = new File(configuration.getReportGenerationConfiguration().getReportGenerationPath());
			if(!(folder.exists() && folder.isDirectory())){
				try{
					folder.mkdir();
				}
				catch(Exception e){
					throw new InvalidConfigurationException("DirectoryCreationError", e.getMessage());
				}
				
			}
		logger.info("Validating Report Generation Path... Complete");
	}

	private void verifyTestableModEnvironment(TestableMod testMod) throws InvalidConfigurationException {
		
		
		logger.debug("Verifying Testable Mod Environments.");
		
		if(testMod.getEnvironments() .size()==0){
			throw new InvalidConfigurationException("NoEnvironmentsConfigured", testMod.getName()+" thrown at org.cognito.config.parsers"
					+ ".ConfigurationBuilder.verifyTestableMod"
					+ "(CognitoConfiguration config) throws "
					+ "InvalidConfigurationException");
		}
		logger.info("Verifying Testable Mod Environments... Complete");
	}

	private void verifyTestableMods() throws InvalidConfigurationException {
		logger.debug("Verifying Testable Mods.");
		
		if(configuration.getTestableMods().isEmpty()){
			
			throw new InvalidConfigurationException("NoModsConfigured", " thrown at org.cognito.config.parsers"
					+ ".ConfigurationBuilder.verifyTestableMod"
					+ "(CognitoConfiguration config) throws "
					+ "InvalidConfigurationException");
		}
		else{
			for(TestableMod testMod : configuration.getTestableMods()){
				verifyTestableModEnvironment(testMod);
			}
		}
		logger.info("Verifying Testable Mods... Complete");
	}

}