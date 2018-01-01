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

public final class RegressionTestConfiguration extends SystemTestConfiguration {

	private String benchmarkEnvironment;

	public String getBenchmarkEnvironment() {
		return benchmarkEnvironment;
	}

	public void setBenchmarkEnvironment(String benchmarkEnvironment) {
		this.benchmarkEnvironment = benchmarkEnvironment;
	}
	
	@Override
	public String toString() {
		
		String configText = "\nREGR-TEST CONFIG";
		configText = configText + "\n\tMOD:" + getModName();
		configText = configText + "\n\tTARGET-ENV:" + getTargetEnvironment();
		configText = configText + "\n\tBENCHMARK-ENV:" + getBenchmarkEnvironment();
		return configText;
	}
	
	@Override
	public String getEnvironmentsDetail() {
		
		return super.getEnvironmentsDetail() + "(" + benchmarkEnvironment + ")";
	}
}