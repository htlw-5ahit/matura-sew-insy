package common;

public class Utils {

    public static MathematicalOperation getOperationByString(String s) {
        MathematicalOperation operation = null;
        switch (s) {
            case "+": operation = MathematicalOperation.ADDITION; break;
            case "-": operation = MathematicalOperation.SUBTRACTION; break;
            case "*": operation = MathematicalOperation.MULTIPLICATION; break;
            case "/": operation = MathematicalOperation.DIVISION; break;
        }
        return operation;
    }

}
