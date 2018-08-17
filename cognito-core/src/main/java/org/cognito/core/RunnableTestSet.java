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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.cognito.config.annotations.IntegrationTest;
import org.cognito.config.annotations.RegressionTest;
import org.cognito.config.annotations.SystemTest;
import org.cognito.config.beans.CognitoConfiguration;
import org.cognito.config.beans.CognitoImplementable;
import org.cognito.config.beans.IntegrationTestConfiguration;
import org.cognito.config.beans.ModInstance;
import org.cognito.config.beans.RegressionTestConfiguration;
import org.cognito.config.beans.SystemTestConfiguration;
import org.cognito.config.exceptions.CognitoConfigurationException;
import org.cognito.config.exceptions.InvalidConfigurationException;
import org.cognito.config.util.TextManipulationUtilities;
import org.cognito.core.annotations.Verification;
import org.cognito.core.exceptions.CognitoTestExecutionException;
import org.cognito.core.exceptions.TestRunInitializationException;
import org.cognito.core.testrun.beans.ExpectedOutput;
import org.cognito.core.testrun.beans.TestRecord;
import org.cognito.core.testrun.beans.TestRunOutput;
import org.cognito.core.testrun.implementables.CognitiveVerifier;
import org.cognito.core.testrun.implementables.ModRunner;
import org.cognito.core.testrun.implementables.RegressionTestRunner;
import org.cognito.core.testrun.implementables.SystemTestRunner;
import org.cognito.core.testrun.implementables.TestIntegrator;
import org.cognito.core.testrun.implementables.TestRunner;
import org.cognito.core.util.recorders.GenericRecorder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;


/**
 * This class should be extended to form all test-sets supported by Cognito.
 * 
 * @author Aditya Karnad
 * @see SystemTest
 * @see IntegrationTest
 * @see RegressionTest
 */

public abstract class RunnableTestSet {
	
	static {
		logger = Logger.getLogger(RunnableTestSet.class);
	}
	private static Logger logger;
	
	/**
	 * Indicates the type of test to be a System Test.
	 */
	public static final byte TYPE_SystemTest = 1;
	/**
	 * Indicates the type of test to be a Regression Test.
	 */
	public static final byte TYPE_RegressionTest = 2;
	/**
	 * Indicates the type of test to be a Integration Test.
	 */
	public static final byte TYPE_IntegrationTest = 3;
	
	private static final byte RECORD_AS_TestLog = 1;
	private static final byte RECORD_AS_CognitiveVerificationLog = 2;
	
	
	
	private CognitoConfiguration configuration;
	
	protected TestRecord record;
	
	protected TestRunner testRunner;
	
	private boolean cognitiveVerifierDefined;
	
	/**
	 * Reference to the current test
	 */
	@Rule
	public TestName testName = new TestName();
	
	
	/**
	 * Triggers the finalization of test run which entails:
	 * <ul>
	 * <li>recording of test log to a file and</li>
	 * <li>recording details of cognitive failures if any were encountered during test run.</li>
	 * </ul>
	 * @throws CognitoConfigurationException 
	 */
	@AfterClass
	public static final void finalizeTest() throws CognitoConfigurationException {
		
		logger.debug("Recording test log.");
		
		System.out.println(TestContext.getTestMetadata().getTestLog());
		
		//Recording the test log
		record(TestContext.getTestMetadata().getTestLog().toString(), RECORD_AS_TestLog);
		logger.info("Recording test log... Complete.");
		
		
		if (TestContext.getTestMetadata().getCognitiveVerificationFailureLog() != null) {
			
			logger.debug("Recording cognitive verification failure log.");
			System.out.println(TestContext.getTestMetadata().getCognitiveVerificationFailureLog());
			//TODO Remove this sysout after test
			
			//Recording the Cognitive Verification failure log
			record(
					TestContext.getTestMetadata().getCognitiveVerificationFailureLog().toString(),
					RECORD_AS_CognitiveVerificationLog
			);
			
			logger.info("Recording cognitive verification failure log... Complete.");
		}
		
		logger.debug("Resetting test log...");
		TestContext.getTestMetadata().setTestLog(null);
		TestContext.getTestMetadata().setCognitiveVerificationFailureLog(null);
		
	}
	
