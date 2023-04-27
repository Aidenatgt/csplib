package frc.csplib.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.csplib.Loop;

public abstract class CSPSubsystem extends SubsystemBase {
    private Loop dashNotifier = new Loop(() -> dashboard());

    public CSPSubsystem() {
        dashNotifier.startPeriodic(0.1);
    }

    public CSPSubsystem(double period) {
        dashNotifier.startPeriodic(period);
    }
    
    public void dashboard() {}
}
