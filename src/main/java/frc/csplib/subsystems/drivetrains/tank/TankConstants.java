package frc.csplib.subsystems.drivetrains.tank;

import edu.wpi.first.math.util.Units;
import frc.csplib.motors.CSP_Falcon;
import frc.csplib.motors.CSP_Motor;

public class TankConstants {

    public enum PIDConstants {
        DRIVE(0.5, 0.0, 0.0);

        public final double kP, kI, kD;
        private PIDConstants(double kP, double kI, double kD) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
        }
    }
    
    private static final double TRACKWIDTH = 1.0; // Meters
    private static final double WHEEL_DIAMETER = 4.0; // Inches
    private static final double GEAR_RATIO = 5.0;
    public static final double ENCODER_CPR = 2048.0;

    public static final double METERS_PER_REVOLUTION = Math.PI * Units.inchesToMeters(WHEEL_DIAMETER) * GEAR_RATIO;
    public static final TankKinematics KINEMATICS = new TankKinematics(TRACKWIDTH);

    public static final CSP_Motor LEFT_DRIVE = new CSP_Falcon(0);
    public static final CSP_Motor RIGHT_DRIVE = new CSP_Falcon(1);
}
