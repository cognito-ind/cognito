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
package org.cognito.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should be used on a method to indicate 
 * that the method defines a cognitive verification.<br>
 * <i><b>Cognitive Verifications</b> are rules that reference the test input, 
 * and verify if the generated test output follows rules / patterns that 
 * are based on either previously run tests or applicable business rules 
 * governing the module targeted for testing.</i>
 * 
 * @author Aditya Karnad
 */ 

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Verification {
	
}