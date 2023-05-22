package frc.csplib.math.functions;

import frc.csplib.CSPTimer;
import frc.csplib.math.Demand;

public class RateLimiter implements Function <Demand, Number> {

    private final CSPTimer timer = new CSPTimer();
    private double last, output;
    private final double rate;

    public RateLimiter(Number rate, Number initial) {
        this.rate = rate.doubleValue();
        last = initial.doubleValue();
    }

    @Override
    public void update(Demand value) {
        if (Math.abs(last - value.set) / timer.getDT() > rate) {
            output = last + (value.set - last) * timer.getDT();
        }

        last = value.current;
    }

    @Override
    public Number get() {
        return output;
    }
}
