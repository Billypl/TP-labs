package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            int n;
            Scanner scanner = new Scanner(System.in);
            n = Integer.parseInt(scanner.nextLine());

            Object receivedObject = in.readObject();
            if (receivedObject instanceof String && ((String) receivedObject).equals("ready")) {
                out.writeObject(n);
                out.flush();
            }
            receivedObject = in.readObject();
            if (receivedObject instanceof String && ((String) receivedObject).equals("ready for messages")) {
                for (int i = 0; i < n; i++) {
                    Message message = new Message(i, scanner.nextLine());
                    out.writeObject(message);
                    out.flush();
                }
            }

            receivedObject = in.readObject();
            if (receivedObject instanceof String && ((String) receivedObject).equals("finished")) {
                System.out.println("Server finished processing messages.");
            }

            in.close();
            out.close();
            socket.close();
            scanner.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
