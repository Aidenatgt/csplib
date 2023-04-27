package frc.csplib.math;

import edu.wpi.first.math.MathUtil;

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

    public PIDLoop(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public void enableOutputRange(double minOutput, double maxOutput) {
        outputRangeEnabled = true;
        outputRange[0] = Math.min(minOutput, maxOutput);
        outputRange[1] = Math.max(minOutput, maxOutput);
    }

    public void disableOutputRange() {
        outputRangeEnabled = false;
    }

    public void enableContinuousInput(double minInput, double maxInput) {
        continuousInputEnabled = true;
        inputRange[0] = Math.min(minInput, maxInput);
        inputRange[1] = Math.max(minInput, maxInput);
    }

    public void disableContinousInput() {
        continuousInputEnabled = false;
    }

    public double calculate(double state, double setpoint) {
        if (continuousInputEnabled) {
            double errorBound = (inputRange[0] - inputRange[1]) / 2.0;
            error = MathUtil.inputModulus(setpoint - state, -errorBound, errorBound);
        } else error = setpoint - state;

        if (outputRangeEnabled) {
            if (lastOutput >= outputRange[0] && lastOutput <= outputRange[1])
                integral.update(error);
        } else integral.update(setpoint);

        double output = kP * error + kI * integral.get() + kD * derivative.getRate(error);

        if (outputRangeEnabled)
            output = MathUtil.clamp(output, outputRange[0], outputRange[1]);
            
        lastOutput = output;
        return output;
    }

    public double getIntegral() {
        return integral.get();
    }
}
