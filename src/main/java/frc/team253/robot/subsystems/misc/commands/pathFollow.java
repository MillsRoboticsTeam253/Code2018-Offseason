package frc.team253.robot.subsystems.misc.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.Robot;
import frc.team253.robot.subsystems.misc.MiscConstants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.*;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainConstants.*;
import static frc.team253.robot.subsystems.misc.Miscellaneous.navX;

public class pathFollow extends Command {

    private double kP = 0, kI = 0, kD = 0, kV = 1/2.872716583788768, kA = 0;

    Trajectory trajecLeft, trajecRight;
    EncoderFollower followerLeft, followerRight;

    public pathFollow(String pathName){

        requires(drivetrain);

        trajecLeft = Pathfinder.readFromCSV(new File("/home/lvuser/profiles/" + pathName + "_left.csv"));
        trajecRight = Pathfinder.readFromCSV(new File("/home/lvuser/profiles/" + pathName + "_right.csv"));
    }

    public pathFollow(Waypoint[] points){

        requires(drivetrain);

        Trajectory.Config pointsConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, MiscConstants.kTimeStep, 2.85, 2, 15);
        Trajectory pointsTrajec = Pathfinder.generate(points, pointsConfig);

        TankModifier modifier = new TankModifier(pointsTrajec);
        modifier.modify(kWheelbase);

        trajecLeft = modifier.getLeftTrajectory();
        trajecRight = modifier.getRightTrajectory();
    }

    protected void initialize(){
        drivetrain.resetGyro();
        drivetrain.resetEncoders();

        setCoastMode();

        kP = SmartDashboard.getNumber("P gain", kP);
        kI = SmartDashboard.getNumber("I gain", kI);
        kD = SmartDashboard.getNumber("D gain", kD);
    }

    protected void execute() {

        followerLeft = new EncoderFollower(trajecLeft);
        followerRight = new EncoderFollower(trajecRight);

        followerLeft.configureEncoder(leftMotorA.getSelectedSensorPosition(0),
                4517, kWheelDiameter);
        followerRight.configureEncoder(rightMotorA.getSelectedSensorPosition(0),
                4517, kWheelDiameter);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);

        double left = followerLeft.calculate(leftMotorA.getSelectedSensorPosition(0));
        double right = followerRight.calculate(rightMotorA.getSelectedSensorPosition(0));

        double gyroHeading = -navX.getAngle(); //Inverts gyro to make it left hand positive like Pathfinder
        double desiredHeading = Pathfinder.r2d(followerRight.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = .8 * (-1.0/80.0) * angleDifference;

        double leftspeed = left+turn;
        double rightspeed = right-turn;

        SmartDashboard.putNumber("commanded left speed", -leftspeed);
        SmartDashboard.putNumber("commanded right speed", -rightspeed);

        SmartDashboard.putNumber("left encoder", leftMotorA.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("right encoder",rightMotorA.getSelectedSensorPosition(0));

        //Determine if this is necessary later
        //leftspeed = processDriveChar(left, Constants.kHRobotVmax, kHVeloCharSlopeL,kHVeloCharInterceptL);
        //rightspeed = processDriveChar(right, Constants.kHRobotVmax, kHVeloCharSlopeR,kHVeloCharInterceptR);

        Robot.drivetrain.drive(-leftspeed, -rightspeed);
    }

    @Override
    protected boolean isFinished() { return followerLeft.isFinished() && followerRight.isFinished(); }

}

