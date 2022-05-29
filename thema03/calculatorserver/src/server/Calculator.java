package server;

import common.CalculatorTask;

public class Calculator {

    private CalculatorTask task;

    public Calculator(CalculatorTask task) {
        this.task = task;
    }

    public void calculate() {
        switch (task.getOperation()) {
            case ADDITION:
                task.setResult(task.getNumber1() + task.getNumber2());
                break;
            case SUBTRACTION:
                task.setResult(task.getNumber1() - task.getNumber2());
                break;
            case DIVISION:
                task.setResult(task.getNumber1() / task.getNumber2());
                break;
            case MULTIPLICATION:
                task.setResult(task.getNumber1() * task.getNumber2());
                break;
        }
    }
}
