package frc.csplib.math;

public class State {
    public final double X, V, A;

    public State(double X, double V, double A) {
        this.X = X;
        this.V = V;
        this.A = A;
    }

    public State() {
        this(0.0, 0.0, 0.0);
    }
}
