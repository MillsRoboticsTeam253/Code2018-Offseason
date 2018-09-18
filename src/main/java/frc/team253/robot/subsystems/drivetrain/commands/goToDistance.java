package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team253.robot.Robot.drivetrain;

public class goToDistance extends Command {
    public static double distanceFeet;
    boolean status;

    public goToDistance(double distanceFeet) {
        this.distanceFeet = -distanceFeet;
        requires(drivetrain);
    }

    @Override
    protected void execute() {

        drivetrain.resetEncoders();

        status = drivetrain.driveDistance(((distanceFeet * 12)/(6*Math.PI))*4517);

    }

    @Override
    protected boolean isFinished() {

        return status;

    }
}
