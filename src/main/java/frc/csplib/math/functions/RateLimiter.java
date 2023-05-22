package frc.csplib.math.functions;

import frc.csplib.CSPTimer;

public class RateLimiter implements Function <RateLimiter.SetData, Number> {

    public class SetData {

        public final double set, current;

        public SetData(Number set, Number current) {
            this.set = set.doubleValue();
            this.current = current.doubleValue();
        }
    }

    private final CSPTimer timer = new CSPTimer();
    private double last, output;
    private final double rate;

    public RateLimiter(Number rate, Number initial) {
        this.rate = rate.doubleValue();
        last = initial.doubleValue();
    }

    @Override
    public void update(SetData value) {
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
