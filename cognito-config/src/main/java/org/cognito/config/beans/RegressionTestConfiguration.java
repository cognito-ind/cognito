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
 * An instance of this class provides information about the Regression test configuration as setup in 
 * the <tt>cognito-cfg.xml</tt>.
 * 
 * @author Aditya Karnad
 */
public final class RegressionTestConfiguration extends SystemTestConfiguration {

	/**
	 * Environment on which the test is to be run on after it runs on the <tt>targetEnvironment</tt>. The second run 
	 * on the <tt>benchmarkEnvironment</tt> would be compared to the first run and the <i>delta</i> would be reported by the framework.
	 * The benchmark environment must also feature in the <tt>cognito-cfg.xml</tt>.
	 */
	private String benchmarkEnvironment;

	/**
	 * Returns the benchmark environment.
	 * @return Benchmark Environment.
	 */
	public String getBenchmarkEnvironment() {
		
		return benchmarkEnvironment;
	}
	
	/**
	 * Sets the benchmark environment.
	 * @param benchmarkEnvironment Benchmark Environment (should be defined in the <tt>cognito-cfg.xml</tt>).
	 */
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