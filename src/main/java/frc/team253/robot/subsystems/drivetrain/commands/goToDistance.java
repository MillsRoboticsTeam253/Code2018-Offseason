package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorA;

public class goToDistance extends Command {

    private double distanceFeet;
    private boolean isComplete;

    public goToDistance(double distanceFeet) {
        this.distanceFeet = -distanceFeet;
        requires(drivetrain);

        drivetrain.resetEncoders();
    }

    @Override
    protected void execute() {

        double target = feetToEncoderTicks(distanceFeet);
        double error = target - leftMotorA.getSelectedSensorPosition(0);

        drivetrain.driveDistance(target);

        if(Math.abs(error) < 300) {
            isComplete = true;
        }else{
            System.out.println(error);
            isComplete = false;
        }

    }

    public double feetToEncoderTicks(double feet){
        return ((feet * 12)/(6*Math.PI))*4517;
    }

    @Override
    protected boolean isFinished() {

        return isComplete;

    }
}
