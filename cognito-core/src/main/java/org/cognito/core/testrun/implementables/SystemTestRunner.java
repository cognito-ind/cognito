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

import java.util.Map;

import org.cognito.config.beans.SystemTestConfiguration;
import org.cognito.core.TestContext;

/**
 * This class represents a component which runs a System test.<br>
 * <i>The implementation for the test execution would have to be provided by the user.</i>
 * @author Aditya Karnad
 */
public abstract class SystemTestRunner extends TestRunner implements ModRunner {
	
	/**
	 * Returns the <tt>Map</tt> of environment specifications for the configured mod 
	 * instance <i>(Module -> Target Environment)</i>.
	 * @return <tt>Map</tt> of environment specifications
	 */
	public final Map<String, String> getTargetEnvironmentSpecifications() {
		
		Map<String, String> environmentSpecs = 
				((SystemTestConfiguration)TestContext
						.getTestMetadata()
						.getTestConfiguration())
				.getModInstance().getTargetEnvironmentSpecification();
		
		return environmentSpecs;
	}
	
	/**
	 * Returns the <tt>Map</tt> of module specifications for the configured module.
	 * @return <tt>Map</tt> of module specifications
	 */
	public final Map<String, String> getModSpecifications() {
		
		Map<String, String> modSpecs = 
				((SystemTestConfiguration)TestContext
						.getTestMetadata()
						.getTestConfiguration())
				.getModInstance().getModSpecification();
		
		return modSpecs;
	}
}