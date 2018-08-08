package frc.team253.robot.subsystems.elevator.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
            elevator.elevatorControl.disable();
            elevator.elevate(elevateAxis);
        }else{
            elevator.elevatorControl.setSetpoint(leftMotorA.getSelectedSensorPosition(0));
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
