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

public class SystemTestConfiguration extends TestConfiguration {

	private String modName;
	private String targetEnvironment;
	private ModInstance modInstance;
	private String expectedOutputSource;

	public String getModName() {
		return modName;
	}
	
	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getTargetEnvironment() {
		return targetEnvironment;
	}

	public void setTargetEnvironment(String targetEnvironment) {
		this.targetEnvironment = targetEnvironment;
	}
	
	
	public ModInstance getModInstance() {
		return modInstance;
	}

	public void setModInstance(ModInstance modInstance) {
		this.modInstance = modInstance;
	}

	public String getExpectedOutputSource() {
		return expectedOutputSource;
	}

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