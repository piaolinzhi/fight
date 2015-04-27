package com.fighthard.scheduletask;

class EchoServer implements Runnable {

    public long startTime;

    public EchoServer(long s) {
        this.startTime = s;
    }

    public void run() {
        Long currentTime = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("Echo Server,PID:"+Thread.currentThread().getId()+":This is a echo server.Initial time is :"
            + this.startTime);
        System.out.println("Echo Server,PID:"+Thread.currentThread().getId()+":This is a echo server.Begin time is :"
            + currentTime);
        System.out.println("Echo Server,PID:"+Thread.currentThread().getId()+":Delay time is :" + (currentTime - startTime));
        try {
            Thread.sleep(50);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("This is a echo server. The current time is "
            + System.currentTimeMillis() + ".");
    }
}
