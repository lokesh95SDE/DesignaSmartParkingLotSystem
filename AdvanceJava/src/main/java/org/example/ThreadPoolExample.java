package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int i=0; i<1000;i++){
            int j = i;
            es.submit(()->{
                System.out.println("Thread Handling  ____: "+j+ " ____"+Thread.currentThread().getName());
            });
        }
    }

}
