package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.subsystems.misc.Miscellaneous.navX;

public class turnToAngle extends Command {

    private double angle;
    private boolean isComplete;

    private double currentHeading;
    private double targetHeading;

    public turnToAngle(double angle) {
        this.angle = angle;
        requires(drivetrain);

        targetHeading = currentHeading + angle;

    }

    protected void initialize() {
        navX.reset();
    }

    protected void execute(){
        double headingError = targetHeading - navX.getAngle();
        double turn = .8 * (-1.0/80.0) * headingError;

        System.out.println(headingError);
        System.out.println(navX.getAngle());

        drivetrain.drive(turn, -turn);

        if(Math.abs(headingError)<10){
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
