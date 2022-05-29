package common;

import java.io.Serializable;

public class CalculatorTask implements Serializable {
    
    private double number1;
    private double number2;
    private MathematicalOperation operation;
    private double result;

    public CalculatorTask(double number1, double number2, MathematicalOperation operation) {
        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
    }

    public double getNumber1() {
        return number1;
    }

    public double getNumber2() {
        return number2;
    }

    public MathematicalOperation getOperation() {
        return operation;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
