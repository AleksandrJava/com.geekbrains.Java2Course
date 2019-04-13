package Lesson6;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    Socket socket;
    Scanner in, console;
    PrintWriter out;

    public static void main(String[] args) {
        Client client = new Client();
    }

    public Client() {

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8189);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.hasNext()) {
                            String w = in.nextLine();
                            System.out.println("Сервер: " + w);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        startMessaging();
    }

    public void startMessaging() {
        String message;
        console = new Scanner(System.in);
        System.out.println("Введите сообщение:");

        while (true) {
            message = console.next();
            out.println(message);
            out.flush();

        }
    }
}