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
package mockups.mockintegration.verifiers;

import java.util.List;

import org.cognito.core.annotations.Verification;
import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.implementables.CognitiveVerifier;

import mockups.mockintegration.in_an_application_far_far_away.mod_01.SentenceParser;

public class SentenceParserVerifier extends CognitiveVerifier {
	
	/**
	 * Searches the input for keywords indicating the operation requested and then checks the parsed output to check if 
	 * The same operation is actually recorded as the output from this module.
	 * @param record contains the input and the output of the test.
	 */
	@Verification
	public void rule_01_OperatorRecognition(TestRecord record) {
		
		//Check for SUM
		String input = (String) record.getTestRunInput().getModInput();
		
		if (input.toLowerCase().contains("sum")) {
			
			if (!record.getTestRunOutput().getOutputFields().get(0).getValue().equals(
					Byte.valueOf(SentenceParser.OPR_SUM))) {
				
				failVerification("Sum operator was not recognized correctly.");
			}
		}
		//Check for PRODUCT
		else if (input.toLowerCase().contains("product")) {
			
			if (!record.getTestRunOutput().getOutputFields().get(0).getValue().equals(
					Byte.valueOf(SentenceParser.OPR_PROD))) {
				
				failVerification("Product operator was not recognized correctly.");
			}
		}
		
		//TODO Neeraj: Pending verifications for DIVISION and SUBTRACTION
		
	}
	
	@Verification
	public void rule_02_MoreThanTwoOperands(TestRecord record) {
		
		@SuppressWarnings("unchecked")
		List<Integer> operands = (List<Integer>) record.getTestRunOutput().getOutputFields().get(1).getValue();
		if(operands.size() > 2) {
			
			failVerification("More than 2 operands.");
		}
	}
//	
//	@Verification
//	public void rule_03_InputSameAsOutput(TestRecord record) {
//		
//		String processedOutput = record.getTestRunOutput().getOutputFields().get(0).getValue().toString();
//		String providedInput = record.getTestRunInput().getModInput().toString();
//		
//		//if after mod run the input remains the same as output, fail the verification.
//		if (providedInput.equalsIgnoreCase(processedOutput)) {
//			
//			failVerification("Input provided \"" + providedInput + "\" was found to be same as output \""+ processedOutput + "\"");
//		}
//	}
//	
//	@Verification
//	public void rule_04_InputContainingMockTest(TestRecord record) {
//		
//		String providedInput = record.getTestRunInput().getModInput().toString();
//		
//		//if after mod run the input remains the same as output, fail the verification.
//		if (providedInput.contains("MOCKTEST")) {
//			
//			failVerification("Input provided \"" + providedInput + "\" contains MOCKTEST");
//		}
//	}
	
}