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
package org.cognito.core.exceptions;

/**
 * Thrown when some preliminary checks made by the Cognito framework against the implementables provided by users fail.
 * 
 * @author Aditya Karnad
 */
public class InvalidImplementableException extends GenericCognitoRuntimeException {
	
	private static final long serialVersionUID = -6875816335077391074L;
	
	/**
	 * Constructs a {@link InvalidImplementableException} using an error code and a message.
	 * 
	 * @param errorCode represents an error code.
	 * @param message represents the arguments passed appropriate to the message corresponding to the error code 
	 * passed as argument.
	 */
	public InvalidImplementableException(String errorCode, Object... message) {
		
		super(errorCode, message);
	}
}