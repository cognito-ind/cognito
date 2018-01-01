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
import org.cognito.core.testrun.implementables.CognitiveVerifier;

public class TestMetadata {
	
	private byte type;
	private String testSet;
	private String scenario;
	private String version;
	private TestConfiguration testConfiguration;
	private int cognitiveVerificationFailureCount;
	private StringBuffer cognitiveVerificationFailureLog;
	private StringBuffer testLog;
	
	static {
		logger = Logger.getLogger(ComparableField.class);
	}
	
	private static Logger logger;
	
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

	public byte getType() {
		return type;
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public String getTestSet() {
		return testSet;
	}

	public void setTestSet(String testSet) {
		this.testSet = testSet;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getCognitiveVerificationFailureCount() {
		return cognitiveVerificationFailureCount;
	}

	public void setCognitiveVerificationFailureCount(int cognitiveVerificationFailureCount) {
		this.cognitiveVerificationFailureCount = cognitiveVerificationFailureCount;
	}

	public StringBuffer getCognitiveVerificationFailureLog() {
		return cognitiveVerificationFailureLog;
	}

	public void setCognitiveVerificationFailureLog(StringBuffer cognitiveVerifictionFailureLog) {
		this.cognitiveVerificationFailureLog = cognitiveVerifictionFailureLog;
	}

	public StringBuffer getTestLog() {
		return testLog;
	}

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