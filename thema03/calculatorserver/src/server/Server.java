package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2000)) {
            System.out.println("Started Server on Port: "  + serverSocket.getLocalPort() );
            System.out.println("Waiting for Clients...\n");

            while (true) {
                // wait and accept inbounding socket and start in own thread
                new SocketThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
