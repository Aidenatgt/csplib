package frc.csplib.motors;

import frc.csplib.math.State;

public interface EncodedMotorController extends MotorController {

    public void setTorqueOutput(Number output);

    public void setPosition(Number position);

    public Number getTorqueOutput();

    public State getState();
}
