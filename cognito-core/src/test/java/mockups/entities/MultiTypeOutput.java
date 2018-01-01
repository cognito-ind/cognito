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
package mockups.entities;

import java.util.List;

public class MultiTypeOutput {
	
	private String text;
	private List<String> list;
	private boolean flag;
	private double numericDouble;
	private int numericInteger;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public double getNumericDouble() {
		return numericDouble;
	}
	public void setNumericDouble(double numericDouble) {
		this.numericDouble = numericDouble;
	}
	public int getNumericInteger() {
		return numericInteger;
	}
	public void setNumericInteger(int numericInteger) {
		this.numericInteger = numericInteger;
	}
	
	
}
