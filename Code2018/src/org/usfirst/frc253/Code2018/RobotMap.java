// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc253.Code2018;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveTrainLeftBack;
    public static SpeedController driveTrainLeftFront;
    public static SpeedController driveTrainRightFront;
    public static SpeedController driveTrainRightBack;
    public static SpeedController driveTrainLeftCenter;
    public static SpeedController driveTrainRightCenter;
    public static SpeedController IntakeVictorSP1;
    public static SpeedController IntakeVictorSP2;
    public static DigitalInput sensorsPhotoelectricSensor;
    public static Ultrasonic sensorsUltrasonicSensor;
    public static DoubleSolenoid pneumaticsDoubleSolenoid1;
    public static DoubleSolenoid pneumaticsDoubleSolenoid2;
    public static Compressor pneumaticsCompressor1;
    public static Spark elevatorSpark1; 
    public static Spark elevatorSpark2; 
    public static SpeedController elevatorTalon; 
    public static DigitalInput sensorsLimitSwitch1;
    public static DigitalInput sensorsLimitSwitch2; 

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainLeftBack = new TalonSRX(0);
        LiveWindow.addActuator("Drive Train", "LeftBack", (TalonSRX) driveTrainLeftBack);
        
        driveTrainLeftFront = new TalonSRX(1);
        LiveWindow.addActuator("Drive Train", "LeftFront", (TalonSRX) driveTrainLeftFront);
        
        driveTrainRightFront = new TalonSRX(2);
        LiveWindow.addActuator("Drive Train", "RightFront", (TalonSRX) driveTrainRightFront);
        
        driveTrainRightBack = new TalonSRX(3);
        LiveWindow.addActuator("Drive Train", "RightBack", (TalonSRX) driveTrainRightBack);
        
        
        sensorsPhotoelectricSensor = new DigitalInput(0);
        LiveWindow.addSensor("Sensors", "PhotoelectricSensor", sensorsPhotoelectricSensor);
        
        sensorsUltrasonicSensor = new Ultrasonic(1, 2);
        LiveWindow.addSensor("Sensors", "UltrasonicSensor", sensorsUltrasonicSensor);
        
        pneumaticsDoubleSolenoid1 = new DoubleSolenoid(0, 0, 1);
        LiveWindow.addActuator("Pneumatics", "Double Solenoid 1", pneumaticsDoubleSolenoid1);
        
        pneumaticsDoubleSolenoid2 = new DoubleSolenoid(0, 2, 3);
        LiveWindow.addActuator("Pneumatics", "Double Solenoid 2", pneumaticsDoubleSolenoid2);
        
        
        pneumaticsCompressor1 = new Compressor(0);
        
        elevatorSpark1 = new Spark(0); 
        LiveWindow.addActuator("Elevator", "Spark1", elevatorSpark1); 
        
        elevatorSpark2 = new Spark(1); 
        LiveWindow.addActuator("Elevator", "Spark2", elevatorSpark2); 
        
        elevatorTalon = new Talon(4);
        LiveWindow.addActuator("Elevator", "Talon", elevatorTalon); 
        
        sensorsLimitSwitch1 = new DigitalInput(0); 
        LiveWindow.addSensor("Sensors", "LimitSwitch1", sensorsLimitSwitch1); 
        
        sensorsLimitSwitch2 = new DigitalInput(1); 
        LiveWindow.addSensor("Sensors", "LimitSwitch2", sensorsLimitSwitch2); 

        IntakeVictorSP1 = new VictorSP(2);
        LiveWindow.addActuator("Drive Train", "Victor 1", (VictorSP) IntakeVictorSP1);
        
        IntakeVictorSP2 = new VictorSP(3);
        LiveWindow.addActuator("Drive Train", "Victor 2", (VictorSP) IntakeVictorSP2);
        
>>>>>>> branch 'master' of https://github.com/MillsRoboticsTeam253/Code2018.git
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
