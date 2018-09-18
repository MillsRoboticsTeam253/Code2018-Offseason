package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.subsystems.misc.Miscellaneous.navX;

public class turnToAngle extends Command {

    double angle;
    boolean isComplete;

    public turnToAngle(double angle) {
        this.angle = angle;
        requires(drivetrain);
    }

    protected void execute(){

        double currentHeading = navX.getAngle();
        double targetHeading = currentHeading + angle;
        double headingError = targetHeading - currentHeading;
        double turn = .8 * (-1.0/80.0) * headingError;

        drivetrain.drive(turn, -turn);

        if(Math.abs(targetHeading-currentHeading)<10){
            isComplete = true;
        } else {
            isComplete = false;
        }

    }

    @Override
    protected boolean isFinished() {

        return isComplete;

    }
}
