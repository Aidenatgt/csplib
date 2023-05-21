package frc.csplib.motors.controllers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import frc.csplib.math.State;
import frc.csplib.math.functions.StateObserver;
import frc.csplib.motors.BrushlessMotorController;
import frc.csplib.motors.MotorConstants;
import frc.csplib.motors.OutputMode;

public class Falcon500 extends WPI_TalonFX implements BrushlessMotorController {

    private final StateObserver observer = new StateObserver();

    private OutputMode mode = OutputMode.PERCENT;
    private double output = 0.0;

    private boolean shutoff = false;
    private boolean warning = false;

    private double shutoffTemp, warningTemp;

    private final Notifier monitor = new Notifier(() -> {
        observer.update(super.getSelectedSensorPosition() / 2048.0);

        if (shutoff && getTempC().doubleValue() > shutoffTemp) {
            setBraking(false);
            mode = OutputMode.PERCENT;
            output = 0.0;
        }

        if (warning && getTempC().doubleValue() > warningTemp) {
            DriverStation.reportWarning(String.format("Motor ID: %d is above the temperature warning threshold (%f/%f)", getID(), getTempC().doubleValue(), warningTemp), false);
        }

        switch (mode) {
            case PERCENT:
                super.set(output);
            case VOLTS:
                super.setVoltage(output);
            case TORQUE:
                super.setVoltage(MotorConstants.FALCON_500.nomVolts * (output/MotorConstants.FALCON_500.torque + (0.1*super.getSelectedSensorVelocity()/2048.0)/MotorConstants.FALCON_500.rpm));
        }
    });

    public Falcon500(int canID) {
        super(canID);
    }

    @Override
    public void setTorqueOutput(Number output) {
        mode = OutputMode.TORQUE;
        this.output = output.doubleValue();
    }

    @Override
    public void enableWarningTemp(Number temp) {
        warning = true;
        warningTemp = temp.doubleValue();
    }

    @Override
    public void enableShutoffTemp(Number temp) {
        shutoff = true;
        shutoffTemp = temp.doubleValue();
    }

    @Override
    public void disableWarningTemp() {
        warning = false;
    }

    @Override
    public void disableShutoffTemp() {
        shutoff = false;        
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
        mode = OutputMode.PERCENT;
        output = percent.doubleValue();
    }

    @Override
    public void setVoltage(Number volts) {
        mode = OutputMode.PERCENT;
        output = volts.doubleValue();
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
