package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String input;
        boolean running = true;
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
             DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
            System.out.println("Open connection from: " + socket.getInetAddress() + " " + socket.getLocalPort());

            while (running) {
                input = dis.readUTF();

                if (input.equals("QUIT"))
                    running = false;

                System.out.println(input);
                dos.writeUTF(input);
                dos.flush();
            }
        } catch (EOFException e) {
            System.out.println("Socket wasn't correctly closed by client!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
