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
 * An object of this type would contain the integral components to the test run.
 * 
 * @author Aditya Karnad
 *
 */
public final class TestRecord {
	
	protected TestRunInput testRunInput;
	protected TestRunOutput testRunOutput;
	protected TestRunOutput benchmarkOutput=null;
	
	
	public TestRunInput getTestRunInput() {
		return testRunInput;
	}
	public void setTestRunInput(TestRunInput testRunInput) {
		this.testRunInput = testRunInput;
	}
	public TestRunOutput getTestRunOutput() {
		return testRunOutput;
	}
	public void setTestRunOutput(TestRunOutput testRunOutput) {
		this.testRunOutput = testRunOutput;
	}
	public TestRunOutput getBenchmarkOutput() {
		return benchmarkOutput;
	}
	public void setBenchmarkOutput(TestRunOutput expectedOutput) {
		this.benchmarkOutput = expectedOutput;
	}
	
	@Override
	public String toString() {
		
		String record = "\n**** Input ****\n" + testRunInput 
				+ "\n**** Output ****\n" + testRunOutput;
		return record;
	}
}