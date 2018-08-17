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

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class provides information about the Integration test configuration as setup in 
 * the <tt>cognito-cfg.xml</tt>.
 * 
 * @author Aditya Karnad
 */
public final class IntegrationTestConfiguration extends TestConfiguration {

	/**
	 * List of module instances ({@link ModInstance}) which participate in the Integration test.
	 */
	private List<ModInstance> participantMods;
	
	/**
	 * Represents the file from which the expected output should be read during test run.
	 */
	private String expectedOutputSource;

	/**
	 * Returns {@link List} of {@link ModInstance}s which participate in the test.
	 * @return participant modules.
	 */
	public List<ModInstance> getParticipantMods() {
		
		if(participantMods == null) {
			participantMods = new ArrayList<ModInstance>();
		}
		return participantMods;
	}

	/**
	 * Sets the participant modules which would participate in the test.
	 * @param participantMods
	 */
	public void setParticipantMods(List<ModInstance> participantMods) {
		this.participantMods = participantMods;
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
	 * @param expectedOutputSource String file path of output source
	 */
	public void setExpectedOutputSource(String expectedOutputSource) {
		this.expectedOutputSource = expectedOutputSource;
	}
	
	@Override
	public String toString() {
		
		String configText = "\nINTGR-TEST CONFIG";
		configText = configText + "\n\tPARTICIPANT MODS:";
		for (ModInstance modInstance : getParticipantMods()) {
			
			configText += modInstance;
		}
		return configText;
	}
	
	@Override
	public String getEnvironmentsDetail() {
		
		String environments = "";
		for (ModInstance instance : participantMods) {
			
			environments.concat(instance.getTargetEnvironment() + " / ");
		}
		return environments;
	}

	
}