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

import mockups.mockintegration.in_an_application_far_far_away.mod_01.SentenceParser;

/**
 * This calculator calculates the resulting Integer value from the operation details provided in the request.
 * 
 * @author Natasha Panchbhai
 */
public class Calculator {

	/**
	 * Calculates the resulting Integer value from the operation details provided in the request.
	 * @param request
	 * @return Calculated result based on the {@link CalculatorRequest} argument passed.
	 * @throws InvalidInstructionException
	 */
	public int calculate(CalculatorRequest request) throws InvalidInstructionException {
		
		validateRequest(request);
		
		int result = (request.getOperationType() == SentenceParser.OPR_PROD)?1:0;
		
		for (Integer operand : request.getOperands()) {
		
			if (request.getOperationType() == SentenceParser.OPR_SUM) {
				
				result += operand;
			}
			else if (request.getOperationType() == SentenceParser.OPR_PROD) {
				
				result *= operand;
			}
		}
		
		if(request.getOperationType() == SentenceParser.OPR_DIFF) {
			
			result = request.getOperands().get(0) - request.getOperands().get(1);
		}
		else if(request.getOperationType() == SentenceParser.OPR_DIV) {
			
			result = request.getOperands().get(0) / request.getOperands().get(1);
		}
		
		return result;
	}
	
	
	private void validateRequest(CalculatorRequest request) throws InvalidInstructionException {
		
		byte operation = request.getOperationType();
		List<Integer> operands = request.getOperands();
		
		//subtraction with more than 2 operands
		if(operation == SentenceParser.OPR_DIFF && operands.size() > 2) {
			
			throw new InvalidInstructionException("Subtraction can only be performed with 2 operands!");
		}
		else if(operation == SentenceParser.OPR_DIV && operands.size() > 2) {
			
			throw new InvalidInstructionException("Division can only be performed with 2 operands!");
		}
	}
}