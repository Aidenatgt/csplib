package frc.csplib.motors;

import frc.csplib.math.State;

public interface FeedbackMotorController extends MotorController {

    public void setTorqueOutput(Number output);

    public void setPosition(Number position);

    public void enableWarningTemp(Number temp);

    public void enableShutoffTemp(Number temp);

    public void disableWarningTemp();

    public void disableShutoffTemp();

    public Number getTempC();

    public Number getTorqueOutput();

    public Number getCurrent();

    public State getState();

    public Number getResistance();
}
