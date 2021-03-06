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
package org.cognito.core;

import org.apache.log4j.Logger;
import org.cognito.config.beans.CognitoConfiguration;
import org.cognito.config.exceptions.CognitoConfigurationException;
import org.cognito.config.parsers.ConfigurationBuilder;
import org.cognito.config.parsers.XmlConfigurationBuilder;
import org.cognito.core.exceptions.GenericCognitoRuntimeException;
import org.cognito.core.testrun.beans.TestMetadata;

/**
 * An instance of this class provides the configuration information {@link CognitoConfiguration} and 
 * the test metadata information {@link TestMetadata}.
 * 
 * @author Aditya Karnad
 */
public final class TestContext {
	
	static {
		logger = Logger.getLogger(TestContext.class);
	}
	private static Logger logger;
	
	static {
		
		ConfigurationBuilder configProvider = new XmlConfigurationBuilder();
		try {
			configuration = configProvider.buildConfiguration();
			testMetadata = new TestMetadata();
		}
		catch (CognitoConfigurationException e) {
			
			throw new GenericCognitoRuntimeException("UnknownConfigErr", e.getMessage());
		}
	}
	
	private static CognitoConfiguration configuration;
	private static TestMetadata testMetadata;
	
	/**
	 * Reads Cognito's configuration file from the classpath and returns the <tt>CognitoConfiguration</tt> instance.
	 * 
	 * @return <tt>CognitoConfiguration</tt> instance read from the configuration file.
	 * @throws CognitoConfigurationException
	 */
	public static CognitoConfiguration getConfiguration() throws CognitoConfigurationException {
		
		logger.info("Fetching Configuration.");
		
		if (configuration == null) {
			
			configuration = new XmlConfigurationBuilder().buildConfiguration();
		}
		logger.info("Fetching Configuration... Complete");
		
		return configuration;
	}
	
	
	/**
	 * Returns the {@link TestMetadata} instance.
	 * @return {@link TestMetadata} instance.
	 */
	public static TestMetadata getTestMetadata() {
		
		logger.info("Fetching Test Metadata.");
		if (testMetadata == null) {
			
			testMetadata = new TestMetadata();
		}
		logger.info("Fetching Test Metadata... Complete");
		
		return testMetadata;
	}
	
	/**
	 * Resets the {@link TestMetadata} instance.
	 */
	public static void resetTestConfiguration() {
		
		logger.info("Resetting Test Configuration.");
		testMetadata = null;
		
		logger.info("Resetting Test Configuration... Complete");
	}
}