package frc.team253.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.subsystems.Drivetrain.DrivetrainSubsystem;

import static frc.team253.robot.Robot.elevator;
import static frc.team253.robot.Robot.oi;

public class elevate extends Command {

    public elevate(){
        requires(elevator);
    }

    protected void execute(){
        double elevateAxis = oi.elevateValue(); //sets elevateAxis to joystick input

        SmartDashboard.putNumber("Elevator Joystick Input",elevateAxis);
        SmartDashboard.putBoolean("Elevator PID Controller Active", elevator.elevatorControl.isEnabled());


        if(Math.abs(elevateAxis) >= 0.1){
            elevator.elevatorControl.disable();
            elevator.elevate(elevateAxis);
        }else{
            elevator.elevatorControl.setSetpoint(DrivetrainSubsystem.leftFront.getSelectedSensorPosition(0));
            elevator.elevatorControl.enable();
        }/*
        elevator.elevatorControl.disable();
        if(Math.abs(elevateAxis)>0.1){
            elevator.elevate(elevateAxis);
        }*/

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
