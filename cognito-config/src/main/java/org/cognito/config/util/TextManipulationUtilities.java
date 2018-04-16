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
package org.cognito.config.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains utility methods for text manipulation.
 * @author Neeraj Suthar
 */
public final class TextManipulationUtilities {
	
	/**
	 * Indicates that the test run is a <b>System Test</b> run.
	 */
	public static final byte TYPE_SystemTest = 1;
	
	/**
	 * Indicates that the test run is a <b>Regression Test</b> run.
	 */
	public static final byte TYPE_RegressionTest = 2;
	
	/**
	 * Indicates that the test run is a <b>Integration Test</b> run.
	 */
	public static final byte TYPE_IntegrationTest = 3;
	
	/**
	 * Returns the {@link String} equivalent of the test type.
	 * @param type Test type as a <tt>byte</tt>.
	 * @return String equivalent of the test type.
	 */
	public static String deriveTestTypeText(byte type) {
		
		String typeText = null;
		
		if (type == TYPE_SystemTest) {
			
			typeText = "SYSTEM_TEST";
		}
		else if (type == TYPE_RegressionTest) {
			
			typeText = "REGRESSION_TEST";
		}
		else if (type == TYPE_IntegrationTest) {
			
			typeText = "INTEGRATION_TEST";
		}
		
		return typeText;
	}
	
	/**
	 * Returns <tt>true</tt> if the <tt>input</tt> and <tt>target</tt> arguments are equivalents.<br>
	 * <i><b>For example:</b> "ModuleReadingXmlFile" and "Module Reading Xml File" and 
	 * "module-reading-xml-file" are all equivalents of each other.
	 * @param input represents the input String to be evaluated as an equivalent fo <tt>target</tt>.
	 * @param target represents the target to match and determine if <tt>input</tt> is an equivalent.
	 * @return true if the <tt>input</tt> and <tt>target</tt> arguments are equivalents.
	 */
	public static boolean isAcceptableEquivalent(String input, String target) {
		
		if(input.equalsIgnoreCase(target)) {
			
			return true;
		}
		boolean equivalentStrings;

		if(isEqualWithInitials(input, target)){
			equivalentStrings =true;
		}
		else if(isEqualIgnoringSpecialCharacters(input, target)){
			equivalentStrings = true;
		}
		else{
			equivalentStrings = false;
		}

		return equivalentStrings;
	}


	private static boolean isEqualWithInitials(String input, String target){

		if( isEqualInitialsWithWhiteSpaceAsSplitter(input, target)){
			return true;
		}
		else if( isEqualInitialsWithHiphenAsSplitter(input, target)){
			return true;
		}
		else if( isEqualInitialsWithUnderscoreAsSplitter(input, target)){
			return true;
		}
		else if( isEqualInitialsWithFirstInitial(input, target)){
			return true;
		}
		else if( isEqualInitialsWithLastInitial(input, target)){
			return true;
		}
		else {
			return false;
		}

	}
	private static boolean isEqualInitialsWithWhiteSpaceAsSplitter(String input, String target){

		String[] splittedStrings =target.split(" ");
		String init;
		String initials = "";

		for(String str : splittedStrings){
			init = Character.toString(str.charAt(0));
			initials = initials.concat(init);
		}

		if(initials.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}

	private static boolean isEqualInitialsWithHiphenAsSplitter(String input, String target){

		String[] splittedStrings =target.split("-");
		String init;
		String initials = "";

		for(String str : splittedStrings){
			init = Character.toString(str.charAt(0));
			initials = initials.concat(init);
		}

		if(initials.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}

	private static boolean isEqualInitialsWithUnderscoreAsSplitter(String input, String target){

		String[] splittedStrings =target.split("_");
		String init;
		String initials = "";

		for(String str : splittedStrings){
			init = Character.toString(str.charAt(0));
			initials = initials.concat(init);
		}	

		if(initials.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}

	private static boolean isEqualInitialsWithFirstInitial(String input, String target){

		String[] splittedStrings =target.split(" ");
		String init;
		String initials = "";

		for(int i=0;i<splittedStrings.length;i++){
			if(i==0){
				init = Character.toString(splittedStrings[i].charAt(0));
				initials = initials.concat(init);
			}
			else {
				initials = initials.concat(splittedStrings[i]);				
			}
		}

		if(initials.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}

	private static boolean isEqualInitialsWithLastInitial(String input, String target){

		String[] splittedStrings =target.split(" ");
		String init;
		String initials = "";

		for(int i=0;i<splittedStrings.length;i++){
			if(i==(splittedStrings.length-1)){
				init = Character.toString(splittedStrings[i].charAt(0));
				initials = initials.concat(init);
			}
			else {
				initials = initials.concat(splittedStrings[i]);				
			}
		}

		if(initials.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}




	private static boolean isEqualIgnoringSpecialCharacters(String input, String target){

		input=removeSpecialCharacters(input);
		target=removeSpecialCharacters(target);

		if(input.equalsIgnoreCase(target)){
			return true;
		}

		return false;
	}

	private static String removeSpecialCharacters(String input){
		char[] character = input.toCharArray();
		List<String> specialCharacter = new ArrayList<String>();
		for(int i=0;i<character.length;i++){
			if(Character.isAlphabetic(character[i]) || Character.isDigit(character[i]) ){
			}
			else{
				specialCharacter.add(String.valueOf(character[i]));
			}

		}

		for(String str : specialCharacter){
			if(input.contains(str)){
				input=input.replace(str, "");	
			}
		}

		return input;
	}
}