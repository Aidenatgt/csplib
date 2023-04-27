package frc.csplib.subsystems.drivetrains.tank;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;

public class TankKinematics {
    
    private final double trackwidth;

    public TankKinematics(double trackwidth) {
        this.trackwidth = trackwidth;
    }

    public ChassisSpeeds forward(DifferentialDriveWheelSpeeds speeds) {
        return new ChassisSpeeds(
            (speeds.leftMetersPerSecond + speeds.rightMetersPerSecond) / 2.0,
            0.0,
            (speeds.rightMetersPerSecond - speeds.leftMetersPerSecond) / trackwidth
        );
    }

    public DifferentialDriveWheelSpeeds inverse(ChassisSpeeds speeds) {
        return new DifferentialDriveWheelSpeeds(
            speeds.vxMetersPerSecond - speeds.omegaRadiansPerSecond * trackwidth / 2.0,
            speeds.vxMetersPerSecond + speeds.omegaRadiansPerSecond * trackwidth / 2.0
        );
    }
}
