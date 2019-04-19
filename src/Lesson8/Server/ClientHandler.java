package Lesson8.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class ClientHandler {

    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    Server server;
    private boolean isAuthorized = false;
    boolean wosLimit = false;


    public String getNick() {
        return nick;
    }

    String nick;


    public ClientHandler(Server server, Socket socket){

        try {

            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clientAuthorization();
                        if(isAuthorized) {
                            workWithClient();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                            System.out.println("Соединение с клиентом потеряно");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientAuthorization() throws IOException {

        try {
            socket.setSoTimeout(10000);

            while (true) {
                String str = in.readUTF();

                if(str.startsWith("/timeLimit")){
                    System.out.println("Время вышло");
                    break;
                }

                if (str.startsWith("/auth")) {
                    String[] tokens = str.split(" ");
                    String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                    if (newNick != null) {
                        if (!server.isNickBusy(newNick)) {
                            sendMsg("/authok");
                            nick = newNick;
                            server.subscribe(ClientHandler.this);
                            isAuthorized = true;
                            break;
                        } else {
                            sendMsg("Учетная запись уже используется!");
                        }
                    } else {
                        sendMsg("Неверный логин/пароль!");
                    }
                }
            }
        } catch(SocketTimeoutException s){
            try{
                sendMsg("10 секунд прошло, соединение закрыто");
                Thread.sleep(3000);
                sendMsg("/timeLimit");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void workWithClient () throws IOException {
        while (true) {
            socket.setSoTimeout(0);
            String str = in.readUTF();
            if(str.startsWith("/")) {
                if (str.equals("/end")) {
                    out.writeUTF("/serverClosed");
                }
                if (str.startsWith("/w ")) {
                    String[] tokens = str.split(" ", 3);
                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                }
                if (str.startsWith("/blacklist ")) {
                    String[] tokens = str.split(" ");
                    if (userHave(tokens[1]) == true) {

                        if (tokens[1].equals(nick)) {
                            sendMsg("Нельзя добавить себя в свой черный список!");
                        } else {

                            if (AuthService.checkInBlackList(tokens[1], nick) == false) {
                                AuthService.addIntoBlackList(tokens[1], nick);
                                sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            } else sendMsg("Пользователь " + tokens[1] + " уже в вашем черном списке!");
                        }
                    }
                } else {
                    server.broadcastMsg(ClientHandler.this, nick + ": " + str);
                }
            }
        }
    }

    public boolean userHave(String user){
        if(AuthService.checkHaveThisUser(user)){
            return true;
        } else {
            sendMsg("Такого пользователя не существует в базе");
            return false;
        }
    }
}
