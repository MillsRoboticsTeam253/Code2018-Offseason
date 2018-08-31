package frc.team253.robot;


import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team253.robot.subsystems.intake.IntakeSubsystem;
import frc.team253.robot.utilities.pathFollow;
import frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.team253.robot.subsystems.elevator.ElevatorSubsystem;
import frc.team253.robot.subsystems.misc.Limelight;
import frc.team253.robot.subsystems.misc.Miscellaneous;

import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.setBrakeMode;

public class Robot extends TimedRobot {

    public static OI oi;
    public static DrivetrainSubsystem drivetrain;
    public static Limelight limelight;
    public static ElevatorSubsystem elevator;
    public static IntakeSubsystem intakeSubsystem;

    @Override
    public void robotInit() {
        Miscellaneous.init();
        drivetrain = DrivetrainSubsystem.getInstance();
        elevator = ElevatorSubsystem.getInstance();
        limelight = Limelight.getInstance();
        intakeSubsystem =  IntakeSubsystem.getInstance();
        oi = new OI();

        drivetrain.resetEncoders();
    }

    @Override
    public void disabledInit() {
        //SET BRAKE
    }

    @Override
    public void autonomousInit() {
        //SET COAST
        new pathFollow("CtoRSwitch").start();
    }

    @Override
    public void teleopInit() {
        setBrakeMode();
    }

    @Override
    public void testInit() {
    }


    @Override
    public void disabledPeriodic() {
        NetworkTableInstance.getDefault().getTable("Limelight").getEntry("ledMode").setNumber(1);

        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        //NetworkTableInstance.getDefault().getTable("Limelight").getEntry("ledMode").setNumber(1);
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
    }
}