package frc.csplib;

public class Loop {

    private Runnable action = null;
    private Looper looper;

    private class Looper extends Thread {
        private final CSPTimer timer = new CSPTimer();

        private final double period;
        private boolean running;

        private Looper(double period) {
            this.period = period;
            running = true;
            start();
        }

        public void run() {
            do {
                action.run();
                try {
                    Thread.sleep((long) (period - timer.get()) * 1000);
                } catch(Exception e) {
                    running = false;
                }
                timer.restart();
            } while (running);
        }
    }
    
    public Loop(Runnable action) {
        this.action = action;
    }

    public void startPeriodic(double period) {
        looper = new Looper(period);
    }
}
