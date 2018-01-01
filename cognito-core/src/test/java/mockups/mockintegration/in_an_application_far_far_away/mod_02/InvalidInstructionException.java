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

/**
 * Thrown when the instruction provided as input is invalid.
 * @author Aditya Karnad
 *
 */
public class InvalidInstructionException extends Exception {
	
	private static final long serialVersionUID = 6363262080724953673L;
	
	public InvalidInstructionException(String message) {
		
		super(message);
	}
}