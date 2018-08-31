package frc.team253.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj.command.Command;

import static frc.team253.robot.Robot.intakeSubsystem;

public class intake extends Command {

    double spinSpeed;

    public intake(double spinSpeed){
        this.spinSpeed = spinSpeed;
        requires(intakeSubsystem);
    }

    public void execute(){

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}