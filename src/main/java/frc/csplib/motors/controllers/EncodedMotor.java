package frc.csplib.motors.controllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import frc.csplib.math.State;
import frc.csplib.motors.*;
import frc.csplib.motors.encoders.Encoder;

public class EncodedMotor implements EncodedMotorController {

    private MotorController motor;
    private Encoder encoder;
    private MotorConstants constants;

    private double output;
    private OutputMode mode;

    private final Notifier monitor = new Notifier(() -> {
        switch (mode) {
            case PERCENT:
                motor.set(output);
            case VOLTS:
                motor.setVoltage(output);
            case TORQUE:
                motor.setVoltage(constants.nomVolts * (output/constants.torque + (60.0*encoder.getState().firstDerivative)/constants.rpm));
        }
    });

    public EncodedMotor(MotorController motor, Encoder encoder, MotorConstants constants) {
        this.motor = motor;
        this.encoder = encoder;
        this.constants = constants;
    }

    @Override
    public void setTorqueOutput(Number output) {
        mode = OutputMode.TORQUE;
        this.output = output.doubleValue();
    }

    @Override
    public void setPosition(Number position) {
        State current = encoder.getState();
        encoder.setState(new State(position.doubleValue(), current.firstDerivative, current.secondDerivative));
    }

    @Override
    public Number getTorqueOutput() {
        return constants.torque * (getVoltage().doubleValue() / constants.nomVolts - (60.0 * encoder.getState().firstDerivative)/constants.rpm);
    }

    @Override
    public State getState() {
        return encoder.getState();
    }

    @Override
    public void setInverted(boolean ccp) {
        motor.setInverted(ccp);
    }

    @Override
    public void init() {
        setPosition(0.0);

        monitor.stop();
        monitor.startPeriodic(0.05);
    }

    @Override
    public void setRampRate(Number rampRate) {
        motor.setRampRate(rampRate);
    }

    @Override
    public void set(Number percent) {
        mode = OutputMode.PERCENT;
        output = percent.doubleValue();
    }

    @Override
    public void setVoltage(Number volts) {
        mode = OutputMode.VOLTS;
        output = volts.doubleValue();
    }

    @Override
    public int getID() {
        return motor.getID();
    }

    @Override
    public Number getVoltage() {
        return motor.getVoltage();
    }
}
