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
 * An instance of this class provides information about the report generation configuration as setup in 
 * the <tt>cognito-cfg.xml</tt>.
 * 
 * @author Aditya Karnad
 */
public final class ReportGenerationConfiguration {

	private String reportGenerationPath;

	/**
	 * Returns the path where the test reports are generated.
	 * @return test report generation path.
	 */
	public String getReportGenerationPath() {
		return reportGenerationPath;
	}
	
	/**
	 * Sets the path where the test reports are generated.
	 * @param reportGenerationPath represents the absolute test report generation path.
	 */
	public void setReportGenerationPath(String reportGenerationPath) {
		this.reportGenerationPath = reportGenerationPath;
	}
	
	@Override
	public String toString() {
		String configText = "\nREPORT-GEN CONFIG";
		configText = configText + "\n\tPATH:" + getReportGenerationPath();
		return configText;
	}
}