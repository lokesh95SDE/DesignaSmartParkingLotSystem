package org.example;

public class BlockedThreadExample {

    final Object lock = new Object();

    public void CommonResource() {
        synchronized (lock){
            System.out.println("Common Resource Accessed by Thread : " + Thread.currentThread().getName()+"_________"+Thread.currentThread().getState());

            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//        for (int i = 0; i < 10000; i++) {
//            System.out.println("Thread Handling : " + Thread.currentThread().getName());
//        }
        }

    }
}

class BlockedThreadMain{
    public static void main(String[] arg) throws InterruptedException {

        BlockedThreadExample bt = new BlockedThreadExample();

        Thread t1 =  new Thread(()->{
            bt.CommonResource();
        });
        Thread t2 =  new Thread(()->{
            bt.CommonResource();
        });
    System.out.println("Before start of t1 "+t1.getState());
    System.out.println("Before start of t2 "+t2.getState());
        t1.start();
        t2.start();
    System.out.println("After start of t1 "+t1.getState());
    System.out.println("After start of t2 "+t2.getState());
        Thread.sleep(50);
    System.out.println("After start of t1 & 50ms wait "+t1.getState());
    System.out.println("After start of t2 & 50ms wait "+t2.getState());


        }
    }

