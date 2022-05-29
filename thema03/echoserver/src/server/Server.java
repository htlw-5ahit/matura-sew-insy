package server;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(2007)) { // open server socket
            System.out.println("Starting Server (" + serverSocket.getLocalSocketAddress() + ")");

            while (true)
                executorService.execute(new ClientHandler(serverSocket.accept())); // accept and open thread
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
