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

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;

import java.util.ArrayList;

import org.usfirst.frc253.Code2018.commands.*;
import org.usfirst.frc253.Code2018.subsystems.*;
import org.usfirst.frc253.Code2018.profiles.TrajecLib;
import org.usfirst.frc253.Code2018.profiles.TrajectoryContainer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
//Robot gets general structure from IterativeRobot 
public class Robot extends IterativeRobot {

	//declarations; you're making space for objects (having computer clear memory for objects)
    Command autonomousCommand;
    SendableChooser<Position> positionChooser;
    SendableChooser<Goal> goalChooser;
    SendableChooser<Boolean> switchSideChooser;
    SendableChooser<AutoMode> autoModeChooser;
    SendableChooser<Priority> priorityChooser;
    
    SendableChooser<TrajectoryContainer> trajecChooser;
    SendableChooser<Boolean> reverseChooser;
    
    public static SendableChooser<Double> propChanger;
    public static SendableChooser<Double> intChanger;
    public static SendableChooser<Double> derivChanger;
    public static SendableChooser<Double> feedChanger;
    
    public static SendableChooser<Trajectory> elevatorChooser;
    //more declarations; public static=you don't need a robot object to use them, anything can use 
    //them; space in computer is reserved for this  
    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Sensors sensors;
    public static Pneumatics pneumatics;
    public static Elevator elevator;
    public static Intake intake;
    public static Arms arms;
    //another declaration 
    public String gameData;
    
    //enum = mini object; object that represents data type 
    public enum Position{
    	LEFT('L'), CENTER('C'), RIGHT('R');
    	
    	//field = variable or object covers entire class; declaration; private = only accessible 
    	//within class; final = specifies values that can't be changed 
    	private final char pos;
    	
    	//constructor for position
    	private Position(char pos){
    		this.pos = pos;
    	}
    	
    	//method that returns row bow's character of position to be compared to other things  
    	public char getPos(){
    		return pos;
    	}
    }
    
    public enum Goal{
    	SCALE, SWITCH, EXCHANGE, BASELINE;
    }
    
    public enum AutoMode{
    	SAFE, TEST, EXP;
    }
    
