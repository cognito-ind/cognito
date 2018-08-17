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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * An instance of this class provides information about the application specific configuration.
 * 
 * @author Aditya Karnad
 */
public final class CognitoConfiguration implements Serializable {

	private static final long serialVersionUID = 863441141289854597L;

	private List<TestableMod> testableMods;
	private List <String> modNames = null;
	private List <String> environmentNames = null;
	private ReportGenerationConfiguration reportGenerationConfiguration;
	
	/**
	 * Returns a list of all testable mods defined in the <tt>cognito-cfg.xml</tt>.
	 * @return list of testable mods.
	 */
	public List<TestableMod> getTestableMods() {

		if (testableMods == null) {

			testableMods = new ArrayList<TestableMod>();
		}
		return testableMods;
	}
	
	
	/**
	 * Returns an object containing report generation configuration information.
	 * @return report generation configuration.
	 */
	public ReportGenerationConfiguration getReportGenerationConfiguration() {
		return reportGenerationConfiguration;
	}
	
	/**
	 * Sets the report generation configuration.
	 * @param reportGenerationConfiguration represents an object containing report generation configuration information.
	 * @see ReportGenerationConfiguration
	 */
	public void setReportGenerationConfiguration(ReportGenerationConfiguration reportGenerationConfiguration) {
		this.reportGenerationConfiguration = reportGenerationConfiguration;
	}
	
	/**
	 * Returns a list of all mod names found in the configuration.
	 * @return List of all mod names found in the configuration.
	 */
	public List<String> getAllModNames() {
		
		if (modNames != null) {
			
			return modNames;
		}
				
		modNames = new ArrayList<String>();

		for (TestableMod testableMod : getTestableMods()) {

			modNames.add(testableMod.getName());
		}

		return modNames;
	}
	
	/**
	 * Returns all the environment names from environments associates with the mod represented by <tt>modName</tt>.
	 * @param modName
	 * @return environment names from the mod <tt>modName</tt>
	 */
	public List<String> getAllEnvironmentNames(String modName) {
		
		if (environmentNames != null) {
			
			return environmentNames;
		}
		
		environmentNames = new ArrayList<String>();
		
		for (TestableMod testableMod : getTestableMods()) {
			
			if(testableMod.getName().equals(modName)) {

				for (Environment env : testableMod.getEnvironments()) {

					environmentNames.add(env.getName());
				}
			}
		}

		return environmentNames;
	}

	@Override
	public String toString() {

		String configText = "\n--------- Cognito Configuration ---------";

		for (TestableMod mod : getTestableMods()) {

			configText += mod;
		}
		
		configText = configText + getReportGenerationConfiguration();
		
		return configText;
	}
}