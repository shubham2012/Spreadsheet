# Spreadsheet
Spreadsheet calculator
A spreadsheet consists of a two-diamensional array of cells, labeled A1, A2, etc. 
Rows are identified using letters, columns by numbers. 
Each cells contains either an integer(its value) or an expression.
Expression contain integers, cell references, and the operators + - * / with the usual rule of evaluation- note that the input is RPN and should be evaluated in stack order.

Write a program to read a spreadsheet from stdin,
evaluate the values of all the cells, and write the output to stdout.

Input definitaion:
* Line 1: Two integers, defining the width and height of the soreadsheet(n,m)
* n*m lines each containing an expression which is the value of the corresponding cell(cells enumerated in the order A1, A2, A<n>, B1, ...)

Your program must output its data in the same formate, but each cell should be reduced to a single floating-point value. refer the following example for the same.

Input
3 2 
A2 
4 5* 
A1
A1 B2 / 2 +
3
39 B1 B2 * /

Output
3 2 
20.00000 
20.00000 
20.00000 
8.66667 
3.00000 
1.50000

Comments: 
Java programs should compile using the standard Oracle JDK, version 1.7, 1.8. or 1.9
* Java solutions should only use the standard Java libraries (unit tes@ng libs are OK). o Java solu@ons should define their main method in a class named Spreadsheet.java o We will use the following command to build your code:“javac *.java”. If you
require build op@ons more sophis@cated than that, you must include a gradle
or maven build file.
* Your code should be runable by like this: “cat spreadsheet.txt | java Spreadsheet”
* Your program should detect cyclic dependencies in the input data, report these in a  sensible manner, and exit with a non8zero exit code. 
* All numbers in the input are positive integers, but internal calculations and output  should be in double precision floating point. 
* You can assume that there are no more than 26 rows  A8Z) in the spreadsheet; however  there can be any number of columns  18n). 
* There should be one output per line, lines organized in this order: A1, A2, ... A<n>, B1,  B2, ... B<n> ... When printing out the numbers, please use formatting as below: 
* In Java, use String.format “%.5f”, val)  
* Extending the expression grammar to support negative numbers as inputs.
* Extending the expression grammar to include an increment or decrement operator  ++ or 88) 
