package org.example;

public class WaitThreadExample {
    public static void main(String[] arg) throws InterruptedException {
        Thread longestThread = new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread sortestThread = new Thread(()->{
            try {
                Thread.sleep(2000);
                longestThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread monitoringThread = new Thread(()->{
            while (longestThread.isAlive()|| sortestThread.isAlive()){
                System.out.println("Monitoring thread is running, longestThread state: "+longestThread.getState()+", sortestThread state: "+sortestThread.getState());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        System.out.println("Before start of longestThread "+longestThread.getState());
        System.out.println("Before start of sortestThread "+sortestThread.getState());
        System.out.println("Before start,state of main thread "+Thread.currentThread().getState());
        longestThread.start();
        sortestThread.start();
        monitoringThread.start();
        System.out.println("After start of longestThread "+longestThread.getState());
        System.out.println("After start of sortestThread "+sortestThread.getState());;
        System.out.println("After start of main thread "+Thread.currentThread().getState());

    }
}
