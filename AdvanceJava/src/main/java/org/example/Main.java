package org.example;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        *******EXTENDED THREAD EXAMPLE********
//         ExtendedThreadExample extendedThreadExample = new ExtendedThreadExample();
//         extendedThreadExample.setDaemon(true);
//         extendedThreadExample.start();

//        *******RUNNABLE THREAD EXAMPLE********

        RunableThreadExample runableThreadExample = new RunableThreadExample();
        Thread thread = new Thread(runableThreadExample);
        thread.setDaemon(false);
        thread.setPriority(10);
        thread.start();

                for(int i=0;i<1000;i++){
                    System.out.println("Main Thread Handling ____: "+i+ " ____"+ Thread.currentThread().getName());
                }

            }
}