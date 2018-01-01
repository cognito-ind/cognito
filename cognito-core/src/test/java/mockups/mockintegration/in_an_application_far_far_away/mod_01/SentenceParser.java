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
package mockups.mockintegration.in_an_application_far_far_away.mod_01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class parses english instructions to add, subtract, multiply and divide numbers.
 * 
 * @author Neeraj Suthar
 */
public class SentenceParser {

	
	private static final String[] ops = {"sum", "difference", "product","divided by"};
	public static final byte OPR_SUM = 1;
	public static final byte OPR_DIFF = 2;
	public static final byte OPR_PROD = 3;
	public static final byte OPR_DIV = 4;
	
	/**
	 * Returns the operation requested on the instruction.
	 * @param input represents the instruction argument passed.
	 * @return the byte equivalent to operation requested.
	 *
	 */
	public byte getOperation(String input) {
		
		for (int index=0; index < ops.length; index++) {
			
			if(input.toLowerCase().contains(ops[index])) {
				
				return (byte) (index+1);
			}
		}
		
		return 0;
	}
	
	/**
	 * Returns a List of Integer operands from the input String.
	 * @param input
	 * @return a List of all Integers in the input string.
	 */
	public List<Integer> getOperands(String input) {
		
		String temp = input.replaceAll("[^0-9]+", "#");
		List <Integer> operands = new ArrayList<Integer>();
		
		for (String string : temp.split("#")) {
			
			if (!string.isEmpty()) {
				
				operands.add(Integer.parseInt(string));
			}
		}
		
		return operands;
	}
}