package frc.csplib.motors.encoders;

import frc.csplib.math.State;

public interface Encoder {

    public State getState();

    public void setState(State state);

    public void setInverted(boolean ccp);
}
