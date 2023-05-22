package frc.csplib.motors;

import frc.csplib.math.State;

public interface FeedbackMotorController extends EncodedMotorController {

    public Number getTempC();

    public Number getCurrent();

    public Number getResistance();

    public void enableWarningTemp(Number temp);

    public void enableShutoffTemp(Number temp);

    public void disableWarningTemp();

    public void disableShutoffTemp();
}
