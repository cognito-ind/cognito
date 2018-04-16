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
package org.cognito.core.util.recorders;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.cognito.core.RunnableTestSet;

/**
 * Recorder that records text to a flat file.
 * @author Aditya Karnad
 */
public class GenericRecorder {
	
	/**
	 * Records the text in <tt>content</tt> to the file specified by <tt>filePath</tt>.
	 * 
	 * @param filePath represents the file with path where the file will be written.
	 * @param content represents the text that has to be written to the file specified by <tt>filePath</tt>.
	 * @throws IOException
	 */
	public static void record(String filePath, String content) throws IOException {
		
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		try {
			fileWriter = new FileWriter(filePath);
			writer = new BufferedWriter(fileWriter);
			
			writer.write(content);
		}
		catch (IOException e) {
			
			//Just passing the same exception on to the calling method...
			throw new IOException(e.getMessage());
		}
		finally {
			
			if(writer != null) {
				
				writer.close();
			}
		}	
	}
	
	/**
	 * Returns String equivalents of the test types.
	 * @param type represents the type indicator.
	 * @return String equivalent of the test type passed as argument.
	 * 
	 * @see RunnableTestSet#TYPE_SystemTest
	 * @see RunnableTestSet#TYPE_RegressionTest
	 * @see RunnableTestSet#TYPE_IntegrationTest
	 */
	public static String deriveTestType(byte type) {
		
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
}