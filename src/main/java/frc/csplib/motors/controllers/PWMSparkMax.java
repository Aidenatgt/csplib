package frc.csplib.motors.controllers;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotController;
import frc.csplib.math.functions.RateLimiter;
import frc.csplib.motors.MotorConstants;
import frc.csplib.motors.MotorController;
import frc.csplib.motors.OutputMode;

public class PWMSparkMax extends edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax implements MotorController {

    private boolean ramping = false;
    private RateLimiter limiter;

    private double output = 0.0;

    private final Notifier monitor = new Notifier(() -> {
        if (ramping) {
            super.set(limiter.get().doubleValue());
        }
        super.set(output);
    });

    public PWMSparkMax(int pwmChannel) {
        super(pwmChannel);

        init();
    }

    @Override
    public void setInverted(boolean ccp) {
        super.setInverted(!ccp);
    }

    @Override
    public void init() {

    }

    @Override
    public void setRampRate(Number rampRate) {
        if (rampRate.doubleValue() != 0.0) {
            limiter = new RateLimiter(rampRate, get());
            ramping = true;
        } else {
            ramping = false;
        }
    }

    @Override
    public void set(Number percent) {
        output = percent.doubleValue();
    }

    @Override
    public void setVoltage(Number volts) {
        output = volts.doubleValue() / RobotController.getInputCurrent();
    }

    @Override
    public int getID() {
        return super.getChannel();
    }

    @Override
    public Number getVoltage() {
        return super.get() * RobotController.getInputVoltage();
    }
}
