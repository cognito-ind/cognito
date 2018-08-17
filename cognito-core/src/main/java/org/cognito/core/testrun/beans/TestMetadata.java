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
package org.cognito.core.testrun.beans;

import org.apache.log4j.Logger;
import org.cognito.config.beans.IntegrationTestConfiguration;
import org.cognito.config.beans.RegressionTestConfiguration;
import org.cognito.config.beans.SystemTestConfiguration;
import org.cognito.config.beans.TestConfiguration;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.testrun.implementables.ModRunner;
import org.cognito.core.testrun.implementables.TestIntegrator;

/**
 * An instance of this class would store information about the test run, 
 * 
 * @author Aditya Karnad
 */
public class TestMetadata {
	
	/**
	 * Type of the test.
	 */
	private byte type;
	
	/**
	 * Name of the test set being run.
	 */
	private String testSet;
	
	/**
	 * Name of the test scenario being run.
	 */
	private String scenario;
	
	/**
	 * Version of the module ({@link ModRunner}) / integrated system ({@link TestIntegrator}) on which the test is run.
	 */
	private String version;
	
	/**
	 * Test configurations as set by the user.
	 */
	private TestConfiguration testConfiguration;
	
	/**
	 * Count of all the cognitive verification failures encountered during test run.
	 */
	private int cognitiveVerificationFailureCount;
	
	/**
	 * Cognitive verification failure log which provides details of the verification failures during test run.
	 */
	private StringBuffer cognitiveVerificationFailureLog;
	
	/**
	 * The test log generated from the test run.
	 */
	private StringBuffer testLog;
	
	static {
		logger = Logger.getLogger(ComparableField.class);
	}
	
	private static Logger logger;
	
	/**
	 * Returns the test configuration.
	 * @return Test configuration based on type of the test.
	 */
	public TestConfiguration getTestConfiguration() {
		
		logger.debug("Fetching Test Configuration based on the Test Type.");
		
		if (testConfiguration == null) {
		
			if (type == RunnableTestSet.TYPE_IntegrationTest) {
				
				testConfiguration = new IntegrationTestConfiguration();
			}
			else if (type == RunnableTestSet.TYPE_SystemTest) {
				
				testConfiguration = new SystemTestConfiguration();
			}
			else if (type == RunnableTestSet.TYPE_RegressionTest) {
				
				testConfiguration = new RegressionTestConfiguration();
			}
		}
		
		logger.info("Fetching Test Configuration based on the Test Type... Complete");
		
		return testConfiguration;
	}
	
	/**
	 * Returns the type of test.
	 * @return Type of test
	 */
	public byte getType() {
		
		return type;
	}
	
	/**
	 * Sets the type of test.
	 * @param type Represents the type of test.
	 * @see RunnableTestSet#TYPE_SystemTest
	 * @see RunnableTestSet#TYPE_RegressionTest
	 * @see RunnableTestSet#TYPE_IntegrationTest
	 */
	public void setType(byte type) {
		
		this.type = type;
	}
	
	/**
	 * Returns the name of the test set.
	 * @return Name of the test set.
	 */
	public String getTestSet() {
		
		return testSet;
	}
	
	/**
	 * Sets the name of the test set.
	 * @param testSet represents the name of the test set.
	 */
	public void setTestSet(String testSet) {
		
		this.testSet = testSet;
	}
	
	/**
	 * Returns the scenario name.
	 * @return Scenario name being run.
	 */
	public String getScenario() {
		
		return scenario;
	}
	
	/**
	 * Sets the scenario name.
	 * @param scenario represents the name of the scenario.
	 */
	public void setScenario(String scenario) {
		
		this.scenario = scenario;
	}
	
	/**
	 * Returns the version of the <b>Test Module</b> / <b>Test Integrator</b>.
	 * @return version of the <b>Test Module</b> / <b>Test Integrator</b>
	 */
	public String getVersion() {
		
		return version;
	}
	
	/**
	 * Sets the version of the <b>Test Module</b> / <b>Test Integrator</b> (configuration).
	 * @param version represents the version of the test module or test integrator which runs the various 
	 * modules of an integrated system.
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * Returns the cognitive verification failure count.
	 * @return number of cognitive verifications failed during test run.
	 */
	public int getCognitiveVerificationFailureCount() {
		return cognitiveVerificationFailureCount;
	}
	
	/**
	 * Sets the cognitive verification failure count.
	 * @param cognitiveVerificationFailureCount represents the number of cognitive verification 
	 * failures encountered during run.
	 */
	public void setCognitiveVerificationFailureCount(int cognitiveVerificationFailureCount) {
		this.cognitiveVerificationFailureCount = cognitiveVerificationFailureCount;
	}
	
	/**
	 * Returns the cognitive verification failure log.
	 * @return Cognitive verification failure log
	 */
	public StringBuffer getCognitiveVerificationFailureLog() {
		return cognitiveVerificationFailureLog;
	}
	
	/**
	 * Sets the cognitive verification failure log.
	 * @param cognitiveVerifictionFailureLog represents the cognitive verification failure log buffer to be set.
	 */
	public void setCognitiveVerificationFailureLog(StringBuffer cognitiveVerifictionFailureLog) {
		this.cognitiveVerificationFailureLog = cognitiveVerifictionFailureLog;
	}
	
	/**
	 * Returns the test run log.
	 * @return Test run log.
	 */
	public StringBuffer getTestLog() {
		return testLog;
	}
	
	/**
	 * Sets the test run log.
	 * @param testLog represents the test run log to be set.
	 */
	public void setTestLog(StringBuffer testLog) {
		this.testLog = testLog;
	}

	@Override
	public String toString() {
		
		StringBuffer metadata= 
				new StringBuffer(System.lineSeparator());
		metadata.append(System.lineSeparator() + "*****************************************************************");
		metadata.append(System.lineSeparator() + "SCENARIO:\t" + scenario + System.lineSeparator());
		metadata.append("*****************************************************************" 
				+ System.lineSeparator());
		return metadata.toString();
	}
}