package frc.csplib.math;

/** An object to store system state information. */
public class State {
    /** The state value */
    public final double S;
    /** The derivative of the state value */
    public final double firstDerivative;
    /** The second derivative of the state value */
    public final double secondDerivative;

    /**
     * Constructs a new {@link State} object.
     * @param S The state value.
     * @param firstDerivative The derivative of the state value.
     * @param secondDerivative The second derivative of the state value.
     */
    public State(double S, double firstDerivative, double secondDerivative) {
        this.S = S;
        this.firstDerivative = firstDerivative;
        this.secondDerivative = secondDerivative;
    }

    /**
     * Constructs a new {@link State} object where the second derivative is 0.
     * @param S The state value.
     * @param firstDerivative The derivative of the state value.
     */
    public State(double S, double firstDerivative) {
        this(S, firstDerivative, 0.0);
    }

    /**
     * Constructs a new {@link State} object where the first and second derivative values are 0.
     * @param S The state value.
     */
    public State(double S) {
        this(S, 0.0, 0.0);
    }

    /**
     * Constructs a new {@link State} object where all values are 0.
     */
    public State() {
        this(0.0, 0.0, 0.0);
    }
}
