package javafx.model;

import common.CalculatorTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ClientService extends Service<CalculatorTask> {

    private CalculatorTask task;
    private SocketHandler handler;

    public ClientService(SocketHandler handler) {
        this.handler = handler;
    }

    @Override
    protected Task<CalculatorTask> createTask() {
        if (handler.getObjectOutputStream() == null || handler.getObjectInputStream() == null || task == null) return null;
        return new ClientTask(handler, task);
    }

    public CalculatorTask getTask() {
        return task;
    }

    public void setTask(CalculatorTask task) {
        this.task = task;
    }
}
