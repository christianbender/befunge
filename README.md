# Befunge-93 interpreter

This repository contains a befunge-93 interpreter written in Java. The program is a simple
command-line program. You find the program as a JAR-file executable called **befunge93.jar**
in this repository. This interpreter is full portable as jar-file.

## Use of the interpreter

Under Linux you needed made the jar-file executable.

``` chmod +x befunge93.jar ```

For running the interpreter you needed type. 
``` befunge93.jar test.txt ```

or under linux
``` ./befunge93.jar test.txt ```

## Important

The file path comes first! That file must be a simple textfile (.txt).

## Command-line arguments

* -printMatrix

Prints the **playfield** onto the console.
* -printStack

At the end of the program this option prints the remaining elements onto stack.
* -noExit

By default all occurs error messages will exit the program/interpreter.
With the option **-noExit** certain error messages will don't exit the program. 

## Examples

The directory **examples** contains a simple test-file which implements a helloWorld-program
in befunge-93
