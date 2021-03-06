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

import org.cognito.config.annotations.SystemTest;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.testrun.beans.TestRunInput;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import mockups.entities.MockInput;
import mockups.mockintegration.entities.SentenceParserInput;
import mockups.mockintegration.testintegrators.SentenceParseTestRunner;
import mockups.mockintegration.verifiers.SentenceParserVerifier;
import mockups.modoperators.ModOperatorMockup;
import mockups.verifiers.MockCognitiveVerifier;

@SystemTest(
		modName="sentenceParser",
		targetEnvironment="DEV",
		cognitiveVerifier=SentenceParserVerifier.class,
		moduleRunner=SentenceParseTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SentenceParserSystemTestCases extends RunnableTestSet {
	
	@Before
	public void setUp() {
		
		TestRunInput input = new SentenceParserInput();
		input.setModInput("fetch the sum of 13 and 7");
		record.setTestRunInput(input);
	}
	
	
	//Standard Working case for System Test
	@Test
	public void test_01_base() {
		
		//No Change to the input - just running with defined base...
	}
	
	@Test
	public void test_02_threeOperandsWithDivision() {
		
		record.getTestRunInput().setModInput("fetch the result of 80 divided by 13 divided by 7");
	}
	
	@Test
	public void test_03_DivisionByZero() {
		
		record.getTestRunInput().setModInput("fetch the result of 75 divided by 0");
	}
	
	@Test
	public void test_04_threeOperandsWithSubtraction() {
		
		record.getTestRunInput().setModInput("fetch the difference between 45, 9 and 66");
	}
	
//	
//	@Test
//	public void test_03_cognitiveFailureTest() {
//		
//		record.getTestRunInput().setModInput("INPUT [[ This should fail... ]]");
//	}
//	
//	@Test
//	public void test_04_cognitiveFailureTest() {
//		
//		record.getTestRunInput().setModInput("OUTPUT [[ This should fail... ]]");
//	}
//	
//	@Test
//	public void test_05_implNewRuleTest() {
//		
//		record.getTestRunInput().setModInput("MOCKTEST [[ This should fail... ]]");
//	}
}