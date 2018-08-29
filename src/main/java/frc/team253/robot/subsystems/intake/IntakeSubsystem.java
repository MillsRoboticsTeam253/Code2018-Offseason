package frc.team253.robot.subsystems.intake;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team253.robot.subsystems.intake.commands.intake;

public class IntakeSubsystem extends Subsystem {

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new intake());
    }

}
