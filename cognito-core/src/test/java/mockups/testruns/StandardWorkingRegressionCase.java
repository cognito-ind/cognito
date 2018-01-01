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
package mockups.testruns;

import org.cognito.config.annotations.RegressionTest;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.testrun.beans.TestRunInput;
import org.junit.Before;
import org.junit.Test;

import mockups.entities.MockInput;
import mockups.modoperators.MockRegresionTestableModOperator;

@RegressionTest (
		modName="sampleUiMod", 
		benchMarkEnvironment="SITE", 
		targetEnvironment="AITE",
		moduleRunner=MockRegresionTestableModOperator.class
		)
public class StandardWorkingRegressionCase extends RunnableTestSet {
	
	//Standard Working case for Regression Test
	
	@Before
	public void setUp() {
		
		TestRunInput input = new MockInput();
		input.setModInput("flag=true;numInt=12;list=a,s,d,f,g");
		record.setTestRunInput(input);
	}
	
	@Test
	public void test_01_base() {
		
		//base case - no changes
	}
	
	@Test
	public void test_02_Sample() {
		
		record.getTestRunInput().setModInput("numInt=76;flag=false");
	}
}