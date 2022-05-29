package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String input;
        boolean running = true;
        try (Socket socket = new Socket("127.0.0.1", 2007);
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
             DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.err.println("Successfully connected with Echo-Server! Write your message and press enter and send.");
            System.err.println("Insert 'QUIT' to close connection and exit the program.");
            System.err.println("Server Information: " + socket.getRemoteSocketAddress() + " - " + socket.getInetAddress());

            while (running) {
                input = scanner.nextLine();

                if (input.equals("QUIT"))
                    running = false;

                dos.writeUTF(input);
                dos.flush();
                System.out.println(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
