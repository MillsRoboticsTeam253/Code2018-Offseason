package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class rotationActivity extends CommandGroup {

    public rotationActivity(){
        addSequential(new goToDistance(3.33), 10);
        addSequential(new turnToAngle(-90), 10);
        addSequential(new goToDistance(20), 10);
        addSequential(new turnToAngle(90), 10);
    }
}
