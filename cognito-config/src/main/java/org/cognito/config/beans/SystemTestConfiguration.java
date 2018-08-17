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
package org.cognito.config.beans;


/**
 * An instance of this class provides information about the System test configuration as setup in 
 * the <tt>cognito-cfg.xml</tt>.
 * 
 * @author Aditya Karnad
 */
public class SystemTestConfiguration extends TestConfiguration {

	/**
	 * Module name as provided in <tt>cognito-cfg.xml</tt>.
	 */
	private String modName;
	/**
	 * Environment on which the test is to be run. This environment must feature in the <tt>cognito-cfg.xml</tt>.
	 */
	private String targetEnvironment;
	/**
	 *  The mod instance ({@link ModInstance}) participating in the test.
	 */
	private ModInstance modInstance;
	/**
	 * Expected output source file path.
	 */
	private String expectedOutputSource;

	/**
	 * Returns the module name as provided in <tt>cognito-cfg.xml</tt>.
	 * @return Module name
	 */
	public String getModName() {
		return modName;
	}
	
	/**
	 * Sets the module name.
	 * @param modName Module name.
	 */
	public void setModName(String modName) {
		this.modName = modName;
	}

	/**
	 * Returns the target environment for the test.
	 * @return Target environment.
	 */
	public String getTargetEnvironment() {
		return targetEnvironment;
	}
	
	/**
	 * Sets the target environment for the test.
	 * @param targetEnvironment Target environment.
	 */
	public void setTargetEnvironment(String targetEnvironment) {
		this.targetEnvironment = targetEnvironment;
	}
	
	/**
	 * Returns the mod instance on which the test is to be run.
	 * @return Mod Instance.
	 */
	public ModInstance getModInstance() {
		return modInstance;
	}

	/**
	 * Sets the mod instance on which the test is to be run.
	 * @param modInstance Mod Instance.
	 */
	public void setModInstance(ModInstance modInstance) {
		this.modInstance = modInstance;
	}

	/**
	 * Returns the file and path from which the expected output should be read during test run.
	 * @return Expected output source file path.
	 */
	public String getExpectedOutputSource() {
		return expectedOutputSource;
	}
	
	/**
	 * Sets the file and path from which the expected output should be read during test run.
	 * @param expectedOutputSource Expected output source file path.
	 */
	public void setExpectedOutputSource(String expectedOutputSource) {
		
		this.expectedOutputSource = expectedOutputSource;
	}

	@Override
	public String toString() {
		
		String configText = "\nSYS-TEST CONFIG";
		configText = configText + "\n\tMOD:" + getModName() + "<"+ getTargetEnvironment()+">";
		return configText;
	}

	@Override
	public String getEnvironmentsDetail() {
		
		return targetEnvironment;
	}
}