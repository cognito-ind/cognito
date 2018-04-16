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
 * Thrown when an exception is encountered during test run initialization.
 * 
 * @author Aditya Karnad
 */
public class TestRunInitializationException extends GenericCognitoRuntimeException {
	
	private static final long serialVersionUID = 1154162914283976973L;
	
	/**
	 * Constructs a {@link TestRunInitializationException} using an error code and a message.
	 * 
	 * @param errorCode represents an error code.
	 * @param message represents the arguments passed appropriate to the message corresponding to the error code 
	 * passed as argument.
	 */
	public TestRunInitializationException(String errorCode, Object... message) {
		super(errorCode, message);
	}
}