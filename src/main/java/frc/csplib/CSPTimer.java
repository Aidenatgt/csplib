package frc.csplib;

import edu.wpi.first.wpilibj.Timer;

public class CSPTimer extends Timer {
    
    private double lastTime = System.currentTimeMillis() / 1000.0;
    public double getDT() {
        double time = System.currentTimeMillis() / 1000.0;
        double dt = time - lastTime;
        lastTime = time;
        return dt;
    }
}
