//Justin and Fiona
// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc253.Code2018.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc253.Code2018.Robot;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

/**
 *
 */
public class tankDrive extends Command {
	private boolean changeStatus = true;
	private boolean sensitivityStatus = true;
	private double klowSensMode = 0.35; //how much to multiply sensitivity in low sensitivity mode
	private boolean toggle = true;
	private double kDeadzone = 0.125; //How far you have to push the joystick to get a response (0.125 = 1/8th of full)
	private boolean PIDtoggle = true;
	
	private boolean accelToggleSwitch = true;
	private boolean accelToggle = false;
	private final double delta = 0.1;
	
	double left = 0;
	double right = 0;
	
    public tankDrive() {

    	//says we need drivetrain to do this command 
        requires(Robot.driveTrain);
        setName("Differential Drive");

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 
    	if(Robot.oi.xboxController.getBButton()){
    		Robot.driveTrain.getLeftBack().setSelectedSensorPosition(0, 0, 0);
    		Robot.driveTrain.getRightFront().setSelectedSensorPosition(0, 0, 0);
    	}
    	
    	if(!Robot.oi.xboxController.getRawButton(7)){
    		PIDtoggle = true;
	    	boolean isPressedTurnOn = Robot.oi.getOperatorJoystick2().getRawButton(9);
	    	if(isPressedTurnOn && toggle){
	    		toggle = false; //if statement that makes toggling system
	    		changeStatus = !changeStatus;
	    	}else if(!isPressedTurnOn){
	    		toggle = true;
	    	}
	    	if(!changeStatus){
	        	double leftSpeed = Robot.oi.getLeftJoystickY();
	        	double rightSpeed = Robot.oi.getRightJoystickY();
	        	
	        	Robot.driveTrain.drive(rightSpeed, leftSpeed);
	        	// we are connecting the left joysticks to the left speedcontrollers
	        	// we are connecting the right joysticks to the right speedcontrollers
	        	// we are sending numbers to the speedcontrollers through the method
	    	}else{
	    		double prevLeft = left;
	    		double prevRight = right;
	    		
	        	if(Math.abs(Robot.oi.getLeftJoystickY())<=kDeadzone){
	        		left = -Robot.oi.getRightJoystickX();
	        		right = Robot.oi.getRightJoystickX();
	        	}else if(Robot.oi.xboxController.getTriggerAxis(GenericHID.Hand.kLeft)>0.125){
	        		 left = -Robot.oi.getLeftJoystickY()+Robot.oi.getLeftJoystickY()*Robot.oi.getRightJoystickX();
		    		 right=-Robot.oi.getLeftJoystickY()-Robot.oi.getLeftJoystickY()*Robot.oi.getRightJoystickX();
	        	}else {
	    		    left = Robot.oi.getLeftJoystickY()+Robot.oi.getLeftJoystickY()*Robot.oi.getRightJoystickX();
	    		    right = Robot.oi.getLeftJoystickY()-Robot.oi.getLeftJoystickY()*Robot.oi.getRightJoystickX();
	        	}
	        	/*boolean sensToggle = Robot.oi.xboxController.getRawButton(4);
		    	if(sensToggle && toggle){
		    		toggle = false; //if statement that makes toggling system
		    		sensitivityStatus = !sensitivityStatus;
		    	}else if(!sensToggle){
		    		toggle = true;
		    	}
		    	if(!sensitivityStatus){
		    		Robot.driveTrain.drive(left*klowSensMode, right*klowSensMode);
		    	}else{
		    		Robot.driveTrain.drive(left, right);
		    	}*/
		    	Robot.driveTrain.drive(left, right);
	    	}
    	} else {
    		if(PIDtoggle){
    			Robot.driveTrain.changekP(Robot.propChanger.getSelected());
    			Robot.driveTrain.changekI(Robot.intChanger.getSelected());
    			Robot.driveTrain.changekD(Robot.derivChanger.getSelected());
    			Robot.driveTrain.changekF(Robot.feedChanger.getSelected());
    			
    			PIDtoggle = false;
    		}
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
