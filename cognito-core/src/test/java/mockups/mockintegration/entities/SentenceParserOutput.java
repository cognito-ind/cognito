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
package mockups.mockintegration.entities;

import java.util.ArrayList;

import org.cognito.core.testrun.beans.ComparableField;
import org.cognito.core.testrun.beans.TestRunOutput;

public class SentenceParserOutput extends TestRunOutput {

	@Override
	public void extractOutputFieldsFromModOutput(Object modOutput) {
		
		//the Object modOutput is to be assumed a user defined POJO containing processed output.
		SentenceParserProcessedOutput processedOutput = (SentenceParserProcessedOutput) modOutput;
		
		outputFields = new ArrayList<ComparableField>();
		outputFields.add(new ComparableField("operation", processedOutput.getOperator()));
		outputFields.add(new ComparableField("operands", processedOutput.getOperands()));
	}
}