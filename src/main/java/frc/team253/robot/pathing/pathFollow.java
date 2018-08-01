package frc.team253.robot.pathing;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.Robot;
import frc.team253.robot.subsystems.DriveTrain;
import frc.team253.robot.utils.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;


import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.RobotMap.gyro;
import static frc.team253.robot.commands.drive.processDriveChar;
import static frc.team253.robot.subsystems.DriveTrain.setCoastMode;
import static frc.team253.robot.utils.Constants.*;

public class pathFollow extends Command {

    private double kP = 0, kI = 0, kD = 0, kV = 1/4.1861967305, kA = 0;
    Trajectory trajecLeft, trajecRight;
    EncoderFollower followerLeft, followerRight;

    public pathFollow(String pathName){
        SmartDashboard.putNumber("P gain", kP);
        SmartDashboard.putNumber("I gain", kI);
        SmartDashboard.putNumber("D gain", kD);


        requires(drivetrain);

        trajecLeft = Pathfinder.readFromCSV(new File("/home/lvuser/profiles/" + pathName + "_left.csv"));
        trajecRight = Pathfinder.readFromCSV(new File("/home/lvuser/profiles/" + pathName + "_right.csv"));

        followerLeft = new EncoderFollower(trajecLeft);
        followerRight = new EncoderFollower(trajecRight);

        followerLeft.configureEncoder(DriveTrain.leftBack.getSelectedSensorPosition(0),
                4096, Constants.kWheelDiameterMeters);
        followerRight.configureEncoder(DriveTrain.rightFront.getSelectedSensorPosition(0),
                4096, Constants.kWheelDiameterMeters);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);
    }

    public pathFollow(Waypoint[] points){
        Trajectory.Config pointsConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.kTimeStepGlobal, 4, 2, 15);
        Trajectory pointsTrajec = Pathfinder.generate(points, pointsConfig);

        TankModifier modifier = new TankModifier(pointsTrajec);
        modifier.modify(Constants.kWheelBaseWidthMeters); //feet
        trajecLeft = modifier.getLeftTrajectory();
        trajecRight = modifier.getRightTrajectory();

        followerLeft = new EncoderFollower(trajecLeft);
        followerRight = new EncoderFollower(trajecRight);

        followerLeft.configureEncoder(DriveTrain.leftBack.getSelectedSensorPosition(0),
                4096, Constants.kWheelDiameterMeters);
        followerRight.configureEncoder(DriveTrain.rightFront.getSelectedSensorPosition(0),
                4096, Constants.kWheelDiameterMeters);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);
    }

    protected void initialize(){
        drivetrain.resetGyro();
        drivetrain.resetEncoders();
    }

    protected void execute() {

        setCoastMode();

        kP = SmartDashboard.getNumber("P gain", kP);
        kI = SmartDashboard.getNumber("I gain", kI);
        kD = SmartDashboard.getNumber("D gain", kD);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);

        double left = followerLeft.calculate(Robot.drivetrain.leftBack.getSelectedSensorPosition(0));
        double right = followerRight.calculate(Robot.drivetrain.rightFront.getSelectedSensorPosition(0));

        double gyroHeading = gyro.getYaw();
        double desiredHeading = Pathfinder.r2d(followerLeft.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = .8 * (-1.0/80.0) * angleDifference*0; //REMEMBER TO TUNE THIS LATER

        double leftspeed = left+turn;
        double rightspeed = right-turn;

        SmartDashboard.putNumber("leftspeed",leftspeed);
        SmartDashboard.putNumber("rightspeed",rightspeed);

        SmartDashboard.putNumber("left encoder",drivetrain.leftBack.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("right encoder",drivetrain.rightFront.getSelectedSensorPosition(0));

        leftspeed = processDriveChar(left, Constants.kHRobotVmax, kHVeloCharSlopeL,kHVeloCharInterceptL);
        rightspeed = processDriveChar(right, Constants.kHRobotVmax, kHVeloCharSlopeR,kHVeloCharInterceptR);

        Robot.drivetrain.drive(leftspeed, rightspeed);
    }

    @Override
    protected boolean isFinished() {

        return followerLeft.isFinished() && followerRight.isFinished();

    }

}

