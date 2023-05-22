package frc.csplib.motors.controllers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import frc.csplib.math.State;
import frc.csplib.math.functions.StateObserver;
import frc.csplib.motors.BrushlessMotorController;
import frc.csplib.motors.MotorConstants;
import frc.csplib.motors.OutputMode;

public class BrushlessCANSparkMax extends CANSparkMax implements BrushlessMotorController {

    private MotorConstants constants;

    private final RelativeEncoder encoder = super.getEncoder();

    private final StateObserver observer = new StateObserver();

    private OutputMode mode = OutputMode.PERCENT;
    private double output = 0.0;

    private boolean shutoff = false;
    private boolean warning = false;

    private double shutoffTemp, warningTemp;

    private final Notifier monitor = new Notifier(() -> {
        observer.update(encoder.getPosition());

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
                super.setVoltage(constants.nomVolts * (output/constants.torque + (0.1*encoder.getVelocity())/constants.rpm));
        }
    });

    public BrushlessCANSparkMax(MotorConstants constants, int canID) {
        super(canID, MotorType.kBrushless);
        this.constants = constants;

        init();
    }

    @Override
    public void setTorqueOutput(Number output) {
        mode = OutputMode.TORQUE;
        this.output = output.doubleValue();
    }

    @Override
    public void setPosition(Number position) {
        encoder.setPosition(position.doubleValue());
        State current = observer.get();
        observer.setState(new State(position.doubleValue(), current.firstDerivative, current.secondDerivative));
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
        return super.getMotorTemperature();
    }

    @Override
    public Number getTorqueOutput() {
        return constants.torque * (getVoltage().doubleValue() / constants.nomVolts - (encoder.getVelocity())/constants.rpm);
    }

    @Override
    public Number getCurrent() {
        return super.getOutputCurrent();
    }

    @Override
    public State getState() {
        return observer.get();
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
        setPosition(0.0);

        monitor.stop();
        monitor.startPeriodic(0.05);
    }

    @Override
    public void setRampRate(Number rampRate) {
        super.setClosedLoopRampRate(1.0 / rampRate.doubleValue());
        super.setOpenLoopRampRate(1.0 / rampRate.doubleValue());
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
        return super.getDeviceId();
    }

    @Override
    public Number getVoltage() {
        return super.getVoltageCompensationNominalVoltage() * super.get();
    }

    @Override
    public void setBraking(boolean braking) {
        super.setIdleMode(braking ? IdleMode.kBrake : IdleMode.kCoast);
    }
    
}
