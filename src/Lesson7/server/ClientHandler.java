package Lesson7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
// класс для работы к клиентами
public class ClientHandler {

    private MainServer server;

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;
    public String getName() {
        return nick;
    }

    public ClientHandler(MainServer server, Socket socket) {

        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // цикл для авторизации
                        while (true) {
                            String str = in.readUTF();
                            // если приходит сообщение начинающееся с /auth значит пользователь хочет авторизоваться
                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                // запрашиваем ник в БД
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                // если ответ не равен null отправляем ответ клиенту о том, что авторизация прошла успешно


                                if(newNick != null && server.check(newNick) == false){
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(ClientHandler.this);
                                    break;
                                } else if(newNick != null && (server.check(newNick) == true)){
                                    sendMsg("Такой пользователь уже в сети!");
                                } else sendMsg("Неверный логин/пароль!");

                            }

                        }
                        // цикл для работы
                        while (true) {
                            String str = in.readUTF();
                            if(str.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                break;
                            }
                            if (str.startsWith("/w")) {
                                String[] tokens = str.split(" ");

                                String newNick = AuthService.getNick(tokens[1]);
                                String msg = "";
                                for(int i = 2; i < tokens.length; i++){
                                    msg = (msg + tokens[i] + " ");
                                }

                                //String msg = tokens[2];
                                if (newNick != null && server.check(newNick) == true) {
                                    server.OneMsg(msg, newNick, ClientHandler.this);

                                } else if(newNick == null){
                                    sendMsg("Такого пользователя нет в БД");
                                } else if(server.check(newNick) == false){
                                    sendMsg("Этот пользователь не в сети сейчас");
                                }
                            } else {
                                server.broadCastMsg(nick + ": " + str);
                            }
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
    // метод для оправки сообщения 1 клиенту
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}