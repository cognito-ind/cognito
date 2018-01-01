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
package mockups.mockintegration.entities;

import java.util.List;

/**
 * This is a POJO output derived from the sentence parser.
 * Think of this as an intermediate output directly obtained from the module which would later be processed by 
 * your defined TestRunOutput (which in this case is {@link SentenceParserOutput}).
 * @author aditya.karnad
 *
 */
public class SentenceParserProcessedOutput {

	private byte operator;
	private List<Integer> operands;
	public byte getOperator() {
		return operator;
	}
	public void setOperator(byte operator) {
		this.operator = operator;
	}
	public List<Integer> getOperands() {
		return operands;
	}
	public void setOperands(List<Integer> operands) {
		this.operands = operands;
	}
	
}
