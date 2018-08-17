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


/**
 * An object of this type is used to wrap the input fed to the test run.
 * 
 * @see TestRunInput#modInput
 * @author Aditya Karnad
 */
public abstract class TestRunInput {
	
	/**
	 * Represents the module input / test input which could be of any generic type.
	 */
	private Object modInput;

	/**
	 * Returns the input fed to the test run.
	 * @return module input
	 */
	public Object getModInput() {
		return modInput;
	}
	
	/**
	 * Sets the input fed to the test run.
	 * @param modInput represents the module input.
	 */
	public void setModInput(Object modInput) {
		this.modInput = modInput;
	}
	
	/**
	 * Return the module input as a formatted String as per test requirement.
	 * @return returns the module input formatted as a String.
	 */
	public abstract String getFormattedStringEquivalent();
	
	@Override
	public String toString() {
		
		return modInput.toString();
		//TODO should this return getFormattedStringEquivalent()?
	}
}