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
package org.cognito.core.testrun.beans;

/**
 * An instance of this class stores the deviation between two {@link ComparableField} instances.
 * 
 * @author Aditya Karnad
 */
public class FieldDeviation {
	
	private String id;
	private Object targetValue;
	private Object benchmarkValue;
	private double deviation;
	
	/**
	 * Returns the {@link ComparableField} instance's identifier.
	 * @return {@link ComparableField} instance's identifier
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the {@link ComparableField} instance's identifier.
	 * @param id represents the {@link ComparableField} instance's identifier.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Returns the value of the target {@link ComparableField} instance.
	 * @return Value of the target {@link ComparableField} instance
	 */
	public Object getTargetValue() {
		return targetValue;
	}
	
	/**
	 * Sets the value of the target {@link ComparableField} instance.
	 * @param targetValue represents the value of the target {@link ComparableField} instance
	 */
	public void setTargetValue(Object targetValue) {
		this.targetValue = targetValue;
	}
	
	/**
	 * Returns the value of the benchmark {@link ComparableField} instance.
	 * @return Value of the benchmark {@link ComparableField} instance
	 */
	public Object getBenchmarkValue() {
		return benchmarkValue;
	}
	
	/**
	 * Sets the value of the benchmark {@link ComparableField} instance.
	 * @param benchmarkValue represents the value of the benchmark {@link ComparableField} instance.
	 */
	public void setBenchmarkValue(Object benchmarkValue) {
		this.benchmarkValue = benchmarkValue;
	}
	
	/**
	 * Returns the deviation between the target and benchmark {@link ComparableField} instances.
	 * @return Deviation between the target and benchmark {@link ComparableField} instances.
	 */
	public double getDeviation() {
		return deviation;
	}
	
	/**
	 * Sets the deviation between the target and benchmark {@link ComparableField} instances.
	 * @param deviation represents the deviation between the target and benchmark {@link ComparableField} instances.
	 */
	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}
	
	@Override
	public String toString() {
		
		return getId()+"\t"+getBenchmarkValue()+"\t"+getTargetValue()+"\t"+getDeviation() + System.lineSeparator();
	}
}