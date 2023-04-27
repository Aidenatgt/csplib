package frc.csplib.motors;

import java.util.Collection;

public class CSP_MotorGroup<T extends CSP_Motor> implements CSP_Motor {
    
    private final Collection<T> motors;

    public CSP_MotorGroup(Collection<T> motors) {
        this.motors = motors;

        init();
    }

    @Override
    public void setInverted(boolean inverted) {
        motors.forEach((T motor) -> motor.setInverted(inverted));
    }

    @Override
    public void init() {
        motors.forEach((T motor) -> motor.init());
    }

    @Override
    public void setBrake(boolean braking) {
        motors.forEach((T motor) -> motor.setBrake(braking));
    }

    @Override
    public void setRampRate(double rampRate) {
        motors.forEach((T motor) -> motor.setRampRate(rampRate));
    }

    @Override
    public void set(double percent) {
        motors.forEach((T motor) -> motor.set(percent));
    }

    @Override
    public void setVoltage(double volts) {
        motors.forEach((T motor) -> motor.setVoltage(volts));

    }

    @Override
    public void setEncoder(double position) {
        motors.forEach((T motor) -> motor.setEncoder(position));
    }

    @Override
    public void setPIDF(double kP, double kI, double kD, double kF) {
        motors.forEach((T motor) -> motor.setPIDF(kP, kI, kD, kF));
    }

    @Override
    public void setScalar(double scalar) {
        motors.forEach((T motor) -> motor.setScalar(scalar));
    }

    @Override
    public void setPosition(double position) {
        motors.forEach((T motor) -> motor.setPosition(position));
    }

    @Override
    public void setVelocity(double velocity) {
        motors.forEach((T motor) -> motor.setVelocity(velocity));
    }

    @Override
    public double getVelocity() {
        double total = 0.0;
        for (T motor : motors)
            total += motor.getVelocity();
        return total / (double) motors.size();
    }

    @Override
    public double getPosition() {
        double total = 0.0;
        for (T motor : motors)
            total += motor.getPosition();
        return total / (double) motors.size();
    }

    @Override
    public double getTemperature() {
        double total = 0.0;
        for (T motor : motors)
            total += motor.getTemperature();
        return total / (double) motors.size();
    }

    @Override
    public double getCurrent() {
        double total = 0.0;
        for (T motor : motors)
            total += motor.getCurrent();
        return total / (double) motors.size();
    }

    @Override
    public int getID() {
        CSP_Motor[] arr = new CSP_Motor[motors.size()];
        motors.toArray(arr);
        return arr[0].getID();
    }
}
