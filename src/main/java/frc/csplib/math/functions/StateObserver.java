// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.csplib.math.functions;

import frc.csplib.math.State;

/** A class which observes a single value and finds the {@link State} object which that value represents. */
public class StateObserver implements Function<Number, State>{
    private Derivative firstDerivative, secondDerivative;
    private State state;

    /**
     * Constructs a new {@link StateObserver} object.
     * @param state The initial state of the value being tracked.
     */
    public StateObserver(State state) {
        setState(state);
    }

    /**
     * Constructs a new {@link StateObserver} object where the intial state is absolutely 0.
     */
    public StateObserver() {
        setState();
    }

    /**
     * Resets the observed state to a specified state.
     * @param state The {@link State} object to be represent the new intended state.
     */
    public void setState(State state) {
        this.firstDerivative = new Derivative(state.S);
        this.secondDerivative = new Derivative(state.firstDerivative);
        this.state = state;
    }

    /**
     * Resets the observed state to have a specified value and derivatives of 0.
     * @param value The new value of the state.
     */
    public void setState(double value) {
        setState(new State(value));
    }

    /**
     * Resets the observed state to absolutely 0.
     */
    public void setState() {
        setState(new State());
    }

    /**
     * Supply a the next value being tracked.
     * @param value The tracked value.
     */
    public void update(Number value) {
        firstDerivative.update(value);
        secondDerivative.update(firstDerivative.get());
        state = new State(value.doubleValue(), firstDerivative.get().doubleValue(), secondDerivative.get().doubleValue());
    }

    /**
     * @return The current state of the tracked value.
     */
    public State get() {
        return state;
    }
}
