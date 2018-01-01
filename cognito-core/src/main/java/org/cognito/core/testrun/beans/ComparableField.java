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

import org.apache.log4j.Logger;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.exceptions.CognitoTestExecutionException;
import org.cognito.core.exceptions.ComparabilityException;
import org.cognito.core.exceptions.TestRunInitializationException;

/**
 * An object of this type is used to store a single output field of the output obtained from a test run.<br>
 * As the name suggests, this output field can be compared with other fields to analyze how much the value of a field deviates from another.
 * @author Aditya Karnad
 */
public class ComparableField {
	
	public ComparableField(String id, Object value) {
		
		this.id = id;
		this.value = value;
	}
	
	/**
	 * This is a field identifier and should be unique for each field in the output.
	 */
	protected String id;
	/**
	 * This holds the value of the field and can be of one of the following types:
	 * <ul>
	 * <li>Boolean</li><li>String</li><li>Numerical (Byte / Short / Integer / Float / Double)</li>
	 * </ul>
	 */
	protected Object value;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	static {
		logger = Logger.getLogger(ComparableField.class);
	}
	private static Logger logger;
	
	
	/**
	 * Gets the deviation <b>percentage</b> of the value of the current field from the value of the 
	 * field passed as argument.
	 * 
	 * <b>Example:</b><br>
	 * If the current field value is 5000000 and the field passed as argument has the value 5000025<br>
	 * Deviation % = ((5000000 / 5000025)-1) * 100 = <b>-0.0005</b><br>
	 * <i>i.e. The current field value is 0.0005% less than the field it's being compared to.</i>
	 * <br>
	 * <br><b>Note:</b> The deviation calculation only works for Strings, Numerical values and Booleans. No other types are supported.
	 * 
	 * @param field represents another field that the current field is being compared with.
	 * @return Deviation of the value of the current field from the value of the 
	 * field passed as argument.
	 */
	public double getDeviationFrom(ComparableField field) {
		
		logger.info("Calculating deviation between the comparable fields.");
		
		double deviationInPercentage = 0.0;

		Object thisField = this.getValue();
		Object comparableField = field.getValue();
		double stringComparisonPoint;
		
		char thisFieldArray[] ;
		char comparableFieldArray[] ;

		if(thisField instanceof String) {
			
			if(comparableField instanceof String){
				
				int length = 0;
				double diff = 0;
				double remainingStringLength = 0;
				
				thisFieldArray = ((String) thisField).toCharArray();
				
				comparableFieldArray = ((String) comparableField).toCharArray();

				if(thisFieldArray.length < comparableFieldArray.length){
					
					length = thisFieldArray.length;
					remainingStringLength = comparableFieldArray.length-thisFieldArray.length;
				}
				else {
					
					length = comparableFieldArray.length;
					remainingStringLength = thisFieldArray.length-comparableFieldArray.length;
				}
				
				for(int i=0; i<length; i++){
					if(thisFieldArray[i]!=comparableFieldArray[i]){
						
						++diff;
					}
				}
				
				diff += remainingStringLength;
				
				deviationInPercentage = (diff/(length+remainingStringLength))*100;
				
				deviationInPercentage = Math.round(deviationInPercentage*10000.0)/10000.0;
				
				logger.info("Calculating deviation between the comparable Strings... Complete");
				
				return deviationInPercentage;
			}
			else{

				throw new CognitoTestExecutionException("IncompatibleFields", "\""+comparableField + "\" is not a String value...");
			}
		}

		else if(thisField instanceof Integer || thisField instanceof Double || thisField instanceof Byte 
				|| thisField instanceof Short || thisField instanceof Long){

			if(comparableField instanceof Integer || comparableField instanceof Double || comparableField instanceof Byte 
					|| comparableField instanceof Short || comparableField instanceof Long){

				thisField = Double.parseDouble(thisField.toString());
				comparableField = Double.parseDouble(comparableField.toString());

				deviationInPercentage = (((Double) thisField/(Double) comparableField)-1) * 100;

				deviationInPercentage = Math.round(deviationInPercentage*10000.0)/10000.0;

				logger.info("Calculating deviation between the comparable fields... Complete");

				return deviationInPercentage;
			}
			else{
				throw new ComparabilityException("IncompatibleFields", "\""+comparableField + "\" cannot be converted to double...");
			}

		}

		else if(thisField instanceof Boolean){
			
			if(comparableField instanceof Boolean){

				if(thisField.equals(comparableField)){

					deviationInPercentage = 0;
				}
				else {

					deviationInPercentage = 100.00;
				}
			}
			
			else{

				throw new CognitoTestExecutionException("IncompatibleFields", "\""+comparableField + "\" is not of boolean type...");
			}

			logger.info("Calculating deviation between the comparable fields... Complete");
			return deviationInPercentage;
		}
		
		else{
			
			throw new ComparabilityException("UncomparableFields", "\""+thisField + "\" and " + "\""+comparableField + "\" cannot be compared.");
		}
	}
}