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
package org.cognito.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cognito.config.beans.CognitoImplementable;

/**
 * Specifies that the test-set class to which this annotation is applied is a Regression Test Set.
 * @author Aditya Karnad
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegressionTest {

	String modName() default "";
	String targetEnvironment() default "";
	String benchMarkEnvironment() default "";
	
	/**
	 * Represents the class defining the module operations.
	 * The definitions of how the tests would be run on the module, format of the test report, 
	 * etc. will be defined in this class.
	 */
	Class<? extends CognitoImplementable> moduleRunner() default CognitoImplementable.class;
}