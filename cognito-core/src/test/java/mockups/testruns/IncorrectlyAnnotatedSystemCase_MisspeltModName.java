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
package mockups.testruns;

import org.cognito.config.annotations.SystemTest;
import org.cognito.core.RunnableTestSet;
import org.junit.Test;

import mockups.verifiers.MockCognitiveVerifier;

@SystemTest(
		modName="sampleWebServiceMod",
		targetEnvironment="DEV",
		cognitiveVerifier=MockCognitiveVerifier.class)
public class IncorrectlyAnnotatedSystemCase_MisspeltModName extends RunnableTestSet {

	//Standard Working case for System Test
	@Test
	public void test_01_base() {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> base case run!");
	}

}

