package org.example;

public class WaitedTimeThreadExample {
    public static void main(String[] arg) throws InterruptedException {
        Thread thread1 = new Thread(()->{
            System.out.println("Before sleep of thread1 "+Thread.currentThread().getState());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Before start of main thread1 "+Thread.currentThread().getState());
        System.out.println("Before start of thread1 "+thread1.getState());
        thread1.start();
        Thread.sleep(50);
        System.out.println("After start of thread1 "+thread1.getState());
        System.out.println("After start of main thread1 "+Thread.currentThread().getState());

    }
}
