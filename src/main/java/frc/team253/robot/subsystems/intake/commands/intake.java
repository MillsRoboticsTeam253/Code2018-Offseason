package frc.team253.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team253.robot.subsystems.intake.IntakeSubsystem;

import static frc.team253.robot.Robot.intakeSubsystem;

public class intake extends Command {

    private double spinSpeed;
    private double timeout = 0;

    public intake(double spinSpeed, double timeout){ //implement a timeout for auto paths to run
        this.spinSpeed = spinSpeed;
        this.timeout = timeout;
        requires(intakeSubsystem);
    }

    public intake(double spinSpeed){
        this.spinSpeed = spinSpeed;
        requires(intakeSubsystem);
    }

    public void execute(){
        if(timeout == 0) {
            IntakeSubsystem.runIntake(spinSpeed);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}