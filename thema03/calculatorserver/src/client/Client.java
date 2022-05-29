package client;

import common.CalculatorTask;
import common.MathematicalOperation;
import common.Utils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Rechnung eingeben (Format a + b):");

            try (Socket socket = new Socket("127.0.0.1", 2000); // open sockets + streams
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
                boolean running = true;

                while (running) {
                    String consoleInput = scanner.nextLine(); // read console input

                    if (consoleInput.equalsIgnoreCase("QUIT")) { // check if quit cmd is entered
                        // send quit command
                        oos.writeObject(new CalculatorTask(0,0, MathematicalOperation.QUIT));
                        oos.flush();

                        // wait for returning quit command
                        CalculatorTask quitTask = (CalculatorTask) ois.readObject();

                        System.out.println(quitTask.getOperation());
                        running = false;
                    } else if (consoleInput.matches("\\d+ [+\\-*/] \\d+")) { // check for format mistakes
                        String[] input = consoleInput.split(" ");

                        CalculatorTask task = new CalculatorTask( // createt ask
                                Double.parseDouble(input[0]), Double.parseDouble(input[2]),
                                Utils.getOperationByString(input[1])
                        );

                        // send task to server
                        oos.writeObject(task);
                        oos.flush();

                        // receive calculated task from server
                        CalculatorTask receivedTask = (CalculatorTask) ois.readObject();

                        System.out.println("Ergebnis: " + receivedTask.getResult()); // print result
                    } else
                        System.out.println("Invalide format!");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


    }
}
