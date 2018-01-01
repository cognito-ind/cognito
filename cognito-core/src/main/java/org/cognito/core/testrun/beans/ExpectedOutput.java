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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cognito.core.TestContext;
import org.cognito.core.exceptions.InvalidExpectedOutputException;

//TODO Aditya: Complete this doc.
/**
 * 
 * @author Aditya Karnad
 *
 */
public class ExpectedOutput extends TestRunOutput {

	static {
		logger = Logger.getLogger(ExpectedOutput.class);
	}

	private static Logger logger;

	private static byte FIELD_ID = 0;
	private static byte FIELD_VALUE = 1;

	private BufferedReader bufferedReader;


	/**
	 * This constructor builds the expected output from the file at path passed as argument.
	 * @param expectedOutputSourcePath represents the file path where the expected output file would be available for reading.
	 */
	public ExpectedOutput(String expectedOutputSourcePath) {

		validateExpectedOutputSource(expectedOutputSourcePath);

		buildOutputFromFile(expectedOutputSourcePath);
	}


	private boolean verifyOutputFileStructure(String line) {

		String secondPartOfLine;
		if(line.contains(":")){

			secondPartOfLine = line.substring(line.indexOf(":")+1);
			if(secondPartOfLine.contains("=")){
				//TODO Neeraj: Implement exception throw if the file has an invalid line in it - Use message in resource bundle: ExpectedOutputSourceFormatError
				return true;
			}
		}
		return false;
	}

	private void validateExpectedOutputSource(String expectedOutputSourcePath) 
			throws InvalidExpectedOutputException {

		logger.debug("Validating the expected output source file...");
		String expectedScenarioName = TestContext.getTestMetadata().getScenario();
		List<String> actualScenarioNames = new ArrayList<String>();

		String scenario;

		try {

			if(readFileData(expectedOutputSourcePath).isEmpty()) {

				throw new InvalidExpectedOutputException("ExpectedOutputEmptyFile", expectedOutputSourcePath);
			}

			for (String line : readFileData(expectedOutputSourcePath)) {

				if(!verifyOutputFileStructure(line)){

					throw new InvalidExpectedOutputException("ExpectedOutputSourceFormatError", line);
				}

				scenario = readTestScenarioName(line);
				actualScenarioNames.add(scenario);
			}


		} catch (FileNotFoundException e) {

			throw new InvalidExpectedOutputException("ExpectedOutputSourceNotFound", expectedOutputSourcePath);

		} catch (IOException e) {

			throw new InvalidExpectedOutputException("ExpectedOutputReadFailure", e.getClass()+" : "+e.getMessage());
		}

		logger.info("Expected Scenario Name : " + expectedScenarioName);



		if(!actualScenarioNames.contains(expectedScenarioName)){
			throw new InvalidExpectedOutputException("ExpectedOutputScenarioNotFound", expectedScenarioName, expectedOutputSourcePath);
		}

		//TODO Neeraj (COMPLETED): If File is Empty, throw ExpectedOutputEmptyFile 

		//TODO Neeraj (COMPLETED): 1. Implement exception throw if the file does not exist - Use message in resource bundle: ExpectedOutputSourceNotFound

	}


	private List<String> readFileData(String expectedOutputSourcePath) throws IOException{
		List<String> expectedOutputList = new ArrayList<String>();
		String line;

		FileReader reader = new FileReader(expectedOutputSourcePath);
		bufferedReader = new BufferedReader(reader);

		while((line = bufferedReader.readLine()) != null){

			expectedOutputList.add(line);
		}
		reader.close();
		return expectedOutputList;
	}

	private String readTestScenarioName(String outputLine){
		String scenarioName = null;

		if( outputLine != null ){

			String[] parts = outputLine.split(":");
			if( parts.length>0){

				scenarioName = parts[0];
			}
		}

		return scenarioName;
	}
	protected void buildOutputFromFile(String expectedOutputSourcePath) {

		logger.debug("Building an output object from the expected output source file...");
		List <ComparableField> listOfFields = new ArrayList<ComparableField>();

		String currentScenario = TestContext.getTestMetadata().getScenario();

		try {
			FileReader reader = new FileReader(expectedOutputSourcePath);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {

				//If the current line defines a field in the expected output for the running scenario...
				if(line.startsWith(currentScenario)) {

					//listOfFields.add(new ComparableField(readFieldId(line), readFieldValue(line)));
					listOfFields.add(new ComparableField((String)readFieldFromOutput(line, FIELD_ID), readFieldFromOutput(line, FIELD_VALUE)));
				}
			}
			reader.close();

			outputFields = listOfFields;
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}


	private Object readFieldValue(String line) {

		line = line.substring(line.indexOf(':')+1);
		String value = line.split("=")[1].trim();

		if (isNumeric(value)) {

			return Double.parseDouble(value);
		}
		else if (value.equalsIgnoreCase("true")) {

			return true;
		}
		else if (value.equalsIgnoreCase("false")) {

			return false;
		}
		else {

			return value;
		}
	}

	private String readFieldId(String line) {

		//TODO Neeraj: 2A. Implement using RegEx the throwing of an exception if this is an invalid line - Use message in resource bundle: ExpectedOutputSourceFormatError

		line = line.substring(line.indexOf(':')+1);
		String id = line.split("=")[0].trim();

		return id;
	}

	private boolean isNumeric(String arg)
	{

		try {

			Double.parseDouble(arg);
		}
		catch(NumberFormatException numFormatException) {

			return false;
		}

		return true;
	}

	private Object processedOutput(String fieldSegment) {

		if (isNumeric(fieldSegment)) {

			return Double.parseDouble(fieldSegment);
		}
		else if (fieldSegment.equalsIgnoreCase("true")) {

			return true;
		}
		else if (fieldSegment.equalsIgnoreCase("false")) {

			return false;
		}
		else {

			return fieldSegment;
		}

	}
	private Object readFieldFromOutput(String outputLine, byte segment){
		String fieldSegment = null;
		String fieldString = null;

		if( outputLine != null ){

			String[] parts = outputLine.split(":");
			if( parts.length>0){

				fieldString = parts[1];
				String[] fieldStringParts = fieldString.split("=");
				fieldSegment = fieldStringParts[segment];
			}
		}

		if(segment==FIELD_VALUE){

			return processedOutput(fieldSegment);
		}

		return fieldSegment;
	}

	@Override
	public void extractOutputFieldsFromModOutput(Object modOutput) {}

}
