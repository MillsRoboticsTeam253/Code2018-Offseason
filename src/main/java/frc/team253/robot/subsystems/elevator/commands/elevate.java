package frc.team253.robot.subsystems.elevator.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.bobabots.utilities.RunCommand;
import frc.team253.robot.OI;
import frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.Robot.elevator;
import static frc.team253.robot.Robot.oi;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorA;

public class elevate extends Command {

    public elevate(){
        requires(elevator);
    }

    protected void execute(){

        double elevateAxis = oi.elevateValue(); //sets elevateAxis to joystick input

        SmartDashboard.putNumber("Elevator Joystick Input",elevateAxis);
        SmartDashboard.putBoolean("Elevator PID Controller Active", elevator.elevatorControl.isEnabled());

        if(Math.abs(elevateAxis) >= 0.1){

            if(elevator.elevatorControl.isEnabled()){ //if PIDController *is* enabled, disable it
                elevator.elevatorControl.disable();
            }
            elevator.elevate(elevateAxis);

        } else {

            if(!elevator.elevatorControl.isEnabled()){ //if PIDController is *not* enabled, enable it
                elevator.elevatorControl.enable();
            }

        }

    }


    @Override
    protected boolean isFinished() {
        return false;
    }
}
