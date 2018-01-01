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

public final class IntegrationTestConfiguration extends TestConfiguration {

	private List<ModInstance> participantMods;
	private String expectedOutputSource;

	public List<ModInstance> getParticipantMods() {
		
		if(participantMods == null) {
			participantMods = new ArrayList<ModInstance>();
		}
		return participantMods;
	}

	public void setParticipantMods(List<ModInstance> participantMods) {
		this.participantMods = participantMods;
	}
	
	public String getExpectedOutputSource() {
		return expectedOutputSource;
	}

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