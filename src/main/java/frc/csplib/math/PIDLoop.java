package frc.csplib.math;

import edu.wpi.first.math.MathUtil;
import frc.csplib.math.functions.Derivative;
import frc.csplib.math.functions.LinearIntegral;

/** Executes the PID control algorithm. */
public class PIDLoop {
    
    private final double kP, kI, kD;

    private LinearIntegral integral = new LinearIntegral();
    private Derivative derivative = new Derivative();

    private double error;

    private boolean outputRangeEnabled = false;
    private final double[] outputRange = new double[] {-1.0, 1.0};

    private boolean continuousInputEnabled = false;
    private final double[] inputRange = new double[] {-1.0, 1.0};

    private double lastOutput = 0.0;

    /**
     * Creates a new {@link PIDLoop} object.
     * @param kP The P term.
     * @param kI The I term.
     * @param kD The D term.
     */
    public PIDLoop(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Define operable outputs of the system and only integrate when the PID output is within this range.
     * @param minOutput The minimum system output.
     * @param maxOutput The maximum system output.
     */
    public void enableOutputRange(double minOutput, double maxOutput) {
        outputRangeEnabled = true;
        outputRange[0] = Math.min(minOutput, maxOutput);
        outputRange[1] = Math.max(minOutput, maxOutput);
    }

    /**
     * Stop using the output range feature.
     */
    public void disableOutputRange() {
        outputRangeEnabled = false;
    }

    /**
     * Enables continuous modular input where the minimum and maximum inputs are considered to be the same value.
     * @param minInput The minimum system input.
     * @param maxInput The maximum system input.
     */
    public void enableContinuousInput(double minInput, double maxInput) {
        continuousInputEnabled = true;
        inputRange[0] = Math.min(minInput, maxInput);
        inputRange[1] = Math.max(minInput, maxInput);
    }

    /**
     * Stops using the continuous input feature.
     */
    public void disableContinousInput() {
        continuousInputEnabled = false;
    }

    /**
     * Calculate a system response based on input.
     * @param state The current state value of the system.
     * @param setpoint The desired state value of the system.
     * @return The calculated optimal state response.
     */
    public double calculate(double state, double setpoint) {
        derivative.update(error);

        if (continuousInputEnabled) {
            double errorBound = (inputRange[0] - inputRange[1]) / 2.0;
            error = MathUtil.inputModulus(setpoint - state, -errorBound, errorBound);
        } else error = setpoint - state;

        if (outputRangeEnabled) {
            if (lastOutput >= outputRange[0] && lastOutput <= outputRange[1])
                integral.update(error);
        } else integral.update(setpoint);

        double output = kP * error + kI * integral.get().doubleValue() + kD * derivative.get().doubleValue();

        if (outputRangeEnabled)
            output = MathUtil.clamp(output, outputRange[0], outputRange[1]);
            
        lastOutput = output;
        return output;
    }

    /**
     * @return The value of the integral of the error.
    */
    public double getIntegral() {
        return integral.get().doubleValue();
    }

    /**
     * @return The value of the derivative of the error.
     */
    public double getDerivative() {
        return derivative.get().doubleValue();
    }
}
