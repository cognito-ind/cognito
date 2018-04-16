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
 * The {@link ModRunner} interface should be implemented by any class whose instances are intended to 
 * run test executions for System or Regression tests on the Cognito framework.
 * @author Aditya Karnad
 * @see TestIntegrator
 */
public interface ModRunner extends CognitoImplementable {

	/**
	 * Returns the current version of the mod.
	 * @return String representing the current version of the mod.
	 */
	public abstract String getModVersion();
}