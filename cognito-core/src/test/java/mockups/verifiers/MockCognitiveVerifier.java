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
package mockups.verifiers;

import org.cognito.core.annotations.Verification;
import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.implementables.CognitiveVerifier;

public class MockCognitiveVerifier extends CognitiveVerifier {
	
	
	@Verification
	public void rule_01_First(TestRecord record) {
		
		// Some rule here...
		
	}
	
	@Verification
	public void rule_02_Second(TestRecord record) {
		
		// Another rule here...
		
	}
	
	@Verification
	public void rule_03_InputSameAsOutput(TestRecord record) {
		
		String processedOutput = record.getTestRunOutput().getOutputFields().get(0).getValue().toString();
		String providedInput = record.getTestRunInput().getModInput().toString();
		
		//if after mod run the input remains the same as output, fail the verification.
		if (providedInput.equalsIgnoreCase(processedOutput)) {
			
			failVerification("Input provided \"" + providedInput
					+ "\" was found to be same as output \""+ processedOutput + "\"");
		}
	}
	
	@Verification
	public void rule_04_InputContainingMockTest(TestRecord record) {
		
		String providedInput = record.getTestRunInput().getModInput().toString();
		
		//if after mod run the input remains the same as output, fail the verification.
		if (providedInput.contains("MOCKTEST")) {
			
			failVerification("Input provided \"" + providedInput + "\" contains MOCKTEST");
		}
	}
	
}