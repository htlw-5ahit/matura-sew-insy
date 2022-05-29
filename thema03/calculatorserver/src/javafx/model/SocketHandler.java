package javafx.model;

import common.CalculatorTask;
import common.MathematicalOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ClientService service;

    public SocketHandler(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        this.service = new ClientService(this);
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientService getService() {
        return service;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }

    public ObjectInputStream getObjectInputStream() {
        return ois;
    }

    public void close() {
        service.reset();
        service.setTask(new CalculatorTask(0,0, MathematicalOperation.QUIT));
        service.start();

        service.setOnSucceeded(e -> {
            try {
                oos.close();
                ois.close();
                socket.close();
            } catch (IOException ioException) {
                System.out.println("Couldn't close connection. Connection already closed...");
            }
        });
    }
}