	/**
	 * Gets the timestamp in YYYYMMDD_HHMM format.
	 * @return formatted timestamp.
	 */
	private static String getFormattedTimestamp() {
		
		Calendar calendar = Calendar.getInstance();
		
		String date = "";
		String time = "";
		String timeStamp = "";
		
		date += calendar.get(Calendar.YEAR);
		
		if(calendar.get(Calendar.MONTH) + 1 < 10){
			
			date += "0"+(calendar.get(Calendar.MONTH) + 1);
		}
		else{
			
			date += (calendar.get(Calendar.MONTH) + 1);
		}

		if(calendar.get(Calendar.DATE) < 10){
			
			date += "0"+(calendar.get(Calendar.DATE));
		}
		else{
			
			date += (calendar.get(Calendar.DATE));
		}
		
		if(calendar.get(Calendar.HOUR_OF_DAY) < 10){
			
			time += "0"+(calendar.get(Calendar.HOUR_OF_DAY));
		}
		else{
			
			time += (calendar.get(Calendar.HOUR_OF_DAY));
		}
		
		if(calendar.get(Calendar.MINUTE) < 10){
			
			time += "0"+(calendar.get(Calendar.MINUTE));
		}
		else{
			
			time += (calendar.get(Calendar.MINUTE));
		}
		
		timeStamp = date + "_" + time;
		
		return timeStamp; //"YYYYMMDD_HHMM format"
	}
	/**
	 * Records the text contained in <tt>content</tt> on a text file.
	 * @param content
	 * @throws CognitoConfigurationException 
	 */
	private static void record(String content, byte recordAs) throws CognitoConfigurationException {
		
		String path = TestContext.getConfiguration().getReportGenerationConfiguration().getReportGenerationPath();
		String filename = 
				TextManipulationUtilities.deriveTestTypeText(TestContext.getTestMetadata().getType());
		
		if (recordAs == RECORD_AS_TestLog) {
			
			filename = filename.concat("_TestLog_");
		}
		else if (recordAs == RECORD_AS_CognitiveVerificationLog) {
			
			filename = filename.concat("_CognitiveVerificationFailureLog_");
		}
		else {
			filename = filename.concat("_Misc_");
		}
		
		filename = filename.concat(getFormattedTimestamp()+".TXT");
		
		try {
			
			GenericRecorder.record(path+"/"+filename, content);
		}
		catch (IOException fileWritingException) {
			
			throw new CognitoTestExecutionException("RecordingTestLogFailed", fileWritingException.getMessage());
		}
	}
	
	
	{
		try {
			initialize();
		}
		catch (CognitoConfigurationException e) {
			
			throw new TestRunInitializationException("UnknownRuntimeException", e.getMessage());
		}
	}
	

	
	
	
	private void deriveTestMetadata() {
		
		logger.debug("Deriving test metadata.");
		TestContext.getTestMetadata().setTestSet(this.getClass().toString().substring(6));
		TestContext.getTestMetadata().setScenario(testName.getMethodName());
		logger.info("Deriving test metadata... Complete.");
	}
	
	
	private String deriveTestType(byte type) {
		
		if (type == RunnableTestSet.TYPE_IntegrationTest) {
			
			return "Integration Test";
		}
		else if (type == RunnableTestSet.TYPE_RegressionTest) {
			
			return "Regression Test";
		}
		else if (type == RunnableTestSet.TYPE_SystemTest) {
			
			return "System Test";
		}
		else {
			return "<< UNKNOWN TEST TYPE >>";
		}
	}

	private void initialize() throws CognitoConfigurationException {
		
		configuration = TestContext.getConfiguration();
		
		record = new TestRecord();
		
		if (TestContext.getTestMetadata().getTestLog() == null) {
			
			TestContext.getTestMetadata().setTestLog(new StringBuffer());
		}
		
		readTestConfigurationAnnotations();
	}
	

