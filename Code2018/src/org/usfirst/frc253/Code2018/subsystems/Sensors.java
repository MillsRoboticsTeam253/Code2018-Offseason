// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

//Audrey and Kristen :)

package org.usfirst.frc253.Code2018.subsystems;

import org.usfirst.frc253.Code2018.RobotMap;
import org.usfirst.frc253.Code2018.commands.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Sensors extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final DigitalInput photoelectricSensor = RobotMap.sensorsPhotoelectricSensor;
    private final Ultrasonic ultrasonicSensor = RobotMap.sensorsUltrasonicSensor;
    private final DigitalInput limitswitch1 = RobotMap.sensorsLimitSwitch1; 
    private final DigitalInput limitswitch2 = RobotMap.sensorsLimitSwitch2; 

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    	setDefaultCommand(new ReadData());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public Sensors(){
    	ultrasonicSensor.setAutomaticMode(true);
    	// Actviates ultrasonicSensor 
    	}
    
    public boolean hasCube(){
    	return photoelectricSensor.get();
    	// Tells if there is cube
    }
    public double getDistance(){ 
    	return ultrasonicSensor.getRangeInches(); 
    	// Tells how far it is from object
    }
    public boolean getLimitSwitch1(){
    	return limitswitch1.get(); 
    	//We don't know yet 
    }
    public boolean getLimitSwitch2(){
    	return limitswitch2.get(); 
    	//We don't know yet 
    }
}


