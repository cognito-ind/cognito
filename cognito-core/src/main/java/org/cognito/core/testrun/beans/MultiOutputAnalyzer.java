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

import java.util.ArrayList;
import java.util.List;

import org.cognito.config.util.TextManipulationUtilities;

/**
 * An object of this type is used to analyze deviations in outputs drawn from varying test runs.<br>
 * The test runs maybe from different module instances, <i>i.e. the same type of module run on different environments</i> 
 * or may also be derived from different modules with similar functionalities.<br>
 * The deviations will be calculated based on the field identifiers (i.e. {@link ComparableField#id}) from each of the outputs.
 * 
 * @author Aditya Karnad
 */
public class MultiOutputAnalyzer {
	
	
	private List<FieldDeviation> fieldDeviations;
	private double maximumDeviation;
	
	/**
	 * Constructs a {@link MultiOutputAnalyzer} instance from the analysis derived from the <tt>target</tt> 
	 * and <tt>benchmark</tt> {@link TestRunOutput} instances passed as arguments.
	 * 
	 * @param target represents the target {@link TestRunOutput} instance which 
	 * is to be compared with a benchmark instance.
	 * @param benchmark represents the benchmark {@link TestRunOutput} instance against which
	 * the target instance is compared.
	 */
	public MultiOutputAnalyzer(TestRunOutput target, TestRunOutput benchmark) {
		
		maximumDeviation = 0;
		fieldDeviations = new ArrayList<FieldDeviation>();
		
		for (ComparableField benchmarkOutputField : benchmark.outputFields) {
			
			FieldDeviation fieldDeviation = new FieldDeviation();
			
			fieldDeviation.setId(benchmarkOutputField.id);
			fieldDeviation.setBenchmarkValue(benchmarkOutputField.value);
				
			for (ComparableField targetOutputField : target.outputFields) {
				
				if (
						TextManipulationUtilities
						.isAcceptableEquivalent(
								targetOutputField.id, 
								benchmarkOutputField.id)
						) {
					
					fieldDeviation.setTargetValue(targetOutputField.value);
					
					//finding deviation
					fieldDeviation.setDeviation(
							benchmarkOutputField.getDeviationFrom(targetOutputField));
					
					if (fieldDeviation.getDeviation() > maximumDeviation) {
						
						maximumDeviation = fieldDeviation.getDeviation();
					}
				}
			}
			
			if (fieldDeviation.getTargetValue() == null) {
				
				fieldDeviation.setTargetValue("N/A");
				fieldDeviation.setDeviation(-999.999);
			}
			
			fieldDeviations.add(fieldDeviation);
			
		}
	}
	
	
//	public List<FieldDeviation> getFieldDeviations() {
//		return fieldDeviations;
//	}
//	
//	
//	public void setFieldDeviations(List<FieldDeviation> fieldDeviations) {
//		this.fieldDeviations = fieldDeviations;
//	}
	
	/**
	 * Returns the maximum deviation found between the target and benchmark fields read from all the 
	 * {@link TestRunOutput} instances used to derive the analysis.
	 * 
	 * @return Maximum deviation found between target and benchmark fields.
	 */
	public double getMaximumDeviation() {
		return maximumDeviation;
	}
	
	@Override
	public String toString() {
		
		StringBuffer outputAnalysis = new StringBuffer(
				System.lineSeparator() 
				+ "********* OUTPUT ANALYSIS *********" 
				+ System.lineSeparator());
		
		outputAnalysis.append("Field\tBenchmark\tTarget\tDeviation"+System.lineSeparator());
		for (FieldDeviation deviation : fieldDeviations) {
			
			outputAnalysis.append(deviation);
		}
		
		return outputAnalysis.toString();
	}
}