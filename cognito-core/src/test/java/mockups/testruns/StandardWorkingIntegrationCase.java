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

import org.cognito.config.annotations.IntegrationTest;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.testrun.beans.TestRunInput;
import org.cognito.core.testrun.implementables.TestIntegrator;
import org.junit.Before;
import org.junit.Test;

import mockups.entities.MockInput;
import mockups.mockintegration.testintegrators.InstructionParsingIntegrator;

@IntegrationTest(
		participantMods= {"sampleWsMod:PROD","sampleUiMod:AITE"},
		testIntegrator= InstructionParsingIntegrator.class
		)
public class StandardWorkingIntegrationCase extends RunnableTestSet {
	
	
	@Before
	public void setUp() {
		
		TestRunInput input = new MockInput();
		input.setModInput("what is the product of 10 and 15 and 3");
		record.setTestRunInput(input);
	}
	
	//Standard Working case for Integration Test
	@Test
	public void test_01_base() {
		
		//TODO Aditya: single record containing an integrated input and output.
		
	}
}