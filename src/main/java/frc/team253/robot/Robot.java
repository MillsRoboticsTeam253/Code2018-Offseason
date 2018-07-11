package frc.team253.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team253.robot.pathing.pathFollow;
import frc.team253.robot.subsystems.DriveTrain;
import frc.team253.robot.subsystems.Elevator;
import frc.team253.robot.subsystems.Limelight;
import frc.team253.robot.subsystems.Pneumatics;

public class Robot extends IterativeRobot {

    public static OI oi;
    public static DriveTrain drivetrain;
    public static Pneumatics pneumatics;
    public static Limelight limelight;
    public static Elevator elevator;



    @Override
    public void robotInit() {
        RobotMap.init();
        drivetrain = DriveTrain.getInstance();
        elevator = Elevator.getInstance();
        pneumatics = Pneumatics.getInstance();
        limelight = Limelight.getInstance();
        oi = new OI();

        drivetrain.resetEncoders();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
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