	private void readIntegrationTestConfiguration() throws CognitoConfigurationException {
		
		logger.debug("Reading Integration Test Configuration.");
		IntegrationTestConfiguration testConfig = 
				(IntegrationTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		IntegrationTest integrationTestMetadata = this.getClass().getAnnotation(IntegrationTest.class);
		
		//Validate and Read participant mods
		readParticipantMods(integrationTestMetadata.participantMods());
		
		logger.debug("Reading Integration Test -> Expected output source...");
		testConfig.setExpectedOutputSource(integrationTestMetadata.expectedOutputSource());
		
		
		logger.debug("Reading Integration Test -> Instantiating Test Integrator : "
				+ integrationTestMetadata.testIntegrator());
		
		try {
			
			testRunner = (TestRunner) integrationTestMetadata.testIntegrator().newInstance();
		}
		catch (ReflectiveOperationException exception) {
			
			throw new InvalidConfigurationException(
					"OperatorInstanceCreationFailure", exception.getClass() + ": " + exception.getMessage());
		}
		
		logger.info("Reading Integration Test Configuration...Complete");
	}
	
	private void readParticipantMods(String[] participantMods) throws InvalidConfigurationException {
		
		logger.debug("Reading Integration Test Participant Mods.");
		logger.debug("Reading Integration Test -> participant mods...");
		
		IntegrationTestConfiguration testConfig = 
				(IntegrationTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		
		for (String modInstance : participantMods) {
			
			String [] fragments = modInstance.split(":");
			validateModInstance(fragments);
			testConfig.getParticipantMods().add(
					new ModInstance(fragments[0].trim(), fragments[1].trim(), null, configuration));
		}
		logger.info("Reading Integration Test Participant Mods...Complete");
	}
	

	private void readRegressionTestConfiguration() throws InvalidConfigurationException {
		
		logger.debug("Reading Regression Test Configuration.");
		RegressionTest regressionTestMetadata = this.getClass().getAnnotation(RegressionTest.class);
		
		logger.debug("Reading Regression Test -> Test Configuration...");
		RegressionTestConfiguration testConfig = 
				(RegressionTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		
		validateRegressionTestConfiguration(
				regressionTestMetadata.modName(),
				regressionTestMetadata.benchMarkEnvironment(),
				regressionTestMetadata.targetEnvironment(),
				regressionTestMetadata.moduleRunner());
		
		logger.debug("Reading Regression Test -> Mod Name...");
		testConfig.setModName(regressionTestMetadata.modName());
		logger.debug("Reading Regression Test -> Benchmark Environment...");
		testConfig.setBenchmarkEnvironment(regressionTestMetadata.benchMarkEnvironment());
		logger.debug("Reading Regression Test -> Target Environment...");
		testConfig.setTargetEnvironment(regressionTestMetadata.targetEnvironment());
		logger.debug("Reading Regression Test -> Mod Instance...");
		testConfig.setModInstance(new ModInstance(
				regressionTestMetadata.modName(), 
				regressionTestMetadata.targetEnvironment(), 
				regressionTestMetadata.benchMarkEnvironment(), 
				configuration));
		
		logger.debug("Reading System Test -> Instantiating Mod Operator : " + regressionTestMetadata.moduleRunner());
		
		try {
			
			testRunner = (RegressionTestRunner) regressionTestMetadata.moduleRunner().newInstance();
		}
		catch (ReflectiveOperationException exception) {
			
			throw new InvalidConfigurationException(
					"OperatorInstanceCreationFailure", 
					exception.getClass() + ": " + exception.getMessage());
		}
		
		logger.info("Reading Regression Test Configuration...Complete");
		
	}
	
	private void readSystemTestConfiguration() throws InvalidConfigurationException {
		
		logger.debug("Reading System Test Configuration for "+this.getClass()+".");
		SystemTest systemTestMetadata = this.getClass().getAnnotation(SystemTest.class);
		
		if (systemTestMetadata.cognitiveVerifier() != CognitoImplementable.class) {
			
			cognitiveVerifierDefined = true;
		}
		
		SystemTestConfiguration testConfig = (SystemTestConfiguration)TestContext.getTestMetadata().getTestConfiguration();
		
		validateSystemTestConfiguration(
				systemTestMetadata.modName(),
				systemTestMetadata.targetEnvironment(),
				systemTestMetadata.cognitiveVerifier(),
				systemTestMetadata.moduleRunner(),
				systemTestMetadata.expectedOutputSource());
		
		logger.debug("Reading System Test -> Mod Name...");
		testConfig.setModName(systemTestMetadata.modName());
		
		logger.debug("Reading System Test -> Target Environment...");
		testConfig.setTargetEnvironment(systemTestMetadata.targetEnvironment());
		
		logger.debug("Reading System Test -> Expected output source...");
		testConfig.setExpectedOutputSource(systemTestMetadata.expectedOutputSource());
		
		logger.debug("Reading System Test -> Mod Instance...");
		testConfig.setModInstance(new ModInstance(
				systemTestMetadata.modName(), 
				systemTestMetadata.targetEnvironment(), 
				null, 
				configuration));
		
		logger.debug("Reading System Test -> Instantiating Mod Operator : " + systemTestMetadata.moduleRunner());
		
		try {
			
			testRunner = (SystemTestRunner) systemTestMetadata.moduleRunner().newInstance();
		}
		catch (ReflectiveOperationException exception) {
			
			throw new InvalidConfigurationException("OperatorInstanceCreationFailure", exception.getClass() + ": " + exception.getMessage());
		}
		
		logger.info("Reading System Test Configuration...Complete");
	}
	
	private void readTestConfigurationAnnotations() throws CognitoConfigurationException {
		
		logger.debug("Reading Test Configuration Annotations.");
		/*
		 * Check if one of the following annotations is used on the class:
		 * 		- SystemTest
		 * 		- IntegrationTest
		 *  	- RegressionTest
		 */
		
		byte testConfigAnnotationsUsed = 0;
		for (Annotation annotation : Arrays.asList(this.getClass().getAnnotations())) {
			
			if (annotation instanceof SystemTest) {
				
				TestContext.getTestMetadata().setType(TYPE_SystemTest);
				testConfigAnnotationsUsed ++;
			}
			else if (annotation instanceof RegressionTest) {
				
				TestContext.getTestMetadata().setType(TYPE_RegressionTest);
				testConfigAnnotationsUsed ++;
			}
			else if (annotation instanceof IntegrationTest) {
				
				TestContext.getTestMetadata().setType(TYPE_IntegrationTest);
				testConfigAnnotationsUsed ++;
			}
		}
		
		if (testConfigAnnotationsUsed != 1) {
			
			throw new InvalidConfigurationException("InvalidAnnotationUsage", Byte.toString(testConfigAnnotationsUsed));
		}
		else {
			
			if (TestContext.getTestMetadata().getType() == TYPE_SystemTest) {
				
				readSystemTestConfiguration();
			}
			else if (TestContext.getTestMetadata().getType() == TYPE_RegressionTest) {
				
				readRegressionTestConfiguration();
			}
			else if (TestContext.getTestMetadata().getType() == TYPE_IntegrationTest) {
				
				readIntegrationTestConfiguration();
			}
		}
		logger.info("Reading Test Configuration Annotations... Complete");
	}
	
	private void runAndRecordIntegrationTest() {
		
		IntegrationTestConfiguration config = (IntegrationTestConfiguration) TestContext.getTestMetadata().getTestConfiguration();
//		List<ModInstance> mods = config.getParticipantMods();
		
		StringBuffer testLog = TestContext.getTestMetadata().getTestLog();
		
		String expectedOutputSourcePath = config.getExpectedOutputSource();
		if(!expectedOutputSourcePath.isEmpty()) {
			
			TestRunOutput expectedOutputReadFromSource = new ExpectedOutput(expectedOutputSourcePath);
			record.setBenchmarkOutput(expectedOutputReadFromSource);
		}
		
		testRunner.runTest(record);
		
		//... and if the cognitive verifier is defined...
		if (cognitiveVerifierDefined) {
			
			IntegrationTest integrationTestMetadata = this.getClass().getAnnotation(IntegrationTest.class);
			
			try {
				
				logger.debug("Running cognitive verifications for "+this.getClass()+".");
				runVerificationMethods(integrationTestMetadata.cognitiveVerifier());
			}
			catch (InvocationTargetException exception) {
				
				throw new CognitoTestExecutionException("CognitiveVerificationFailure",
						exception.getTargetException().getMessage());
			}
			catch (ReflectiveOperationException exception) {
				
				throw new CognitoTestExecutionException("CognitiveVerificationInvokationFailed",
						exception.getClass()+": "+exception.getMessage());
			}
		}
		
		logger.debug("Gathering cognitive verification failure logs.");
		
		if(TestContext.getTestMetadata().getCognitiveVerificationFailureLog() != null) {
			
			testLog.append(System.lineSeparator() + TestContext.getTestMetadata().getCognitiveVerificationFailureLog());
		}
		
		logger.info(("Running cognitive verifications for "+this.getClass()+"... Complete"));
		
		logger.debug("Resetting Cognitive Verification Failure Log...");
		
		TestContext.getTestMetadata().setCognitiveVerificationFailureLog(null);
		
	}
	
	
	private void runAndRecordRegressionTest() {
		
		testRunner.runTest(record);
	}

	private void runAndRecordSystemTest() {
		
		StringBuffer testLog = TestContext.getTestMetadata().getTestLog();
		
		String expectedOutputSourcePath = ((SystemTestConfiguration)TestContext.getTestMetadata().getTestConfiguration()).getExpectedOutputSource();
		if(!expectedOutputSourcePath.isEmpty()) {
			
			//XXX Feature for Release 2 >> @SystemTest.expectedOutputDefinition --> would allow end users to define customized ExpectedOutput objects.
			TestRunOutput expectedOutputReadFromSource = new ExpectedOutput(expectedOutputSourcePath);
			record.setBenchmarkOutput(expectedOutputReadFromSource);
		}
		
		testRunner.runTest(record);
		
		//... and if the cognitive verifier is defined...
		if (cognitiveVerifierDefined) {
			
			SystemTest systemTestMetadata = this.getClass().getAnnotation(SystemTest.class);
			
			try {
				
				logger.debug("Running cognitive verifications for "+this.getClass()+".");
				runVerificationMethods(systemTestMetadata.cognitiveVerifier());
			}
			catch (InvocationTargetException exception) {
				
				throw new CognitoTestExecutionException("CognitiveVerificationFailure",
						exception.getTargetException().getMessage());
			}
			catch (ReflectiveOperationException exception) {
				
				throw new CognitoTestExecutionException("CognitiveVerificationInvokationFailed",
						exception.getClass()+": "+exception.getMessage());
			}
		}
		
		logger.debug("Gathering cognitive verification failure logs.");
		
		if(TestContext.getTestMetadata().getCognitiveVerificationFailureLog() != null) {
			
			testLog.append(System.lineSeparator() + TestContext.getTestMetadata()
				.getCognitiveVerificationFailureLog());
		}
		
		logger.info(("Running cognitive verifications for "+this.getClass()+"... Complete"));
		
		logger.debug("Resetting Cognitive Verification Failure Log...");
		
		TestContext.getTestMetadata().setCognitiveVerificationFailureLog(null);
	}

	/**
	 * Runs the test and records the test log to a flat file, details of which are configured 
	 * in the "<tt>report-generation</tt>" field of the XML Cognito configuration.
	 */
	@After
	public final void runAndRecordTest() {
		
		logger.info("Run and record test.");
		
		StringBuffer testLog = TestContext.getTestMetadata().getTestLog();
		
		if (testLog.length() == 0) {
			
			testLog.append("------------------"
						+ " C O G N I T O   T E S T   R U N "
						+ "------------------"
						+ System.lineSeparator());
			testLog.append("TEST TYPE:\t" + deriveTestType(TestContext.getTestMetadata().getType()) + System.lineSeparator());
			testLog.append("TEST SET:\t" + TestContext.getTestMetadata().getTestSet() + System.lineSeparator());
			testLog.append("TEST ENV:\t" + TestContext.getTestMetadata().getTestConfiguration().getEnvironmentsDetail() + System.lineSeparator());
			testLog.append("---------------------------------------------------------------------" + System.lineSeparator());
		}
		
		//Recording scenario header / test metadata
		testLog.append(TestContext.getTestMetadata());
		
		if (TestContext.getTestMetadata().getType() == TYPE_SystemTest) {
			
			runAndRecordSystemTest();
		}
		else if (TestContext.getTestMetadata().getType() == TYPE_RegressionTest) {
			
			runAndRecordRegressionTest();
		}
		else if (TestContext.getTestMetadata().getType() == TYPE_IntegrationTest) {
			
			runAndRecordIntegrationTest();
		}
		
		
		
		logger.info("Run and record test... Complete");
	}
	

	/**
	 * Runs all the methods annotated with the {@link Verification} annotation.
	 * @param cognitiveVerifier represents the {@link CognitiveVerifier} from which the verifications need to be run.
	 * @throws ReflectiveOperationException
	 */
	private void runVerificationMethods(Class<? extends CognitoImplementable> cognitiveVerifier)
			throws ReflectiveOperationException {
		
		//Resetting the failure count on current scenario verifications.
		TestContext.getTestMetadata().setCognitiveVerificationFailureCount(0);
		
		Method[] methods = cognitiveVerifier.getDeclaredMethods();
		CognitiveVerifier verifier = (CognitiveVerifier) cognitiveVerifier.newInstance();
		
		for (Method method : methods) {
			
			if(method.isAnnotationPresent(Verification.class)) {
				
				logger.debug("Running verification method: " + method.getName());
				method.invoke(verifier, record);
			}
		}
		
		if (TestContext.getTestMetadata().getCognitiveVerificationFailureCount() > 0) {
			
			try {
				Assert.fail("Test failed with "
						+Integer.toString(TestContext.getTestMetadata().getCognitiveVerificationFailureCount())
						+ " cognitive verification failure(s)! Failure log generated at "+ TestContext.getConfiguration().getReportGenerationConfiguration().getReportGenerationPath()+".");
			} catch (CognitoConfigurationException e) {
				
				throw new CognitoTestExecutionException("UnknownException", e.getMessage());
			}
		}
	}
	
	/**
	 * Sets up the test before run.
	 */
	@Before
	public final void setupTest() {
		
		logger.debug("Setting up test scenario.");
		deriveTestMetadata();
		logger.debug("Validating test runner.");
		//TODO Aditya: Add testRunner validation here.
		logger.info("Validating test runner... Complete.");
		StringBuffer version = new StringBuffer();
			
		if (testRunner instanceof ModRunner) {
			
			version.append(((ModRunner)testRunner).getModVersion());
		}
		else if (testRunner instanceof TestIntegrator) {
			
			version.append(((TestIntegrator)testRunner).getVersion());
		}
		
		TestContext.getTestMetadata().setVersion(version.toString());
		logger.info("Setting up test scenario... Complete.");
	}
	
	/**
	 * Checks if the fragments of the mod instance are valid by checking for their presence in the configuration.
	 * @param modInstanceFragments represents an array containing 2 elements, mod name at [0] and environment at [1].
	 */
	private void validateModInstance(String [] modInstanceFragments) throws InvalidConfigurationException {
		
		logger.debug("Validating Mod Instance.");
		logger.debug("Validating Integration Test -> Mod Instance.");

		
		if (!configuration.getAllModNames().contains(modInstanceFragments[0].trim())) {
			
			throw new InvalidConfigurationException("ModReferenceException", modInstanceFragments[0]+" is not present in the configuration.");
		}
		logger.info("Validating Mod Instance...Complete");
	}
	

	/**
	 * Validates the below points:<br>
	 * <ol>
	 * <li><tt>modName</tt> passed as argument is a valid mod defined in the configuration.</li>
	 * <li><tt>modName</tt> passed as argument has at least 2 environments for a regression test.</li>
	 * <li><tt>benchMarkEnvironment</tt> and <tt>targetEnvironment</tt> arguments correspond to valid 
	 * environments of the mod, provided the mod is valid.</li>
	 * </ol>
	 * @param modName
	 * @param benchMarkEnvironment
	 * @param targetEnvironment
	 * @param class1 
	 */
	private void validateRegressionTestConfiguration(String modName, String benchMarkEnvironment,
			String targetEnvironment, Class<? extends CognitoImplementable> modOperatorSubclass) throws InvalidConfigurationException {
		
		logger.debug("Validating Regression Test Configuration.");
		
		//mod name not found in XML config...
		if(!configuration.getAllModNames().contains(modName)) {
			
			throw new InvalidConfigurationException("ModNotFound", modName);
		}
		//mod does not have enough environments for regression - needs atleast 2.
		if(configuration.getAllEnvironmentNames(modName).size() < 2){
			
			throw new InvalidConfigurationException("InsufficientEnvsConfigured", configuration.getAllEnvironmentNames(modName).size()+"");
		}
		else {
			
			//target environment specified not found among mod environments...
			if (!configuration.getAllEnvironmentNames(modName).contains(targetEnvironment)) {
			
				throw new InvalidConfigurationException("TargetEnvNotFoundInMod", targetEnvironment, modName);
			}
			
			//benchmark environment specified not found among mod environments...
			if (!configuration.getAllEnvironmentNames(modName).contains(benchMarkEnvironment)) {
			
				throw new InvalidConfigurationException("BenchmarkUnavailableInMod", benchMarkEnvironment, modName);
			}
			
			//benchmark and target environment cannot be the same environment...
			if (benchMarkEnvironment.equals(targetEnvironment)) {
			
				throw new InvalidConfigurationException("TargetSameAsBenchmark", targetEnvironment , benchMarkEnvironment);
			}
			
			//mod operator should be a subclass of Regression
			if (
					!RegressionTestRunner.class.isAssignableFrom(modOperatorSubclass)
				||	modOperatorSubclass == RegressionTestRunner.class
				||	modOperatorSubclass == CognitoImplementable.class
					
				) {
				
				throw new InvalidConfigurationException("TestableModOperatorNotExtended", modOperatorSubclass.toString(), RegressionTestRunner.class.toString());
			}
		}
		
		logger.info("Validating Regression Test Configuration...Complete");
	}
	
	/**
	 * Validates the below points:<br>
	 * <ol>
	 * <li><tt>modName</tt> passed as argument is a valid mod defined in the configuration.</li>
	 * <li><tt>targetEnvironment</tt> argument corresponds to a valid 
	 * environment of the mod, provided the mod is valid.</li>
	 * <li><tt>cognitiveVerifierSubClass</tt> passed as argument is of type <tt>org.cognito.core.implementables.CognitiveVerifier</tt>.</li>
	 * </ol>
	 * @param modName
	 * @param targetEnvironment
	 * @param cognitiveVerifierSubClass
	 * @param expectedOutputSource 
	 * @param class1 
	 * @throws InvalidConfigurationException 
	 */
	private void validateSystemTestConfiguration(String modName, String targetEnvironment, Class<? extends CognitoImplementable> cognitiveVerifierSubClass, Class<? extends CognitoImplementable> modOperatorSubclass, String expectedOutputSource) throws InvalidConfigurationException {
		
		logger.debug("Validating System Test Configuration.");
		
		//mod name not found in XML config...
		if (!configuration.getAllModNames().contains(modName)) {
			throw new InvalidConfigurationException("ModNotFound", modName);
		}
		
		//target environment not found among the environments associated with the mod specified.
		if (!configuration.getAllEnvironmentNames(modName).contains(targetEnvironment)) {
			
			throw new InvalidConfigurationException("TargetEnvNotFoundInMod", targetEnvironment, modName);
		}
		
		if (expectedOutputSource.isEmpty() && cognitiveVerifierSubClass == CognitoImplementable.class) {
			
			throw new InvalidConfigurationException("NoExpectedOuputSourceOrVerifierFound");
		}
		
		else if (
				cognitiveVerifierDefined
				&& (
						!CognitiveVerifier.class.isAssignableFrom(cognitiveVerifierSubClass)
					||	cognitiveVerifierSubClass == CognitiveVerifier.class
				)
			) {
			
			throw new InvalidConfigurationException("CognitiveVerifierNotExtended", cognitiveVerifierSubClass.toString());
		}
		
		if (
				!SystemTestRunner.class.isAssignableFrom(modOperatorSubclass)
			||	modOperatorSubclass == SystemTestRunner.class
			||	modOperatorSubclass == CognitoImplementable.class
				
			) {
			
			throw new InvalidConfigurationException("TestableModOperatorNotExtended", modOperatorSubclass.toString(), SystemTestRunner.class.toString());
		}
		
		logger.info("Validating System Test Configuration... Complete");
	}
}