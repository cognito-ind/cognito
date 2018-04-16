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
package org.cognito.core.testrun.beans;

import java.util.List;

/**
 * An object of this type is used to wrap the output from the test run.
 * The output from tests run on a module is fragmented into various fields ({@link ComparableField}) which have 
 * a reference and a value.
 * 
 * @author Aditya Karnad
 */
public abstract class TestRunOutput {
	
	/**
	 * Represents the list of output fields of type {@link ComparableField}.
	 */
	protected List<ComparableField> outputFields;
	
	/**
	 * Returns the list of fields associated with this instance of output.
	 * @return List of output fields.
	 */
	public List<ComparableField> getOutputFields() {
		
		return this.outputFields;
	}
	
	/**
	 * <b>End User implementable: </b> 
	 * Defines how the framework understands the <tt>modOutput</tt>.
	 * This method should define the transformation of the module's run 
	 * output object into a {@link TestRunOutput} object.
	 * 
	 * @param modOutput represents the output provided by the module targeted for testing.
	 */
	public abstract void extractOutputFieldsFromModOutput(Object modOutput);
	
	@Override
	public String toString() {
		
		StringBuffer output = new StringBuffer(
				System.lineSeparator()
				+ "---------- TEST RUN OUTPUT ----------"
				+ System.lineSeparator());
		
		for (ComparableField field : outputFields) {
			
			output.append(System.lineSeparator() + field.id + "\t" + field.value);
		}
		
		return output.toString();
	}
}