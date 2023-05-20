package frc.csplib.math.functions;

import frc.csplib.CSPTimer;

/** A class which solves for the rate of change in a value. */
public class Derivative implements Function<Number, Number> {
    private double lastVal, rate;
    private CSPTimer timer;

    /**
     * Constructs a new {@link Derivative} object.
     * @param x The initial value.
     */
    public Derivative(double x) {
        this.lastVal = x;
        this.timer = new CSPTimer();
    }

    /**
     * Constructs a new {@link Derivative} object where the initial value is defaulted to <b>0.0</b>.
     */
    public Derivative() {
        this(0.0);
    }

    /**
     * Call this in a method that loops (such as a periodic method).
     * @param x The value who's rate of change is being solved for.
     */
    @Override
    public void update(Number x) {
        double dx = x.doubleValue() - lastVal;
        lastVal = x.doubleValue();
        rate = dx / timer.getDT();
    }

    /**
     * @return The current derivative of the tracked value.
     */
    public Number get() {
        return rate;
    }
}