    public enum Priority{
    	DEFAULT, BASELINE;
    }

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    UsbCamera camera;
    UsbCamera camera2;
    //what happens when robot starts 
    public void robotInit() {
    //initiates RobotMap 
    	RobotMap.init(); 
    	
    	camera = CameraServer.getInstance().startAutomaticCapture(0);
//    	camera2 = CameraServer.getInstance().startAutomaticCapture(1);
////    	
    	camera.setResolution(320, 240);
    	camera.setFPS(15);
//    	camera2.setResolution(320, 240);	
//    	camera2.setFPS(15);
    	    	
    	propChanger = new SendableChooser<Double>();
	    propChanger.addObject("+0.1", 0.1);
	    propChanger.addObject("+0.01", 0.01);
	    propChanger.addObject("+0.001", 0.001);
	    propChanger.addDefault("0 (P Gain)", 0.0);
	    propChanger.addObject("-0.001", -0.001);
	    propChanger.addObject("-0.01", -0.01);
	    propChanger.addObject("-0.1", -0.1);
	    SmartDashboard.putData("Proportional Changer", propChanger);
	    
	    intChanger = new SendableChooser<Double>();
	    intChanger.addObject("+0.001", 0.001);
	    intChanger.addObject("+0.0005", 0.005);
	    intChanger.addObject("+0.0001", 0.0001);
	    intChanger.addDefault("0 (I Gain)", 0.0);
	    intChanger.addObject("-0.0001", -0.0001);
	    intChanger.addObject("-0.0005", -0.0005);
	    intChanger.addObject("-0.001", -0.001);
	    //SmartDashboard.putData("Integral Changer", intChanger);
	    
	    derivChanger = new SendableChooser<Double>();
	    derivChanger.addObject("+10", 10.0);
	    derivChanger.addObject("+5", 5.0);
	    derivChanger.addObject("+1", 1.0);
	    derivChanger.addDefault("0 (D Gain)", 0.0);
	    derivChanger.addObject("-1", -1.0);
	    derivChanger.addObject("-5", -5.0);
	    derivChanger.addObject("-10", -10.0);
	    SmartDashboard.putData("Derivative Changer", derivChanger);
	    
	    feedChanger = new SendableChooser<Double>();
	    feedChanger.addObject("+10", 10.0);
	    feedChanger.addObject("+1", 1.0);
	    feedChanger.addObject("+0.1", 0.1);
	    feedChanger.addDefault("0 (F Gain)", 0.0);
	    feedChanger.addObject("-0.1", -0.1);
	    feedChanger.addObject("-1", -1.0);
	    feedChanger.addObject("-10", -10.0);
	    //SmartDashboard.putData("FeedForward Changer", feedChanger);
	    
	    elevatorChooser = new SendableChooser<Trajectory>();
	    elevatorChooser.addObject("Scale", TrajecLib.ScaleUp);
	    elevatorChooser.addObject("Switch", TrajecLib.SwitchUp);
	    elevatorChooser.addDefault("Do not set height", null);
	    SmartDashboard.putData("Elevator Chooser", elevatorChooser);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    	//refer line 41, now we're putting these new objects into the space that was reserved 
        driveTrain = new DriveTrain();
        sensors = new Sensors();
        pneumatics = new Pneumatics();
        elevator = new Elevator();
        intake = new Intake();
        arms = new Arms();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        //The choosers got data from the smart dashboard 
        positionChooser = new SendableChooser<Position>();
        positionChooser.addDefault("Center", Position.CENTER);
        positionChooser.addObject("Left", Position.LEFT);
        positionChooser.addObject("Right", Position.RIGHT);
        SmartDashboard.putData("Position", positionChooser);
        
        goalChooser = new SendableChooser<Goal>();
        goalChooser.addDefault("Base line (All positions)", Goal.BASELINE);
        goalChooser.addObject("Switch (All positions)", Goal.SWITCH);
        goalChooser.addObject("Scale (Left/Right)", Goal.SCALE);
        goalChooser.addObject("Exchange zone (Center; not implemented)", Goal.EXCHANGE);
        SmartDashboard.putData("Goal", goalChooser);
        
        switchSideChooser = new SendableChooser<Boolean>();
        switchSideChooser.addDefault("Ally is going left/right side of switch", true);
        switchSideChooser.addObject("Ally is going front of switch", false);
        SmartDashboard.putData("SwitchSide", switchSideChooser);

        autoModeChooser = new SendableChooser<AutoMode>();
        autoModeChooser.addDefault("Safe Mode", AutoMode.SAFE);
        autoModeChooser.addObject("Test Mode", AutoMode.TEST);
        autoModeChooser.addObject("EXPERIMENTAL MODE", AutoMode.EXP);
        SmartDashboard.putData("Auto Mode Chooser", autoModeChooser);
        
        priorityChooser = new SendableChooser<Priority>();
        priorityChooser.addDefault("Default priority", Priority.DEFAULT);
        priorityChooser.addObject("Prioritize Baseline", Priority.BASELINE);
        SmartDashboard.putData("Priority Chooser", priorityChooser);
        
        trajecChooser = new SendableChooser<TrajectoryContainer>();
        trajecChooser.addDefault("No trajectory", null);
        trajecChooser.addObject("Straight15ft", new TrajectoryContainer(TrajecLib.Straight15ft, false));
        trajecChooser.addObject("RtoRSwitch", new TrajectoryContainer(TrajecLib.RtoRSwitch, false));
        trajecChooser.addObject("RtoRScale", new TrajectoryContainer(TrajecLib.RtoRScale, false));
        trajecChooser.addObject("LtoLSwitch", new TrajectoryContainer(TrajecLib.RtoRSwitch, true));
        trajecChooser.addObject("CtoRSwitch", new TrajectoryContainer(TrajecLib.CtoRSwitch, false));
        trajecChooser.addObject("CtoLSwitch", new TrajectoryContainer(TrajecLib.CtoLSwitch, false));
        trajecChooser.addObject("RtoLScale", new TrajectoryContainer(TrajecLib.RtoLScale, false));
        trajecChooser.addObject("LtoRScale", new TrajectoryContainer(TrajecLib.RtoLScale, true));
        trajecChooser.addObject("BackuptoRScale", new TrajectoryContainer(TrajecLib.BackuptoRScale, false));
        trajecChooser.addObject("BackuptoLScale", new TrajectoryContainer(TrajecLib.BackuptoRScale, true));
        trajecChooser.addObject("BackuptoRCube", new TrajectoryContainer(TrajecLib.BackuptoRCube, false));
        trajecChooser.addObject("BackuptoLCube", new TrajectoryContainer(TrajecLib.BackuptoRCube, true));
        trajecChooser.addObject("CtoCube", new TrajectoryContainer(TrajecLib.CtoCube, false));
        SmartDashboard.putData("Trajectory Chooser", trajecChooser);
        
        String experimentalAutos = "Two cube auto (Side scale, same side)\n"
        						+  "Opposite side scale (Side scale, opp side)\n"
        						+  "Two cube switch (Center switch)";
        SmartDashboard.putString("Experimental Autos", experimentalAutos);
        
        reverseChooser = new SendableChooser<Boolean>();
        reverseChooser.addDefault("Do not reverse trajectory", false);
        reverseChooser.addObject("Reverse trajectory", true);
        SmartDashboard.putData("Reverse Chooser", reverseChooser);
        
        oi = new OI();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    }

