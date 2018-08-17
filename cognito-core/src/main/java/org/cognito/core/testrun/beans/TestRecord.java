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

/**
 * An instance of this type would contain the test input ({@link TestRunInput}) and 
 * test output ({@link TestRunOutput}) that are associated with a test run.
 * 
 * @author Aditya Karnad
 */
public final class TestRecord {
	
	protected TestRunInput testRunInput;
	protected TestRunOutput testRunOutput;
	protected TestRunOutput benchmarkOutput=null;
	
	/**
	 * Returns the test run input associated with the test.
	 * @return Test run input as {@link TestRunInput}.
	 */
	public TestRunInput getTestRunInput() {
		return testRunInput;
	}
	
	/**
	 * Sets the test run input associated with the test.
	 * @param testRunInput represents the test run input as {@link TestRunInput}
	 */
	public void setTestRunInput(TestRunInput testRunInput) {
		this.testRunInput = testRunInput;
	}
	
	/**
	 * Returns the test run output associated with the test.
	 * @return Test run output as {@link TestRunOutput}.
	 */
	public TestRunOutput getTestRunOutput() {
		return testRunOutput;
	}
	
	/**
	 * Sets the test run output associated with the test.
	 * @param testRunOutput represents the test run output as {@link TestRunOutput}
	 */
	public void setTestRunOutput(TestRunOutput testRunOutput) {
		this.testRunOutput = testRunOutput;
	}
	
	/**
	 * Returns the benchmark output (if available).
	 * @return benchmark output as {@link TestRunOutput}.
	 */
	public TestRunOutput getBenchmarkOutput() {
		return benchmarkOutput;
	}
	
	/**
	 * Sets the benchmark output.
	 * @param output represents benchmark output as {@link TestRunOutput}.
	 */
	public void setBenchmarkOutput(TestRunOutput output) {
		this.benchmarkOutput = output;
	}
	
	@Override
	public String toString() {
		
		String record = "\n**** Input ****\n" + testRunInput 
				+ "\n**** Output ****\n" + testRunOutput;
		return record;
	}
}