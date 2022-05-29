package server;

import common.CalculatorTask;
import common.MathematicalOperation;

import java.io.*;
import java.net.Socket;

public class SocketThread extends Thread {

    private final Socket socket;
    private boolean running;

    public SocketThread(Socket socket) {
        this.socket = socket;
        this.running = true;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println(this.getId() + "...Connection from " + socket.getInetAddress() + " (" + socket.getPort() + ")");

            while (running) {
                CalculatorTask task = (CalculatorTask) ois.readObject();

                if (task.getOperation() == MathematicalOperation.QUIT) { // check for quit event
                    running = false;
                } else { // run quit event
                    new Calculator(task).calculate(); // calculate task
                }

                System.out.println(task.getOperation());

                oos.writeObject(task);
                oos.flush(); // send task back
            }

            socket.close();
            System.out.println(this.getId() + "...Closed successfully Connection");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
