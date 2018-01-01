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

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;
import org.cognito.config.beans.CognitoConfiguration;
import org.cognito.config.beans.Environment;
import org.cognito.config.beans.ReportGenerationConfiguration;
import org.cognito.config.beans.TestableMod;
import org.cognito.config.exceptions.CognitoConfigurationException;

public final class XmlConfigurationBuilder extends ConfigurationBuilder {


	/*
Keys available in XML are:

>> testable-mods.testable-mod[@name]
>> testable-mods.testable-mod[@type]
>> testable-mods.testable-mod.environments.environment[@name]
>> testable-mods.testable-mod.environments.environment.env-spec[@detail]
>> testable-mods.testable-mod.environments.environment.env-spec[@id]
>> testable-mods.testable-mod.mod-specs.mod-spec[@detail]
>> testable-mods.testable-mod.mod-specs.mod-spec[@id]
>> report-generation[@path]
>> regression-test[@mod-name]
>> regression-test[@benchmark-env]
>> regression-test[@target-env]
>> regression-test.test-scope.test-set[@class]
>> system-test[@mod-name]
>> system-test[@target-env]
>> system-test.test-scope.test-set[@class]
>> integration-test.partipant-mods.integratable-mod[@mod-name]
>> integration-test.partipant-mods.integratable-mod[@env-name]
>> integration-test.test-scope.test-set[@class]
>> integration-test.mod-integrator[@class]
	 */
	
	private String configurationFileName;
	
	public XmlConfigurationBuilder() {

		//Default setting
		configurationFileName = "cognito-cfg.xml";
	}
	
	public static final Logger logger;
	
	static{
		
		logger = Logger.getLogger(XmlConfigurationBuilder.class);
	}
	
	public XmlConfigurationBuilder(String configurationFileName) {

		this.configurationFileName = configurationFileName;
	}
	
	@Override
	protected void readConfiguration() throws CognitoConfigurationException {

		logger.debug("Starting XML Configuration parse.");
		this.configuration = new CognitoConfiguration();
		
		FileBasedConfigurationBuilder<XMLConfiguration> builder = new Configurations()
				.xmlBuilder(this.configurationFileName);
		
		try {
			
			XMLConfiguration xmlConfig = builder.getConfiguration();

			//reading testable mods
			readTestableMods(xmlConfig, 0);

			//reading report generation configuration
			readReportGenerationConfigurations(xmlConfig);

			
			//reading test configurations
			//readTestConfigurations(xmlConfig);

			//showAllKeys(xmlConfig);
		} catch (ConfigurationException configException) {

			throw new CognitoConfigurationException("UnknownConfigErr", 
					configException.getMessage());
		}

		logger.info("Starting XML Configuration parse... Complete");
	}
	

	private void readReportGenerationConfigurations(XMLConfiguration xmlConfig) {
		
		logger.debug("Reading Report Generation Configurations.");
		String reportGenerationPath = xmlConfig.getString("report-generation[@path]");
		ReportGenerationConfiguration reportGenerationConfig = new ReportGenerationConfiguration();
		reportGenerationConfig.setReportGenerationPath(reportGenerationPath);
		this.configuration.setReportGenerationConfiguration(reportGenerationConfig);
		logger.info("Reading Report Generation Configurations... Complete");
	}

	private void readTestableMods(XMLConfiguration xmlConfig, int beginAt) {

		logger.debug("Reading Testable Mods.");
		int currentMod = beginAt;
		int nextMod = beginAt+1;

		if(xmlConfig.getString(
				"testable-mods.testable-mod("
						+ currentMod +")[@name]") != null) {
			
			TestableMod testableMod = new TestableMod();
			testableMod.setName(xmlConfig.getString(
					"testable-mods.testable-mod("+ currentMod +")[@name]"));
			String typeReadFromXml = xmlConfig.getString(
					"testable-mods.testable-mod("+ currentMod +")[@type]");
			testableMod.recognizeAndSetType(typeReadFromXml);
			this.configuration.getTestableMods().add(testableMod);
			readEnvironmentsInMod(xmlConfig, currentMod, 0);
			readModSpecification(xmlConfig, currentMod, 0);
			readTestableMods(xmlConfig, nextMod);
		}
		logger.info("Reading Testable Mods... Complete");
	}

	private void readModSpecification(
			XMLConfiguration xmlConfig, int modIndex, int beginAt) {
		logger.debug("Reading Mod Specification.");
		
		int currentSpec = beginAt;
		int nextSpec = beginAt+1;
		
		if(xmlConfig.getString(
				"testable-mods.testable-mod("+ modIndex +").mod-specs.mod-spec("
						+currentSpec+")[@id]") != null) {
			
			String specId = xmlConfig.getString(
					"testable-mods.testable-mod("+ modIndex +").mod-specs.mod-spec("
							+currentSpec+")[@id]");
			String specDetail = xmlConfig.getString(
					"testable-mods.testable-mod("+ modIndex +").mod-specs.mod-spec("
							+currentSpec+")[@detail]");
			
			this.configuration.getTestableMods().get(modIndex)
				.getModSpecification().put(specId, specDetail);
			
			readModSpecification(xmlConfig, modIndex, nextSpec);
			logger.info("Reading Mod Specification... Complete");
		}
	}

	private void readEnvironmentsInMod(
			XMLConfiguration xmlConfig, int modIndex, int beginAt) {

		logger.debug("Reading Environments present in Mod.");
		int currentEnv = beginAt;
		int nextEnv = beginAt+1;

		if(xmlConfig.getString(
				"testable-mods.testable-mod("+ modIndex +").environments.environment("
						+ currentEnv +")[@name]") != null) {
			Environment environment = new Environment();
			environment.setName(xmlConfig.getString(
					"testable-mods.testable-mod("+ modIndex +").environments.environment("
							+ currentEnv +")[@name]"));
			this.configuration.getTestableMods().get(modIndex)
			.getEnvironments().add(environment);

			readEnvironmentSpecs(xmlConfig, modIndex, currentEnv, 0);
			readEnvironmentsInMod(xmlConfig, modIndex, nextEnv);
		}
		logger.info("Reading Environments present in Mod... Complete");
	}

	private void readEnvironmentSpecs(
			XMLConfiguration xmlConfig, 
			int modIndex, 
			int envIndex, 
			int beginAt) {
		
		logger.debug("Reading Environment Specifiactions.");
		int currentSpec = beginAt;
		int nextSpec = beginAt + 1;
		
		if(xmlConfig.getString(
				"testable-mods.testable-mod("+ modIndex +").environments.environment("
						+ envIndex +").env-spec("+currentSpec+")[@id]") != null) {
			
			String specId = xmlConfig.getString(
					"testable-mods.testable-mod("+ modIndex +").environments.environment("
							+ envIndex +").env-spec("+currentSpec+")[@id]");
			String specDetail = xmlConfig.getString(
					"testable-mods.testable-mod("+ modIndex +").environments.environment("
							+ envIndex +").env-spec("+currentSpec+")[@detail]");
			
			this.configuration
				.getTestableMods().get(modIndex)
				.getEnvironments().get(envIndex)
				.getEnvironmentSpecification().put(specId, specDetail);
			
			readEnvironmentSpecs(xmlConfig, modIndex, envIndex, nextSpec);
		}
		logger.info("Reading Environment Specifiactions... Complete");
	}

}
