package Practice3;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8085;
    public static LinkedList<ServerImpl> serverList = new LinkedList<>(); // список всех подключенных клиентов
    public static MessageBuf messageBuf; // буфер сообщений

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT); // передаем порт
        messageBuf = new MessageBuf();
        System.out.println("Server Started"); // сообщение о запуске сервера
        try {
            Thread one = new Thread(()->{ // объявляем поток
                while (true){
                    if(!messageBuf.isBufEmpty()){ // если буфер сообщений заполнен, то
                        for (ServerImpl vr : Server.serverList) { // начинаем итерироваться по списку серверов
                            try {
                                for (ServerImpl _server : serverList ){
                                    messageBuf.printBuf(new BufferedWriter(new OutputStreamWriter(_server.getSocket().getOutputStream()))); // выводим сообщения всем клиентам
                                }
                            } catch (IOException e) { //в случае чего выкидываем ошибку
                                throw new RuntimeException(e);
                            }
                            finally {
                                messageBuf.clearBuf(); // чистим буфер (выполняется в любом случае)
                            }
                        }
                    }
                    try {
                        Thread.sleep(20000); // на сколько спит поток (раз в 20 сек отсылает весь буфер сообщений всем клиентам)
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            one.start(); // запускаем поток
            while (true) {
                Socket socket = server.accept(); // запускаем сервер в режим принятия соединения
                try {
                    serverList.add(new ServerImpl(socket)); //добавляем клиента в список
                } catch (IOException e) {
                    socket.close(); // закрываем соединение
                }
            }
        } finally {
            server.close();
        }
    }
}
class ServerImpl extends Thread { // наследуется от класса потока, может работать асинхронно
    private Socket socket;
    private BufferedReader in; // входной поток
    private BufferedWriter out; //выходной поток
    public ServerImpl(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // инициализируем поток
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.messageBuf.printBuf(out); // выводим список сообщений в выходной поток
        start(); // запускаем метод run
    }
    @Override // метод по умолчанию потока
    public void run() {
        String word;
        try {
            while (true) {
                word = in.readLine(); // считывается слово
                Server.messageBuf.addBufEl(word); // добавляется в буфер
            }
        } catch (IOException e) {
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
// буфер сообщений
class MessageBuf {
    private LinkedList<String> serverBuf = new LinkedList<>(); // список сообщений
    public void addBufEl(String el) {
        serverBuf.add(el);
    } // добавление элемента в буфер

    public void printBuf(BufferedWriter writer) {
        if(serverBuf.size() > 0) {
            try {
                for (String vr : serverBuf) { // итерируемся по буферу
                    writer.write(vr + "\n");
                }
                writer.flush(); // очищаем буфер
            } catch (IOException ignored) {}
        }
    }

    public void clearBuf(){
        serverBuf.clear();
    }

    public boolean isBufEmpty(){
        return serverBuf.isEmpty();
    }
}