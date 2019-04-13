package Lesson6;

//Написать консольный вариант клиент-серверного приложения, в котором пользователь может писать сообщения,
//как на клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать «Привет», нажать Enter,
//то сообщение должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне,
//то сообщение передается клиенту и печатается у него в консоли. Есть одна особенность, которую нужно учитывать:
//клиент или сервер может написать несколько сообщений подряд. Такую ситуацию необходимо корректно обработать.

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент присоединился!");
                new Thread(new ClientHandler(socket)).start();
                new ClientHandler(socket).startMessaging();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                server.close();
                System.out.println("Сервер остановлен");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private Scanner in, consoles;


    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (in.hasNext()) {
                String w = in.nextLine();
                System.out.println("Клиент: " + w);
                out.flush();
            }
        }
    }
    public void startMessaging() {
        String message;
        consoles = new Scanner(System.in);
        System.out.println("Введите сообщение:");

        while (true) {
            message = consoles.next();
            out.println(message);
            out.flush();
        }
    }
}
