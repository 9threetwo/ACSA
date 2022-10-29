package Practice3;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {

    public static String ipAddr = "localhost";
    public static int port = 8085;

    public static void main(String[] args) {
        new ClientImpl(ipAddr, port);
    }
}

class ClientImpl {

    private Socket socket;
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток чтения в сокет
    private BufferedReader inputUser; // поток чтения с консоли
    private String addr; // ip адрес клиента
    private int port; // порт соединения
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1; //форматировщик даты

    public ClientImpl(String addr, int port) {
        this.addr = addr;
        this.port = port;
        try {
            this.socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in)); // поток чтения из сокета
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // поток записи в сокет
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // поток чтения с консоли
            new ReadMsg().start(); // нить, читающая сообщения из сокета в бесконечном цикле
            new WriteMsg().start(); /// нить, пишущая сообщения в сокет приходящие с консоли в бесконечном цикле
        } catch (IOException e) {

        }
    }

    // нить чтения сообщений с сервера
    private class ReadMsg extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = in.readLine(); // ждем сообщения с сервера
                    System.out.println(str); // пишем сообщение с сервера на консоль
                }
            } catch (IOException e) { //в случае ошибки выбросится исключение

            }
        }
    }

    // нить отправляющая сообщения приходящие с консоли на сервер
    public class WriteMsg extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    time = new Date(); // текущая дата
                    dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                    dtime = dt1.format(time);
                    userWord = inputUser.readLine(); // сообщения с консоли
                    out.write("(" + dtime + ") " + ": " + userWord + "\n"); // отправляем на сервер
                    out.flush(); // чистим
                } catch (IOException e) { //в случае ошибки выбросится исключение
                }

            }
        }
    }
}