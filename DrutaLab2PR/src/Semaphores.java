package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Semaphores {
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        //crearea si initializarea semafoarelor
        Semaphore sem1 = new Semaphore(0);
        Semaphore sem2 = new Semaphore(0);
        Semaphore sem3 = new Semaphore(0);
        Semaphore sem5 = new Semaphore(0);
        Semaphore sem6 = new Semaphore(0);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Work("5",sem5);//nu depinde de nimeni, il pornim simplu
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Work("6",sem6); //nu depinde de nimeni, il pornim simplu
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //2 depends 5 && 2 depends 6
                    sem5.acquire();
                    sem6.acquire();
                    Work("2",sem2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            //3 depends 2
            public void run() {
                Waitnwork("3",sem2,sem3);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            //1 depends 3
            public void run() {
                Waitnwork("1",sem3,sem1);
            }
        }).start();
    }

    public static void Waitnwork(String id,Semaphore semToAquire,Semaphore semToRelease){
        // gets thread id, semaphore from thread which should be waited, semaphore which will release thsr thread
        try {
            semToAquire.acquire();
            Work(id,semToRelease);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Work(String id, Semaphore semToRelease){ // gets thread id, and semaphore which will be released
        int millis = random.nextInt(10);
        try {
            Thread.sleep(millis); // sleep for time < 2000
            System.out.printf("Thread nr. %s\n",id);
            semToRelease.release(); // semaphore releases next thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}