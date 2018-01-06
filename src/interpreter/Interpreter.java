/*
 * author: Christian Bender
 * date:
 * 
 * This is a Befunge-93 interpreter for the command-line.
 */

package interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Pattern;


public class Interpreter {
	
	// befung playfield
	public static char [][] matrix;
	
	// instruction pointer comprises x- and y- coordinate.
	public static int x = 0;
	public static int y = 0;
	
	// stack for the befunge language
	public static Stack<Object> stack;
	
	// This type is for determines the direction of the code flow.
	public static enum Navigate {right, left, top, bottom};
	
	// flag for ignoring warnings.
	public static boolean isExit = true;

	/**
	 * @param args
	 * purpose: reads a text file (befunge code) from the command-line 
	 * and interprets it.
	 * assumes: the file-path comes first. 
	 */
	public static void main(String[] args) {
		
		// flag for printing matrix (playfield)
		boolean printMatrix = false;
		
		// flag for printing the remaining elements onto stack.
		boolean printStack = false;
		
		// make sure at least given a path.
		if (args.length >= 1) {

	        File file = new File(args[0]);
	        
	        // iterates over all command-line arguments
	        // assumes the file-path comes first!
	        for (String option : args) {
	        	switch (option) {
	        	case "-printMatrix":	// catch print-matrix command
	        		printMatrix = true;
	        		break;
	        	case "-printStack":		// catch print-stack command
	        		printStack = true;
	        		break;
	        	case "-noExit":
	        		isExit = false;
	        		break;
	        	}
	        }
	        
	        // removes all read tokens. the token-list must be empty. 
	        init(); // initializes playfield

	        if (!file.canRead() || !file.isFile())
	            System.exit(0);

	            BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(file));
	            String line = null;
	            
	    		// fetches each line of file and passes it into scanner. 
	            while ((line = in.readLine()) != null) {
	                scanner(line);
	            }
	            
	            // prints the playfield
	            if (printMatrix) {
	            	printPlayfield();
	            }
	            
	            // invokes parser
	            parser();
	            
	            // prints the remaining elements onto stack.
	            if (printStack) {
	            	for (int i = 0; i < stack.size(); i++) {
	            		System.out.println("STACK-PRINT --> " + i);
	            	}
	            }
	            
	        } catch (IOException e) { // error case
	        	System.err.println("Error: unable access file");
	        } finally { // tidy up
	            if (in != null)
	                try {
	                    in.close();
	                } catch (IOException e) {
	                }
	        } 
		} else { // error case
			System.err.println("Error: no path found!");
			System.exit(1);
		}
		
	} // end of main method
	
	
	/**
	 * simple scanner
	 * @param code
	 * collects the tokens and save it in the vector 'matrix'
	 * line by line!
	 */
	public static void scanner (String code) {
		
		// saves the current read character.
		char ch = ' ';
			
		// iterates over the source-code.
		for (int i = 0; i < code.length(); i++) {
			
			// fetch next character
			ch = code.charAt(i);
			switch (ch) {
			case '0':	// catch a number between 0 and 9
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '+':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '-':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '*':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '/':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '%':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '!':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '`':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '>':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '<':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '^':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case 'v':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '?':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '_':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '|':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '"':	// catch the begin of a string
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case ':':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '\\':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '$':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '.':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case ',':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '#':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case 'g':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case 'p':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '&':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '~':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '@':
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
				
			case ' ':	
				if (y < 80) {
					matrix[x][y] = ch;
					y++;
				} else {
					System.err.println("Error: too large tokens --> " + ch);
					System.exit(1);
				}
				break;
			case '\n':	// catch whitespace character
			case '\t':
			case '\r':
			case '\b':
				break;
			default:	// error case
				if (Character.isLetter(ch)) {
					if (y < 80) {
						matrix[x][y] = ch;
						y++;
					} else {
						System.err.println("Error: too large tokens --> " + ch);
						System.exit(1);
					}
				} else {
					System.err.println("Error: unknow character --> " + ch);
					System.exit(1);
				}
				
				break;
			} // end switch
		} // end for
		
		// make a new line for the source code.
		if (x+1 < 25) {
			x++;
		}
		y = 0;
		
	}
	
	
	/**
	 * initializes matrix (playfield)
	 */
	public static void init() {
		matrix = new char[25][80];
		stack = new Stack<Object>();

		// instruction pointer set to 0/0
		x = 0;
		y = 0; 
		
		// initializes the playfield with empty characters.
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 80; j++) {
				matrix[i][j] = ' ';
			}
		}
	}
	
	
	/**
	 * prints the playfield onto console.
	 */
	public static void printPlayfield() {
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 80; j++) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	
	/**
	 * parser: parses the tokens in the matrix.
	 */
	public static void parser() {
		
		// current read token
		char token = ' ';
		
		// shows navigation in the program
		boolean isJump = false;
		
		// determines the direction.
		Navigate direction = Navigate.right;
		
		// flag for ascii-modus.
		boolean isASCII = false;
		
		// initializes the instruction pointer with 0/0
		x = y = 0;
		
		// fetches next token
		token = matrix[x][y];
		
		Scanner scanner = new Scanner(System.in);
		
		// while token isn't equal to '@' iterates over the tokens.
		while (token != '@' && (x < 25) && (y < 80)) {
			switch (token) {
			case '0':	// catch a number between 0 and 9
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				if (!isASCII) {
					stack.push(((int)token) - 48);
				} else {
					stack.push(token);
				}
				break;
			case '+':	// catch arithmetic operators
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							stack.push(((int) stack.pop())
									+ ((int) stack.pop()));
						} catch (ClassCastException e) {
							System.err
									.println("\nIN + --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					} 
					// TODO error empty stack
				} else {
					stack.push(token);
				}
				break;
			case '-':
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							stack.push(((int) stack.pop())
									- ((int) stack.pop()));
						} catch (ClassCastException e) {
							System.err
									.println("\nIN - --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
					// TODO error case for empty stack
				} else {
					stack.push(token);
				}
				break;
			case '*':
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							stack.push(((int) stack.pop())
									* ((int) stack.pop()));
						} catch (ClassCastException e) {
							System.err
									.println("\nIN * --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
					// TODO error case for empty stack
				} else {
					stack.push(token);
				}
				break;
			case '/':
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							stack.push(((int) stack.pop())
									/ ((int) stack.pop()));
						} catch (ClassCastException e) {
							System.err
									.println("\nIN / --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
					// TODO error case for empty stack
				} else {
					stack.push(token);
				}
				break;
			case '%':	// catch modulo operator
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							int x = ((int) stack.pop());
							int y = ((int) stack.pop());
							stack.push(((int) x % y));
						} catch (ClassCastException e) {
							System.err
									.println("\nIN % --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
				} else {
					stack.push(token);
				}
				break;
			case '!':	// catch logic not operator
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 1) {

						// make sure the element are number (int)
						try {
							if (((int) stack.pop()) != 0) {
								stack.push(1);
							} else {
								stack.push(0);
							}
						} catch (ClassCastException e) {
							System.err
									.println("\nIN ! --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					} else {
						
						System.err.println("\nIN ! --> Warning: empty stack!");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '`':	// catch compare operator >
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 2) {

						// make sure the two elements are numbers (int)
						try {
							int x = ((int) stack.pop());
							int y = ((int) stack.pop());
							stack.push((x > y) ? 1 : 0);
						} catch (ClassCastException e) {
							System.err
									.println("\nIN ` --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
				} else {
					stack.push(token);
				}
				break;
			case '.':	// catch print statement for numbers
				if (!isASCII) {
					// make sure stack has at least two elements on it.
					if (stack.size() >= 1) {

						// make sure the two elements are numbers (int)
						try {
							int x = ((int) stack.pop());
							System.out.print(x); // print the pop number.
						} catch (ClassCastException e) {
							System.err
									.println("\nIN . --> Error: no number on stack!");
							if (isExit)
								System.exit(1);
						}
					}
				} else {
					stack.push(token);
				}
				break;
			case '>':	// catch navigate commands
				if (!isASCII) {
					direction = Navigate.right;
				} else {
					stack.push(token);
				}
				break;
			case '<':
				if (!isASCII) {
					direction = Navigate.left;
				} else {
					stack.push(token);
				}
				break;
			case 'v':
				if (!isASCII) {
					direction = Navigate.bottom;
				} else {
					stack.push(token);
				}
				break;
			case '^':
				if (!isASCII) {
					direction = Navigate.top;
				} else {
					stack.push(token);
				}
				break;
			case '?':	// catch random-direction command
				if (!isASCII) {
					Random random = new Random();
					switch (random.nextInt(4)) {
					case 0:
						direction = Navigate.right;
						break;
					case 1:
						direction = Navigate.left;
						break;
					case 2:
						direction = Navigate.top;
						break;
					case 3:
						direction = Navigate.bottom;
						break;
					} // end switch random choice
				} else {
					stack.push(token);
				}
				break;
			case '_':	// conditional navigate
				if (!isASCII) {
					try {
						if (stack.size() >= 1) {
							Object tmp = stack.pop();
							int x = -1;

							// make sure secure casting
							if (tmp instanceof Character) {
								x = Character.getNumericValue((char) tmp);
							} else if (tmp instanceof Integer) {
								x = (int) tmp;
							}

							if (x == 0) {
								direction = Navigate.right;
							} else {
								direction = Navigate.left;
							}
						} else { // error case
							System.err
									.println("\nIN _ --> Warning: empty stack!");
							if (isExit)
								System.exit(1);
						}
					} catch (ClassCastException e) {
						System.err
								.println("\nIN _ --> Error: no number on stack!");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '|':	// conditional navigate
				if (!isASCII) {
					try {

						// make sure stack has at least one element.
						if (stack.size() == 0) {
							System.err
									.println("\nIN | --> Warning: empty stack!");
							System.exit(1);
						}
						int x = (int) stack.pop();
						if (x == 0) {
							direction = Navigate.bottom;
						} else {
							direction = Navigate.top;
						}
					} catch (ClassCastException e) {
						System.err
								.println("\nIN | --> Error: no number on stack!");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '"':
				if (isASCII) {
					isASCII = false;
				} else {
					isASCII = true;
				}
				break;
			case ':':	// duplicates the top element on stack.
				if (!isASCII) {
					// make sure stack has at least one element.
					if (stack.size() == 0) {
						
						System.err.println("\nIN : --> Warning: empty stack!");
						System.exit(1);
						
					}
					Object tmp = stack.pop();
					stack.push(tmp);
					stack.push(tmp);
				} else {
					stack.push(token);
				}
				break;
			case '\\':	// swaps the top elements on stack.
				if (!isASCII) {
					// make sure stack has at least one element.
					if (stack.size() < 2) {
						System.err
								.println("\nError: to little elements on stack!");
						System.exit(1);
					}
					Object x = stack.pop();
					Object y = stack.pop();
					stack.push(x);
					stack.push(y);
				} else {
					stack.push(token);
				}
				break;
			case '$':	// pops a value from stack and discards it.
				if (!isASCII) {
					// make sure stack has at least one element.
					if (stack.size() == 0) {
						
						System.err.println("\nIN $ --> Warning: empty stack!");
						System.exit(1);
					}
					stack.pop();
				} else {
					stack.push(token);
				}
				break;
			case ',':	// pops a element from the stack and print 
						// it out as ascii-character
				if (!isASCII) {
					// make sure stack has at least one element.
					if (stack.size() == 0) {
						System.err.println("\nIN , --> Warning: empty stack!");
						System.exit(1);
					}
					try {
						Object obj = stack.pop();
						if (obj instanceof Character) {
							System.out.print((char) obj);
						} else if (obj instanceof Integer) {
							System.out.print(" ");
						}
					} catch (ClassCastException e) {
						System.err
								.println("\nIN , --> Error: none ascii-code!");
						System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '#':	// skips next cell
				if (!isASCII) {
					isJump = true;
				} else {
					stack.push(token);
				}
				break;
			case 'p': // put character v in matrix (v,x,y)
				if (!isASCII) {
					if (stack.size() >= 3) {
						try {
							int yLoc = (int) stack.pop();
							int xLoc = (int) stack.pop();
							char v = (char) stack.pop();
							if (xLoc < 25 && xLoc >= 0 && yLoc >= 0
									&& yLoc < 80) {
								matrix[xLoc][yLoc] = v;
							} else {
								System.err.println("\nIN p --> Error: "
										+ "coordinates out of bounds");
								System.exit(1);
							}
						} catch (ClassCastException e) {
							System.err
									.println("\nIN p --> Error: none number!");
							if (isExit)
								System.exit(1);
						}
					} else {
						System.err
								.println("\nIN p --> Error: to little elements on stack");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case 'g': // get character v at (x/y) and pushes it onto stack
				if (!isASCII) {
					if (stack.size() >= 2) {
						try {
							int yLoc = (int) stack.pop();
							int xLoc = (int) stack.pop();
							if (xLoc < 25 && xLoc >= 0 && yLoc >= 0
									&& yLoc < 80) {
								stack.push(matrix[xLoc][yLoc]);
							} else {
								System.err.println("\nIN p --> Error: "
										+ "coordinates out of bounds");
								if (isExit)
									System.exit(1);
							}
						} catch (ClassCastException e) {
							System.err
									.println("\nIN p --> Error: none number!");
							if (isExit)
								System.exit(1);
						}
					} else {
						System.err
								.println("\nIN p --> Error: to little elements on stack");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '&': // asks user for number and push it.
				if (!isASCII) {
					try {
						int number = scanner.nextInt();
						stack.push(number);
					} catch (NumberFormatException e) {
						System.err.print("\nIN & --> please type in a number.");
						if (isExit)
							System.exit(1);
					}
				} else {
					stack.push(token);
				}
				break;
			case '~': // asks user for number and push it.
				if (!isASCII) {
					char character = scanner.next().charAt(0);
					stack.push(character);
				} else {
					stack.push(token);
				}
				break;
			default: // default: pushs charcater (in ascii-mode) 
						// onto the stack.
				if (Character.isLetter(token) || Character.isWhitespace(token)) {
					if (isASCII) {
						stack.push(token);
					}
				}
				break;
				
			} // end switch token
			
			// move the instruction pointer
			switch (direction) {
			case right:
				if (y < 80) {
					// fetches next token
					if (isJump) {
						y += 2;
						isJump = false;
					} else {
						y++;
					}
					
					token = matrix[x][y];
					
				} else {
					y = 0;
					if (x+1 < 25) {
						x++;
						token = matrix[x][y];
					}
				}
				break;
			case left:
				if (y >= 0) {
					if (isJump) {
						y -= 2;
						isJump = false;
					} else {
						y--;
					}
					// fetches next token
					token = matrix[x][y];
					
				} else {
					y = 79;
					if (x-1 >= 0) {
						x--;
						token = matrix[x][y];
					}
				}
				break;
			case top:
				if (x >= 0) {
					if (isJump) {
						x -= 2;
						isJump = false;
					} else {
						x--;
					}
					// fetches next token
					token = matrix[x][y];
				
				} 
//				else {
//					y = 79;
//					if (x-1 >= 0) {
//						x--;
//						token = matrix[x][y];
//					}
//				}
				break;
			case bottom:
				if (x < 25) {
					if (isJump) {
						x += 2;
						isJump = false;
					} else {
						x++;
					}
					// fetches next token
					if (x < 25 && y < 80) {
						token = matrix[x][y];
					}
					
					
				} 
//				else {
//					y = 79;
//					if (x-1 >= 0) {
//						x--;
//						token = matrix[x][y];
//					}
//				}
				break;
				
			} // end switch direction
			
		} // end while loop
	}
	
}
