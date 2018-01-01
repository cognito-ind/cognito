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
package mockups.modoperators;

import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.beans.TestRunOutput;
import org.cognito.core.testrun.implementables.SystemTestRunner;

import mockups.entities.MockOutput;

public class ModOperatorMockup extends SystemTestRunner {

	@Override
	public String getModVersion() {
		
		return "v1.2.3";
	}

	@Override
	protected void executeTest(TestRecord record) {
		
		saveToLog("Test input for run: " + record.getTestRunInput().getModInput());
		saveToLog("Processing - in this case just replacing the INPUT with OUTPUT...");
		
		TestRunOutput output = new MockOutput();
		
		//Actual processing of the output.
		String outputString = ((String) record.getTestRunInput().getModInput()).replaceFirst("INPUT", "OUTPUT");
		
		//Forming cognito output
		output.extractOutputFieldsFromModOutput(outputString);
		
		record.setTestRunOutput(output);
	}
}