package spreadsheet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Spreadsheet {

    private static String INCREMENT = "++";
    private static String DECREMENT = "--";
    private static Set<String> operators = new HashSet<String>() {{
        add("*");
        add("/");
        add("-");
        add("+");
    }};

    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();
        String[][] mat = spreadsheet.readInputIntoMatrix();
        if (spreadsheet.processMatrix(spreadsheet, mat))
            return;
        spreadsheet.print(mat);
    }

    /**
     * this method reads input from stdin form, composes matrix of it and returns it as String[][]
     * @return
     */
    private String[][] readInputIntoMatrix() {
        List<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String firstRow = null;
        if (scanner.hasNextLine()) {
            firstRow = scanner.nextLine();
        }
        String[] dimension = firstRow.split(" ");
        String[][] mat = new String[Integer.valueOf(dimension[1])][Integer.valueOf(dimension[0])];
        for (int i = 0; i < mat[0].length * mat.length; i++) {
            list.add(scanner.nextLine());
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[i][j] = list.remove(0);
            }
        }
        return mat;
    }

    /**
     * This method processes the matrix for spreadsheet calculation
     * @param spreadsheet
     * @param mat
     * @return
     */
    protected boolean processMatrix(Spreadsheet spreadsheet, String[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                try {
                    mat[i][j] = String.format("%.5f", Double.parseDouble(spreadsheet.processCellForMultiValue(mat, mat[i][j])));
                } catch (CyclicDependencyException e) {
                    System.out.println(String.format("%s\nError message \"%s\"", e.getCode(), e.getMessage()));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this method takes value of every cell and processes that value and returns the computed result
     * @param mat
     * @param expression
     * @return
     * @throws CyclicDependencyException
     */
    private String processCellForMultiValue(String[][] mat, String expression) throws CyclicDependencyException {
        String[] split = expression.split(" ");
        String finalExpression = "";
        for (String s : split) {
            HashSet<String> set = new HashSet<>();
            String eval = processCellForSingleValue(mat, s, set);
            finalExpression = finalExpression + eval + " ";
        }
        return evaluateExpression(finalExpression.trim());
    }

    /**
     * this method take single calculable value that will can be processed and returns its computed value
     * @param mat
     * @param expression
     * @param set
     * @return
     * @throws CyclicDependencyException
     */
    private String processCellForSingleValue(String[][] mat, String expression, HashSet<String> set) throws CyclicDependencyException {
        String[] split = expression.split(" ");
        String value = split[0];
        if (split.length == 1) {
            if (Character.isDigit(value.charAt(0)) || operators.contains(value)) {
                return expression;
            } else if (value.charAt(0) == '-' && Character.isDigit(value.charAt(1))) {
                return expression;
            } else if (value.contains(INCREMENT) || value.contains(DECREMENT)) {
                if (value.contains(INCREMENT)) {
                    if (value.contains(INCREMENT)) {
                        return String.valueOf(Double.parseDouble(processCellForMultiValue(mat, value.replace(INCREMENT, ""))) + 1);
                    }
                } else {
                    return String.valueOf(Double.parseDouble(processCellForMultiValue(mat, value.replace(DECREMENT, ""))) - 1);
                }
            } else {
                String[] valuesOfCell = value.split("");
                if (set.contains(value)) {
                    throw new CyclicDependencyException(100, String.format("A cyclic dependency has found for value %s", value));
                }
                set.add(value);
                String val = mat[valuesOfCell[0].charAt(0) - 'A'][Integer.valueOf(value.replace(valuesOfCell[0], "")) - 1];
                if (Character.isDigit(val.charAt(0))) {
                    return val;
                } else {
                    return processCellForSingleValue(mat, val, set);
                }
            }
        }
        return expression;
    }

    /**
     * This method computes expression
     * @param str
     * @return
     */
    private String evaluateExpression(String str) {
        String[] arr = str.split(" ");
        if (arr.length < 3)
            return str;
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            char curChar = arr[i].charAt(0);
            if (Character.isDigit(curChar) || (curChar == '-' && arr[i].length() > 1 && Character.isDigit(arr[i].charAt(1)))) {
                stack.push(Double.parseDouble(arr[i]));
            } else {
                double y = stack.pop();
                double x = stack.pop();
                stack.push(applyOperatorOnValues(x, y, curChar));
            }
        }
        return stack.pop().toString();
    }

    /**
     * This method applies given operator on values passed.
     * @param val1
     * @param val2
     * @param operator
     * @return
     */
    private double applyOperatorOnValues(double val1, double val2, char operator) {
        switch (operator) {
            case '+':
                return val1 + val2;
            case '-':
                return val1 - val2;
            case '/':
                return val1 / val2;
            case '*':
                return val1 * val2;
            default:
                return Integer.MIN_VALUE;
        }
    }

    /**
     * Method prints the output as stdout
     * @param arr
     */
    protected void print(String[][] arr) {
        System.out.println(arr[0].length + " " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.println(arr[i][j]);
            }
        }
    }

    /**
     * Cyclic dependency exception class which returns code and a message in Exception of particular type
     * in case exceptions needs to be thrown for Cyclic dependency
     */
    public class CyclicDependencyException extends Exception {
        private int code;
        public CyclicDependencyException(int code, String msg) {
            super(msg);
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }
}

