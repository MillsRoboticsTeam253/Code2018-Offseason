package frc.team253.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team253.robot.subsystems.intake.IntakeSubsystem;

import static frc.team253.robot.Robot.intakeSubsystem;

public class intake extends Command {

    private double spinSpeed;
    private double timeout;

    public intake(double spinSpeed, double timeout){ //implement a timeout for auto paths to run
        this.spinSpeed = spinSpeed;
        this.timeout = timeout;

        setTimeout(this.timeout);
        requires(intakeSubsystem);
    }

    public intake(double spinSpeed){
        this.spinSpeed = spinSpeed;
        this.timeout = 0;

        requires(intakeSubsystem);

    }

    public void execute(){
        IntakeSubsystem.runIntake(spinSpeed);
    }

    @Override
    protected boolean isFinished() {
        if (this.timeout == 0) {
            return false;
        } else if (isTimedOut()) {
            return true;
        } else {
            return false;
        }
    }
}