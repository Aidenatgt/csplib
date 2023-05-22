package frc.csplib.math;

public class Demand {

    public final double set, current;

    public Demand(Number set, Number current) {
        this.set = set.doubleValue();
        this.current = current.doubleValue();
    }
}
