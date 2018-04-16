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

	/**
	 * Represents the "mod name", where a mod can be any module with well defined input, functionality and output.
	 * <br><br><i><b>Note:</b> The mod name for the module would need to be specified in the <tt>cognito-cfg.xml</tt> file.</i>
	 */
	String modName() default "";
	
	/**
	 * Represents the environment on which the tests are to be run.
	 * <br><br><i><b>Note:</b> All the environments on which the mod is available for test runs would need to be specified 
	 * in the <tt>cognito-cfg.xml</tt> file.</i>
	 */
	String targetEnvironment() default "";
	
	/**
	 * Represents the environment which is known to be running the most correct version of the mod.
	 * In most cases this would be the production environment.
	 * <br><br><i><u>For example:</u> If a regression test on a module is required to find out if after some recent 
	 * "technical only" changes released to an environment A (lower environment),  have had an undesirable "functional" impact
	 * on the module, the <tt>targetEnvironment</tt> would be "A" and the <tt>benchMarkEnvironment</tt> would be 
	 * Production / any other environment where the change has not yet been released.</i>
	 * <br><br><i><b>Note:</b> All the environments on which the mod is available for test runs would need to be specified in the <tt>cognito-cfg.xml</tt> file.</i>
	 */
	String benchMarkEnvironment() default "";
	
	/**
	 * Represents the class defining the module operations.
	 * The definitions of how the tests would be run on the module, format of the test report, 
	 * etc. will be defined in this class.
	 */
	Class<? extends CognitoImplementable> moduleRunner() default CognitoImplementable.class;
}