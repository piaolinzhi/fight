package com.fighthard.scheduletask;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleInterfaceImpl {
    
    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    /**
     * 以固定周期频率执行任务
     */
    public static void executeFixedRate() {
        executor.scheduleAtFixedRate(
            new EchoServer(System.currentTimeMillis()), 0, 100,
            TimeUnit.MILLISECONDS);
    }

    /**
     * 以固定延迟时间进行执行
     * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
     */
    public static void executeFixedDelay() {
        executor.scheduleWithFixedDelay(
            new EchoServer(System.currentTimeMillis()), 0, 100,
            TimeUnit.MILLISECONDS);
    }

    public static void executeSchedule() {
        for(int i = 0; i < 10; i++) {
            executor.schedule(new EchoServer(System.currentTimeMillis()), 200,
                TimeUnit.MILLISECONDS);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("main:"+System.currentTimeMillis());
        executeSchedule();
        System.out.println("main"+System.currentTimeMillis());

    }

}
