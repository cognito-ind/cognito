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
package mockups.mockintegration.in_an_application_far_far_away.mod_02;

import java.util.List;

/**
 * Mock request to the Calculator module.
 * 
 * @author Aditya Karnad
 */
public class CalculatorRequest {
	
	private byte operationType;
	private List<Integer> operands;
	
	/**
	 * Returns the operation type associated with the request.
	 * @return operation type
	 */
	public byte getOperationType() {
		return operationType;
	}
	/**
	 * Sets the operation type associated with the calculation request.
	 * @param operationType represents the operation type associated with the calculation request.
	 */
	public void setOperationType(byte operationType) {
		this.operationType = operationType;
	}
	/**
	 * Get the operands of the calculation from the request.
	 * @return operands on which calculation takes place.
	 */
	public List<Integer> getOperands() {
		return operands;
	}
	
	/**
	 * Sets the operands associated with the calculation request.
	 * @param operands associated with the calculation request.
	 */
	public void setOperands(List<Integer> operands) {
		this.operands = operands;
	}
}