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

import org.cognito.config.beans.CognitoImplementable;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.TestContext;
import org.cognito.core.exceptions.InvalidImplementableException;
import org.cognito.core.testrun.beans.MultiOutputAnalyzer;
import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.beans.TestRunOutput;

public abstract class TestRunner implements CognitoImplementable {
	
	/**
	 * Executes the test on the module and records the resulting output(s) to the test log.
	 * @param record
	 */
	public final void runTest(TestRecord record) {
		
		executeTest(record);
		
		validateExecutionImplementation(record);
		
		
		if (record.getBenchmarkOutput()!=null) {
			
			saveFormattedOutputComparisonToLog(record.getTestRunOutput(), record.getBenchmarkOutput());
		}
		else {
			
			saveToLog(record.getTestRunOutput());
		}
	}
	
	/**
	 * Validates whether the implemented {@link TestRunner#executeTest(TestRecord)} 
	 * provides an output and a benchmark output.
	 * @param record represents the processed {@link TestRecord} which should have a valid output and/or benchmark output. 
	 */
	private void validateExecutionImplementation(TestRecord record) {
		
		if (record.getTestRunOutput() == null) {
			
			throw new InvalidImplementableException("InvalidImplementableOutputNotFound");
		}
		
		if (TestContext.getTestMetadata().getType() == RunnableTestSet.TYPE_RegressionTest) {
			
			if (record.getBenchmarkOutput() == null) {
				
				throw new InvalidImplementableException("InvalidImplementableBenchmarkNotFound");
			}
		}
	}
	
	/**
	 * This method executes the test on the mod.
	 * <br>
	 * <i><b>Note:</b> The implementation for this method needs to be provided by the user.</i>
	 * @param record
	 */
	protected abstract void executeTest(TestRecord record);

	/**
	 * This method is used to save messages to the test log which will be recorded in the test run report.
	 * @param message
	 */
	public final static void saveToLog(Object message) {
		
		TestContext.getTestMetadata().getTestLog().append(System.lineSeparator() + message);
	}
	
	/**
	 * Generates a formatted output comparison of two <tt>TestRunOutput</tt> objects passed as arguments.
	 * This comparison is recorded on the test log <i>(and will subsequently feature in the test report)</i>.
	 * @param targetOutput
	 * @param benchmarkOutput
	 */
	private final void saveFormattedOutputComparisonToLog(TestRunOutput targetOutput, TestRunOutput benchmarkOutput) {
		
		MultiOutputAnalyzer outputAnalyzer = new MultiOutputAnalyzer(targetOutput, benchmarkOutput);
		saveToLog(outputAnalyzer);
	}
}