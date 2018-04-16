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
package org.cognito.core.testrun.implementables;

import java.util.Map;

import org.apache.log4j.Logger;
import org.cognito.config.beans.CognitoImplementable;
import org.cognito.core.TestContext;
import org.cognito.core.testrun.beans.ComparableField;


import static org.cognito.core.util.recorders.GenericRecorder.*;

/**
 * This class should be extended to define cognitive verifications for the test run.<br>
 * <i><b>Cognitive Verifications</b> are rules that reference the test input, 
 * and verify if the generated test output follows rules / patterns that 
 * are based on either previously run tests or applicable business rules 
 * governing the module targeted for testing.</i>
 * 
 * @author Aditya Karnad
 */
public abstract class CognitiveVerifier implements CognitoImplementable {
	
	static {
		
		logger = Logger.getLogger(ComparableField.class);
	}
	
	private static Logger logger;
	private static Map<String, Object> memory;
	
	/**
	 * Commits an object to test memory (for later recall) with a reference key.
	 * 
	 * @param key represents the reference with which the argument is committed.
	 * @param value represents the object to commit to memory.
	 */
	public static void commit(String key, Object value) {
		
		memory.put(key, value);
	}
	
	

	/**
	 * Retrieves a committed object from the test memory.
	 * 
	 * @param key represents the reference key with which the object was committed to test memory.
	 * @return Committed object from the test memory.
	 */
	public static Object recall(String key) {
		
		return memory.get(key);
	}
	
	
	/**
	 * This method is used to indicate a failure in a cognitive verification definition.
	 * 
	 * @param message represents the message which will be recorded in the cognitive failures log.
	 */
	public final void failVerification(String message) {
		
		logger.info("Failing verification: " + Thread.currentThread().getStackTrace()[2].getMethodName());
		StringBuffer failureLog = TestContext.getTestMetadata().getCognitiveVerificationFailureLog();
		
		
		if (failureLog == null) {
			
			failureLog = new StringBuffer();
			
			failureLog.append("**********"
						+ " C O G N I T O   F A I L U R E   L O G "
						+ "**********"
						+ System.lineSeparator());
			failureLog.append("TEST TYPE:\t" + deriveTestType(TestContext.getTestMetadata().getType()) + System.lineSeparator());
			failureLog.append("TEST SET:\t" + TestContext.getTestMetadata().getTestSet() + System.lineSeparator());
			failureLog.append("TEST ENV:\t" + TestContext.getTestMetadata().getTestConfiguration().getEnvironmentsDetail() + System.lineSeparator());
			failureLog.append("-----------------------------------------------------------" + System.lineSeparator());
		}
		
		if (TestContext.getTestMetadata().getCognitiveVerificationFailureCount() == 0) {
			
			failureLog.append(getScenarioFailureHeader());
		}
		
		//Incrementing failure count
		TestContext.getTestMetadata().setCognitiveVerificationFailureCount(
				TestContext.getTestMetadata().getCognitiveVerificationFailureCount() + 1);
		
		failureLog.append(System.lineSeparator()+TestContext.getTestMetadata().getCognitiveVerificationFailureCount()+"\t"+message);
		
		TestContext.getTestMetadata().setCognitiveVerificationFailureLog(failureLog);
		logger.info("*** Failure Count = " + TestContext.getTestMetadata().getCognitiveVerificationFailureCount() + " ***");
		
//		Assert.fail(Integer.toString(failureCount));
		
		logger.info("Failing verification... Complete.");
	}

	private Object getScenarioFailureHeader() {
		
		String header = System.lineSeparator();
		header = header.concat(System.lineSeparator() + "**********************************************************");
		header = header.concat(System.lineSeparator() + "Cognitive Failures for " + TestContext.getTestMetadata().getScenario());
		header = header.concat(System.lineSeparator() + "**********************************************************");
		return header;
	}
	
}