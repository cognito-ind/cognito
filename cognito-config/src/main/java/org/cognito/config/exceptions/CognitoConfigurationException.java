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

public class CognitoConfigurationException extends Exception {
	
	static {
		logger = Logger.getLogger(XmlConfigurationBuilder.class);
	}
	private static Logger logger;
	private static final long serialVersionUID = 8997398665222908309L;
	private static ResourceBundle errors = ResourceBundle.getBundle("cognito-config-errors");

	public CognitoConfigurationException(String errorCode, String... message) {

		super(MessageFormat.format(errors.getString(errorCode), message));
		
		StringWriter stringWriter = new StringWriter();
		printStackTrace(new PrintWriter(stringWriter));

		StringBuffer buffer = new StringBuffer(
				MessageFormat.format(errors.getString(errorCode), message));
		
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