package frc.csplib.motors.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Notifier;
import frc.csplib.math.State;
import frc.csplib.math.functions.Derivative;
import frc.csplib.math.functions.StateObserver;
import frc.csplib.motors.BrushlessMotorController;
import frc.csplib.motors.MotorConstants;

public class Falcon500 extends WPI_TalonFX implements BrushlessMotorController {

    private final StateObserver observer = new StateObserver();

    private double percent = 0.0;

    private final Notifier monitor = new Notifier(() -> {
        observer.update(super.getSelectedSensorPosition() / 2048.0);

        super.set(percent);
    });

    public Falcon500(int canID) {
        super(canID);
    }

    @Override
    public void setTorqueOutput(Number output) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enableWarningTemp(Number temp) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enableShutoffTemp(Number temp) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disableWarningTemp() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disableShutoffTemp() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Number getTempC() {
        return super.getTemperature();
    }

    @Override
    public Number getTorqueOutput() {
        return MotorConstants.FALCON_500.torque * (getVoltage().doubleValue() / MotorConstants.FALCON_500.nomVolts - (0.1*super.getSelectedSensorVelocity()/2048.0)/MotorConstants.FALCON_500.rpm);
    }

    @Override
    public Number getCurrent() {
        return super.getStatorCurrent();
    }

    @Override
    public Number getResistance() {
        return getVoltage().doubleValue() / getCurrent().doubleValue();
    }

    @Override
    public void setInverted(boolean ccp) {
        super.setInverted(!ccp);
    }

    @Override
    public void init() {
        super.setNeutralMode(NeutralMode.EEPROMSetting);
        super.setInverted(TalonFXInvertType.CounterClockwise);

        monitor.stop();
        monitor.startPeriodic(0.05);
    }

    @Override
    public void setRampRate(Number rampRate) {
        super.configOpenloopRamp(1.0/rampRate.doubleValue());
    }

    @Override
    public void set(Number percent) {
        super.set(ControlMode.PercentOutput, percent.doubleValue());
    }

    @Override
    public void setVoltage(Number volts) {
        super.setVoltage(volts.doubleValue());
    }

    @Override
    public int getID() {
        return super.getDeviceID();
    }

    @Override
    public Number getVoltage() {
        return super.getMotorOutputVoltage();
    }

    @Override
    public void setBraking(boolean braking) {
        if (braking) super.setNeutralMode(NeutralMode.Brake);
        else super.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public State getState() {
        return observer.get();
    }
    
}
