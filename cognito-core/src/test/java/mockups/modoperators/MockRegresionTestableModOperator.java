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

import java.util.Arrays;
import java.util.Map;

import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.implementables.ModRunner;
import org.cognito.core.testrun.implementables.RegressionTestRunner;

import mockups.entities.MockOutputWithList;
import mockups.entities.MultiTypeOutput;
import mockups.entities.ValidMockOutput;

public class MockRegresionTestableModOperator extends RegressionTestRunner implements ModRunner{

	@Override
	public String getModVersion() {
		
		return "v0.1";
	}
	
	
	

	@Override
	protected void executeTest(TestRecord record) {
		
		saveToLog("Test input for run: " + record.getTestRunInput().getModInput());
//		saveToLog("BenchmarkEnv: " + getBenchmarkEnvironmentSpecifications().get("url"));
//		saveToLog("TargetEnv: " + getTargetEnvironmentSpecifications().get("url"));
//		saveToLog("AcceptableDeviation: " + getModSpecifications().get("acceptableDeviation"));
		
		//Processing output from input...
		String input = (String) record.getTestRunInput().getModInput();
		MultiTypeOutput outputFromTargetEnv = new MultiTypeOutput();
		MultiTypeOutput outputFromBenchmarkEnv = new MultiTypeOutput();
		
		//Step 1: Process output on target environment
		processOutputFromEnvironment(input, outputFromTargetEnv, getTargetEnvironmentSpecifications());
		MockOutputWithList targetOutput = new MockOutputWithList();
		targetOutput.extractOutputFieldsFromModOutput(outputFromTargetEnv);
		//Step 2: Process output on benchmark environment
		processOutputFromEnvironment(input, outputFromBenchmarkEnv, getBenchmarkEnvironmentSpecifications());
		ValidMockOutput benchmarkOutput = new ValidMockOutput();
		benchmarkOutput.extractOutputFieldsFromModOutput(outputFromBenchmarkEnv);
		
		record.setTestRunOutput(targetOutput);
		record.setBenchmarkOutput(benchmarkOutput);
	}
	
	
	/**
	 **************** This is what the mod would do ********
	 */
	private void processOutputFromEnvironment(
			String input, MultiTypeOutput modOutput, Map<String, String> environmentSpecs) {
		
		String[] fields = input.split(";");
		
		for (String field  : fields) {
			
			if(field.contains("list=")) {
				
				String[] listElements = field.split("=")[1].split(",");
				modOutput.setList(Arrays.asList(listElements));
			}
			else if (field.contains("flag=")) {
				
				if(field.split("=")[1].equalsIgnoreCase("true")) {
					modOutput.setFlag(true);
				}
				else {
					modOutput.setFlag(false);
				}
			}
			else if (field.contains("numInt=")) {
				
				modOutput.setNumericInteger(Integer.parseInt(field.split("=")[1]));
			}
		}
		
		modOutput.setNumericDouble(Double.parseDouble(getModSpecifications().get("acceptableDeviation")));
		modOutput.setText(environmentSpecs.get("url"));
	}
}