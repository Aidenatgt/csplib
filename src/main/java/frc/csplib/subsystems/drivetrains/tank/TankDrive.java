package frc.csplib.subsystems.drivetrains.tank;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.csplib.math.PIDLoop;
import frc.csplib.motors.CSP_Motor;
import frc.csplib.subsystems.CSPSubsystem;
import frc.csplib.subsystems.drivetrains.tank.TankConstants.PIDConstants;

public class TankDrive extends CSPSubsystem {

    private static TankDrive instance = null;
    public static synchronized TankDrive getInstance() {
        if (instance == null) instance = new TankDrive();
        return instance;
    }

    private final PIDLoop leftPID = new PIDLoop(PIDConstants.DRIVE.kP, PIDConstants.DRIVE.kI, PIDConstants.DRIVE.kD);
    private final PIDLoop rightPID = new PIDLoop(PIDConstants.DRIVE.kP, PIDConstants.DRIVE.kI, PIDConstants.DRIVE.kD);

    private final CSP_Motor leftDrive = TankConstants.LEFT_DRIVE;
    private final CSP_Motor rightDrive = TankConstants.RIGHT_DRIVE;

    private TankDrive() {
    }

    @Override
    public void dashboard() {
        SmartDashboard.putString("Chassis Speeds (Robot Oriented)", getChassisSpeeds().toString());
    }
    
    @Override
    public void periodic() {

    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            leftDrive.getVelocity() * TankConstants.METERS_PER_REVOLUTION / TankConstants.ENCODER_CPR,
            rightDrive.getVelocity() * TankConstants.METERS_PER_REVOLUTION / TankConstants.ENCODER_CPR);
    }

    public ChassisSpeeds getChassisSpeeds() {
        return TankConstants.KINEMATICS.forward(getWheelSpeeds());
    }

    public void setVolts(double left, double right) {
        leftDrive.setVoltage(left);
        rightDrive.setVoltage(right);
    }

    public void setWheelSpeeds(DifferentialDriveWheelSpeeds speeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = getWheelSpeeds();

        setVolts(
            leftPID.calculate(wheelSpeeds.leftMetersPerSecond, speeds.leftMetersPerSecond),
            rightPID.calculate(wheelSpeeds.rightMetersPerSecond, speeds.rightMetersPerSecond)
        );
    }

    public void setChassisSpeeds(ChassisSpeeds speeds) {
        setWheelSpeeds(TankConstants.KINEMATICS.inverse(speeds));
    }
}
