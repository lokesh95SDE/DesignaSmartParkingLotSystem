package org.example;

public class ExtendedThreadExample extends Thread{

@Override
    public void run(){
    for(int i=0;i<10000;i++){
        System.out.println("Thread Handling  ____: "+i+ " ____"+Thread.currentThread().getName());
    }
}
}
