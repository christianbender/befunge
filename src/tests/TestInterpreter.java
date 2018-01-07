/*
 * author: Christian Bender
 * date: 07.01.2018
 * class: TestInterpreter
 * 
 * This file contains a test suite for the befunge-interpreter.
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Vector;

import interpreter.Interpreter;

public class TestInterpreter {

	@Test
	public void testCase1Scanner() {
		Interpreter.init();
		Interpreter.scanner("4 3 + . @");
		char ch = Interpreter.matrix[0][4];
		assertEquals(ch,'+');
		//Interpreter.scanner(";  "); // test for unknow character
	}
	
	

}