    public void disabledPeriodic() {
    	Scheduler.getInstance().run();
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        SmartDashboard.putString("gameData", gameData);
    }

    public void autonomousInit() {
    	//receives data about colors 
    	gameData = DriverStation.getInstance().getGameSpecificMessage();

    	//gets data from choosers 
    	Position position = positionChooser.getSelected();
    	Goal goal = goalChooser.getSelected();
    	boolean isSideSwitch = switchSideChooser.getSelected();
    	AutoMode autoMode = autoModeChooser.getSelected();
    	Priority priority = priorityChooser.getSelected();
    	
    	boolean reverse = reverseChooser.getSelected();
    	
    	//starts auto by using info compiled from line 157 
    	if(autoMode == AutoMode.TEST){
	    	if(elevatorChooser.getSelected() != null){
	    		autonomousCommand = new AutonomousCommand(elevatorChooser.getSelected(), reverse);
	    	} else if(trajecChooser.getSelected() != null){
	    		autonomousCommand = new AutonomousCommand(trajecChooser.getSelected(), reverse);
	    	} else {
	    		autonomousCommand = new AutonomousCommand(position, goal, isSideSwitch, gameData, autoMode, priority);
	    	}
    	} else {
    		autonomousCommand = new AutonomousCommand(position, goal, isSideSwitch, gameData, autoMode, priority);
    	}
	    
    		// schedule the autonomous command (example)
        if (autonomousCommand != null){
        	autonomousCommand.start();
        }
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        gameData = DriverStation.getInstance().getGameSpecificMessage();

    	//gets data from choosers 
    	Position position = positionChooser.getSelected();
    	Goal goal = goalChooser.getSelected();
    	boolean isSideSwitch = switchSideChooser.getSelected();
    	AutoMode autoMode = autoModeChooser.getSelected();
    	Priority priority = priorityChooser.getSelected();
    	
    	boolean reverse = reverseChooser.getSelected();
    	
    	//starts auto by using info compiled from line 157 
    	if(autoMode == AutoMode.TEST){
	    	if(elevatorChooser.getSelected() != null){
	    		autonomousCommand = new AutonomousCommand(elevatorChooser.getSelected(), reverse);
	    	} else if(trajecChooser.getSelected() != null){
	    		autonomousCommand = new AutonomousCommand(trajecChooser.getSelected(), reverse);
	    	} else {
	    		autonomousCommand = new AutonomousCommand(position, goal, isSideSwitch, gameData, autoMode, priority);
	    	}
    	} else {
    		autonomousCommand = new AutonomousCommand(position, goal, isSideSwitch, gameData, autoMode, priority);
    	}
        
        SmartDashboard.putData(autonomousCommand);
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
}
