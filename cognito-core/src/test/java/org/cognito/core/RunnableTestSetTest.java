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
package org.cognito.core;

import static org.junit.Assert.assertEquals;

import org.cognito.config.beans.IntegrationTestConfiguration;
import org.cognito.config.beans.SystemTestConfiguration;
import org.cognito.config.exceptions.CognitoConfigurationException;
import org.cognito.core.RunnableTestSet;
import org.cognito.core.TestContext;
import org.cognito.core.exceptions.GenericCognitoRuntimeException;
import org.cognito.core.exceptions.TestRunInitializationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class RunnableTestSetTest {
	
	private RunnableTestSet currentTestSet;
	
	@Before
	public void resetTestEnvironment() throws CognitoConfigurationException {
		
		TestContext.resetTestConfiguration();
	}
	
	@Test (expected=GenericCognitoRuntimeException.class)
	public void testAnnotations_noAnnotationsApplied() throws ReflectiveOperationException {
		
		Class.forName("mockups.testruns.NoRequiredAnnotations").newInstance();
	}
	
	@Test (expected=GenericCognitoRuntimeException.class)
	public void testAnnotations_moreThanOneAnnotationsApplied() throws ReflectiveOperationException {
		
		Class.forName("mockups.testruns.MultipleConfigAnnotations").newInstance();
	}
	
	@Test
	public void testIntegrationRun_standardRun() throws ReflectiveOperationException, CognitoConfigurationException {
		
		currentTestSet = (RunnableTestSet) Class.forName("mockups.testruns.StandardWorkingIntegrationCase").newInstance();
		IntegrationTestConfiguration testConfig = (IntegrationTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		assertEquals(testConfig.getParticipantMods().size(), 2);
		assertEquals(testConfig.getParticipantMods().get(0).getModName(),"sampleWsMod");
		assertEquals(testConfig.getParticipantMods().get(1).getModName(),"sampleUiMod");
		assertEquals(testConfig.getParticipantMods().get(0).getBenchmarkEnvironmentSpecification(),null);
		assertEquals(testConfig.getParticipantMods().get(1).getBenchmarkEnvironmentSpecification(),null);
		assertEquals(testConfig.getParticipantMods().get(0).getTargetEnvironment(),"PROD");
		assertEquals(testConfig.getParticipantMods().get(1).getTargetEnvironment(),"AITE");
//		assertEquals(mockups.modintegrators.ModIntegratorMockup.class, testConfig.getModIntegrator());
	}
	
	@Test
	public void testSystemRun_standardRun() throws ReflectiveOperationException {
		
		currentTestSet = (RunnableTestSet) Class.forName("mockups.testruns.StandardWorkingSystemCase").newInstance();
		SystemTestConfiguration testConfig = (SystemTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		
	}

	@Test(expected=TestRunInitializationException.class)
	public void testSystemRun_InvalidVerifierUsed() throws ReflectiveOperationException {
		
		Class.forName("mockups.testruns.IncorrectlyAnnotatedSystemCase_InvalidCognitiveVerifier").newInstance();
	}

	
	@Test(expected=TestRunInitializationException.class)
	public void testSystemRun_MisspeltModName() throws ReflectiveOperationException {

		currentTestSet=(RunnableTestSet)Class.forName("mockups.testruns.IncorrectlyAnnotatedSystemCase_MisspeltModName").newInstance();
		SystemTestConfiguration testConfig = (SystemTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
	}

	@Test(expected=TestRunInitializationException.class)
	public void testSystemRun_InvalidTargetEnvironmnet() throws ReflectiveOperationException{

		currentTestSet=(RunnableTestSet)Class.forName("mockups.testruns.IncorrectlyAnnotatedSystemCase_InvalidTargetEnvironment").newInstance();
		SystemTestConfiguration testConfig = (SystemTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
	}

	@Test(expected=TestRunInitializationException.class)
	public void testIntegrationRun_MisspeltModName()throws ReflectiveOperationException{
		
		Class.forName("mockups.testruns.IncorrectlyAnnotatedIntegrationCase_MisspeltModName").newInstance();
	}
	
	@Test(expected=TestRunInitializationException.class)
	public void testIntegrationRun_MisspeltEnvironment()throws ReflectiveOperationException{
		
		Class.forName("mockups.testruns.IncorrectlyAnnotatedIntegrationCase_MisspeltEnvironment").newInstance();
	}
	
	@Test(expected=TestRunInitializationException.class)
	public void testRegressionRun_MisspeltModName() throws ReflectiveOperationException {

		Class.forName("mockups.testruns.IncorrectlyAnnotatedRegressionCase_MisspeltModName").newInstance();
	}
	
	@Test(expected=TestRunInitializationException.class)
	public void testRegressionRun_InvalidTargetEnvironment() throws ReflectiveOperationException {

		Class.forName("mockups.testruns.IncorrectlyAnnotatedRegressionCase_InvalidTargetEnvironment").newInstance();
	}
	
	@Test(expected=TestRunInitializationException.class)
	public void testRegressionRun_InvalidBenchmarkEnvironment() throws ReflectiveOperationException {

		Class.forName("mockups.testruns.IncorrectlyAnnotatedRegressionCase_InvalidBenchmarkEnvironment").newInstance();
	}
	
	/*
	 * FYI Make sure the "expected" exceptions are setup in cases for the negative test cases.
	 * If you miss adding this, the test will fail and cannot push code that fails test.
	 */
	@Test(expected=TestRunInitializationException.class)
	public void testRegressionRun_EqualTargetAndBenchmarkEnvironment() throws ReflectiveOperationException {

		Class.forName("mockups.testruns.IncorrectlyAnnotatedRegressionCase_EqualTargetAndBenchmarkEnvironment").newInstance();
	}
		
}