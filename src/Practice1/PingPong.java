package Practice1;

import static java.lang.Thread.sleep;

public class PingPong {

    public static void main(String[] args) {
        PingPong pp = new PingPong();
        pp.start();
    }

    private void start(){
        Thread ping = new Thread(new Word(), "ping");
        ping.start();
        Thread pong = new Thread(new Word(), "pong");
        pong.start();
    }

    public class Word implements Runnable{

        @Override
        public void run() {
            while (true){
                printWord();
            }
        }
    }

    synchronized void printWord() {
        System.out.println(Thread.currentThread().getName());
        try {
            sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notify();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}