package Lesson8.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Server {

    private Vector<ClientHandler> clients;

    public Server() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
//            AuthService.addUser("login1", "pass1", "nick1");
//            AuthService.addUser("login2", "pass2", "nick2");
//            AuthService.addUser("login3", "pass3", "nick3");
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this,socket);
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

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientList();
    }


    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientList();
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o: clients) {
            if(AuthService.checkInBlackList(o.getNick(), from.getNick()) == false && AuthService.checkInBlackList(from.getNick(),o.getNick()) == false) {
                o.sendMsg(checkTime() + "  "  + msg);
            }
        }
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o: clients) {
            if(o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public String checkTime(){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy (HH:mm) ");

        return formatForDateNow.format(dateNow);
    }


    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        if(AuthService.checkInBlackList(nickTo, from.getNick()) == false && AuthService.checkInBlackList(from.getNick(),nickTo) == false) {
            for (ClientHandler o : clients) {
                if (o.getNick().equals(nickTo)) {
                    o.sendMsg(checkTime() + "  " + "from " + from.getNick() + ": " + msg);
                    from.sendMsg(checkTime() + "  " + "to " + nickTo + ": " + msg);
                    return;
                }
            }
            from.sendMsg("Клиент с ником " + nickTo + " не найден!");
        } else {
            from.sendMsg("Отправка личного сообщения пользователю " + nickTo + " недоступна! Вы добавили его в свой черный список, либо он заблокировал вас");
        }

    }


    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");

        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }

        String out = sb.toString();
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }
}
