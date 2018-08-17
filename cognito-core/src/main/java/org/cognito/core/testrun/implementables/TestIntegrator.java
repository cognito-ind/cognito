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
package org.cognito.core.testrun.implementables;

import org.cognito.config.beans.CognitoImplementable;

/**
 * The {@link TestIntegrator} interface should be implemented by any class whose instances are intended to 
 * run Integration test executions on the Cognito framework.
 * 
 * @author Aditya Karnad
 * @see ModRunner
 */
public interface TestIntegrator extends CognitoImplementable {
	
	/**
	 * Returns the current version of the integration of modules being tested.
	 * @return String representing the current version of the target integration (configuration).
	 */
	public abstract String getVersion();
}