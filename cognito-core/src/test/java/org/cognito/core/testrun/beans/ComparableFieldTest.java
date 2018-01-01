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

import static org.junit.Assert.*;

import org.cognito.core.exceptions.CognitoTestExecutionException;
import org.cognito.core.exceptions.ComparabilityException;
import org.cognito.core.exceptions.TestRunInitializationException;
import org.cognito.core.testrun.beans.ComparableField;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ComparableFieldTest {	
	
	private ComparableField comparableField = new ComparableField("testField", 5000000.00);
	
	@Before
	public void initialize(){
		
		comparableField.setValue(5000000.00);
	}
	
	@Test
	public void testGetDeviationFrom_NegativeDeviation(){
		ComparableField nwField = new ComparableField("test",5000025.0);
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals(-0.0005, response, 0.0);
	}
	
	@Test
	public void testGetDeviationFrom_PositiveDeviation(){
		
		ComparableField nwField = new ComparableField("test",5000000.0);
		comparableField.setValue(5000025.0);
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals(0.0005, response, 0.0);
	}

	@Test
	public void testGetDeviationFrom_ZeroDeviation(){
		
		ComparableField nwField = new ComparableField("test",5000000.0);
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals(0.00, response, 0.0);

	}
	
	@Test
	public void testGetDeviationFrom_ZeroDeviationWithItsOwnReference(){
		
		double response = comparableField.getDeviationFrom(comparableField);
		assertEquals(0.00, response, 0.0);
	}
	
	@Test
	public void testGetDeviationFrom_StringValuesPassed(){
		
		ComparableField nwField = new ComparableField("testString","abc.aite.com");
		comparableField.setValue("abc.site.com");
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals(8.3333, response, 0.0);
	}
	
	@Test
	public void testGetDeviationFrom_BooleanValuesPassed(){
		
		ComparableField nwField = new ComparableField("testBool",true);
		comparableField.setValue(true);
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals(0.00, response, 0.0);
	}
	
	
	@Test(expected=ComparabilityException.class)
	public void testGetDeviationFrom_NonComparableObjectsPassed(){
		
		ComparableField nwField = new ComparableField("testInvalidObj",comparableField);
		comparableField.setValue(nwField);
		double response = comparableField.getDeviationFrom(nwField);
	}
	
	@Test (expected=ComparabilityException.class)
	public void testGetDeviationFrom_DifferentTypesOfParametersPassed() {
		
		ComparableField nwField = new ComparableField("testLetter","A");
		double response = comparableField.getDeviationFrom(nwField);
	}
	
	@Test 
	public void testGetDeviationFrom_ByteAndDoublePassed() {
		
		Byte a = 5;
		Double b = 5.00;
		ComparableField nwField = new ComparableField("testNum",a);
		comparableField.setValue(b);
		double response = comparableField.getDeviationFrom(nwField);
		assertEquals("The given values are not equal : ", 0.0, response, 0.0);
	}
}