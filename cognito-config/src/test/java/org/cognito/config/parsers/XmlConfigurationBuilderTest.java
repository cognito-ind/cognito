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
package org.cognito.config.parsers;

import static org.junit.Assert.*;

import org.cognito.config.beans.CognitoConfiguration;
import org.cognito.config.exceptions.CognitoConfigurationException;
import org.cognito.config.parsers.XmlConfigurationBuilder;
import org.junit.Before;
import org.junit.Test;

public class XmlConfigurationBuilderTest {

	@Before
	public void setUp() throws Exception {
		
		
	}

	@Test
	public void testBuildConfiguration_01_baseEpcConfig() {
		
		try {
			
			String desiredStr="\n--------- Cognito Configuration ---------\nMOD:EpcEngine(web service)\n\tENV:DEV\n\t\tENV-SPEC:url=blah-dev.com/service/doSomething?wsdl\n\t\tENV-SPEC:dbUrl=dbUrlHere\n\tENV:TEST\n\t\tENV-SPEC:specX=specDetail\n\t\tENV-SPEC:url=blah-test.com/service/doSomething?wsdl\n\tMOD-SPEC:acceptableDeviation=100\n\tMOD-SPEC:someOtherUserDefinedOption=Option XYZ\nMOD:EpcUi(user interface)\n\tENV:AITE\n\t\tENV-SPEC:url=blah-dev.com/showui\n\tENV:SITE\n\t\tENV-SPEC:url=blah-test.com/showui\n\tMOD-SPEC:acceptableDeviation=100\n\tMOD-SPEC:someOtherUserDefinedOption=Option XYZ\nREPORT-GEN CONFIG\n\tPATH:C:/cognito-reports";
			
			CognitoConfiguration config = 
					new XmlConfigurationBuilder("test-config_01.xml")
					.buildConfiguration();
			
			assertTrue(config.toString().equals(desiredStr));
			assertEquals(config.getTestableMods().size(), 2);
		}
		catch (CognitoConfigurationException exception) {
			
			fail("Exception encountered: " + exception.getClass() + ": "+exception.getMessage());
		}
	}
}