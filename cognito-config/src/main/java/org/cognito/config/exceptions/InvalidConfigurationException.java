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
package org.cognito.config.exceptions;

/**
 * Thrown when a configuration exception is encountered on Cognito where the configurations are invalid.
 * 
 * @author Aditya Karnad
 */
public class InvalidConfigurationException extends CognitoConfigurationException {
	
	private static final long serialVersionUID = 7673137676828329005L;

	/**
	 * Constructs a {@linkplain InvalidConfigurationException} using an error code and a message.
	 * 
	 * @param errorCode represents an error code.
	 * @param args represents the arguments passed appropriate to the message corresponding to the error code passed as argument.
	 */
	public InvalidConfigurationException(String errorCode, Object... args) {
		
		super(errorCode, args);
	}
}