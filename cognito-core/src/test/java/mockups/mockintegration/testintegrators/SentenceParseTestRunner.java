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

import java.util.List;

import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.implementables.SystemTestRunner;

import mockups.mockintegration.entities.SentenceParserInput;
import mockups.mockintegration.entities.SentenceParserOutput;
import mockups.mockintegration.entities.SentenceParserProcessedOutput;
import mockups.mockintegration.in_an_application_far_far_away.mod_01.SentenceParser;

/**
 * This is an optional System test runner, for circumstances that demands an initial system test of the Sentence 
 * Parser module before the integration is tested. <br>
 * 
 * <b>This is optional</b>
 * 
 * @author aditya.karnad
 *
 */
public class SentenceParseTestRunner extends SystemTestRunner {
	
	@Override
	protected void executeTest(TestRecord record) {
		
		//assumes the record's input is already set
		
		//step 1a: get input from record.
		String modInput = (String) ((SentenceParserInput)record.getTestRunInput()).getModInput();
		
		//step 1b: Make empty output object for populating output.
		SentenceParserOutput modOutput = new SentenceParserOutput();
		
		//step 2: use the target module to process output from the input derived from step 1a.
		byte identifiedOperation = new SentenceParser().getOperation(modInput);
		List<Integer> derivedOperands = new SentenceParser().getOperands(modInput);
		
		//step 3: Pack outputs in a POJO (if outputs do not naturally form one - like in this case, a byte and a list)
		SentenceParserProcessedOutput processedOutput = new SentenceParserProcessedOutput();
		processedOutput.setOperands(derivedOperands);
		processedOutput.setOperator(identifiedOperation);
		
		//step 4: 
		modOutput.extractOutputFieldsFromModOutput(processedOutput);
		record.setTestRunOutput(modOutput);
	}

	@Override
	public String getModVersion() {
		
		return "sParse_v0.4.1";
	}
}