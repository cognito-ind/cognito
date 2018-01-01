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
package mockups.mockintegration.testintegrators;

import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.beans.TestRunOutput;
import org.cognito.core.testrun.implementables.TestRunner;

import mockups.entities.MockOutput;
import mockups.mockintegration.entities.CalculationOutput;
import mockups.mockintegration.in_an_application_far_far_away.mod_01.SentenceParser;
import mockups.mockintegration.in_an_application_far_far_away.mod_02.Calculator;
import mockups.mockintegration.in_an_application_far_far_away.mod_02.CalculatorRequest;
import mockups.mockintegration.in_an_application_far_far_away.mod_02.InvalidInstructionException;

import org.cognito.core.testrun.implementables.TestIntegrator;

public class InstructionParsingIntegrator extends TestRunner implements TestIntegrator{

	

	@Override
	protected void executeTest(TestRecord record) {
		
		saveToLog("Input for run: " + record.getTestRunInput().getModInput());
		saveToLog("Integration of mods parses the input and returns the resulting integer.");
		
		TestRunOutput output = new CalculationOutput();
		
		
		//parsing instruction using mod 1 (Sentence Parser), output from mod 1 directed to input of mod 2 (Calculator)
		String instruction = ((String) record.getTestRunInput().getModInput());
		CalculatorRequest request = new CalculatorRequest();
		request.setOperands(new SentenceParser().getOperands(instruction));
		request.setOperationType(new SentenceParser().getOperation(instruction));
		
		//Result from mod 2 (final output from the integration)...
		int calculationResult = 0;
		try {
			calculationResult = new Calculator().calculate(request);
		}
		catch (InvalidInstructionException e) {
			
			throw new RuntimeException(e.getMessage());
		}
		
		//...saved into the output
		output.extractOutputFieldsFromModOutput(new Integer(calculationResult));
		
		//Save output in record.
		record.setTestRunOutput(output);
	}

	@Override
	public String getVersion() {
		
		return "v0.0.1";
	}
}