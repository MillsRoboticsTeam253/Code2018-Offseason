package frc.team253.robot.utilities;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorA;

public class ElevatorEncoderSource implements PIDSource {
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        return leftMotorA.getSelectedSensorPosition(0);
    }
}
