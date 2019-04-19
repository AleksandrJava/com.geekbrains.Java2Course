package Lesson7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServer {
    public boolean k;

    Vector<ClientHandler> clients;

    public MainServer() throws SQLException {

        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            AuthService.connect();

            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился!");
                // создаем нового клиента
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }

    }
    // метод для рассылки сообщения всем клиентам
    public void broadCastMsg(String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }
    //метод для отправки личного сообщения конкретному клиенту
    public void OneMsg(String msg, String client, ClientHandler r) {
        String name = r.getName();
        for (ClientHandler a: clients) {
            if (a.getName().equals(client)) {
                a.sendMsg(name + " пишет Вам: " + msg);
            }
        }
    }

    // подписываем клиента и добавляем его в список клиентов
    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    // проверка, есть ли пользователь в сети
    public boolean check(String nickname){
        for (ClientHandler a: clients) {
            if (a.getName().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    // отписываем клиента и удаляем его из списка клиентов
    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }



}
