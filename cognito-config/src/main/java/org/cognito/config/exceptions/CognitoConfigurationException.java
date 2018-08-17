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
package org.cognito.config.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.cognito.config.parsers.XmlConfigurationBuilder;

/**
 * Thrown when a generic configuration exception is encountered on Cognito.
 * 
 * @author Aditya Karnad
 */
public class CognitoConfigurationException extends Exception {
	
	static {
		logger = Logger.getLogger(XmlConfigurationBuilder.class);
	}
	private static Logger logger;
	private static final long serialVersionUID = 8997398665222908309L;
	private static ResourceBundle errors = ResourceBundle.getBundle("cognito-config-errors");
	
	/**
	 * Constructs a {@linkplain CognitoConfigurationException} using an error code and a message.
	 * 
	 * @param errorCode represents an error code.
	 * @param args represents the arguments passed appropriate to the message corresponding to the error code 
	 * passed as argument.
	 */
	public CognitoConfigurationException(String errorCode, Object... args) {
		
		//TODO Aditya: Look into the type mismatch warning...
		super(MessageFormat.format(errors.getString(errorCode), args));
		
		StringWriter stringWriter = new StringWriter();
		printStackTrace(new PrintWriter(stringWriter));

		StringBuffer buffer = new StringBuffer(
				MessageFormat.format(errors.getString(errorCode), args));
		
		buffer.append(System.lineSeparator()+ System.lineSeparator());
		buffer.append("******************* STACK TRACE *******************");
		buffer.append(System.lineSeparator()
				+ stringWriter
				+ System.lineSeparator());
		buffer.append("***************************************************"
				+ System.lineSeparator());
		
		logger.error(buffer);
	}
}