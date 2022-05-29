package javafx.model;

import common.CalculatorTask;
import javafx.concurrent.Task;

public class ClientTask extends Task<CalculatorTask> {

    private SocketHandler handler;
    private CalculatorTask task;

    public ClientTask(SocketHandler handler, CalculatorTask task) {
        this.handler = handler;
        this.task = task;
    }

    @Override
    protected CalculatorTask call() throws Exception {
        CalculatorTask receivedTask = null;

        // send task
        handler.getObjectOutputStream().writeObject(task);
        handler.getObjectOutputStream().flush();

        // receive task
        receivedTask = (CalculatorTask) handler.getObjectInputStream().readObject();

        return receivedTask;
    }

}