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

public class ComparabilityException extends GenericCognitoException {
	
	private static final long serialVersionUID = -417617934046000817L;
	
	/**
	 * Thrown when two equivalent outputs fields (which should be storing the same type of data) 
	 * are found to be of different types which results in the framework not being able to compare 
	 * them with each other.
	 * 
	 * @param errorCode
	 * @param message
	 */
	public ComparabilityException(String errorCode, Object... message) {
		super(errorCode, message);
	}
}