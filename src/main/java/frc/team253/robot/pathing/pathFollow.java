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

public class pathFollow extends Command {

    private double kP = 0, kI = 0, kD = 0, kV = 1/2.872716583788768, kA = 0;
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
                4517, Constants.kWheelDiameterMeters);
        followerRight.configureEncoder(DriveTrain.rightFront.getSelectedSensorPosition(0),
                4517, Constants.kWheelDiameterMeters);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);
    }

    public pathFollow(Waypoint[] points){
        Trajectory.Config pointsConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, Constants.kTimeStepGlobal, 3.6, 2, 15);
        Trajectory pointsTrajec = Pathfinder.generate(points, pointsConfig);

        TankModifier modifier = new TankModifier(pointsTrajec);
        modifier.modify(Constants.kWheelBaseWidthMeters); //feet
        trajecLeft = modifier.getLeftTrajectory();
        trajecRight = modifier.getRightTrajectory();

        followerLeft = new EncoderFollower(trajecLeft);
        followerRight = new EncoderFollower(trajecRight);

        followerLeft.configureEncoder(DriveTrain.leftBack.getSelectedSensorPosition(0),
                4517, Constants.kWheelDiameterMeters);
        followerRight.configureEncoder(DriveTrain.rightFront.getSelectedSensorPosition(0),
                4517, Constants.kWheelDiameterMeters);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);
    }

    protected void initialize(){
        drivetrain.resetGyro();
        drivetrain.resetEncoders();

        drivetrain.leftBack.setNeutralMode(NeutralMode.Coast);
        drivetrain.leftFront.setNeutralMode(NeutralMode.Coast);
        drivetrain.rightBack.setNeutralMode(NeutralMode.Coast);
        drivetrain.rightFront.setNeutralMode(NeutralMode.Coast);
    }

    protected void execute() {

        drivetrain.leftBack.setNeutralMode(NeutralMode.Coast);
        drivetrain.leftFront.setNeutralMode(NeutralMode.Coast);
        drivetrain.rightBack.setNeutralMode(NeutralMode.Coast);
        drivetrain.rightFront.setNeutralMode(NeutralMode.Coast);

        kP = SmartDashboard.getNumber("P gain", kP);
        kI = SmartDashboard.getNumber("I gain", kI);
        kD = SmartDashboard.getNumber("D gain", kD);

        followerLeft.configurePIDVA(kP, kI, kD, kV, kA);
        followerRight.configurePIDVA(kP, kI, kD, kV, kA);


        double left = followerLeft.calculate(Robot.drivetrain.leftBack.getSelectedSensorPosition(0));
        double right = followerRight.calculate(Robot.drivetrain.rightFront.getSelectedSensorPosition(0));

        double gyroHeading = -gyro.getAngle();
        double desiredHeading = Pathfinder.r2d(followerRight.getHeading());
        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        double turn = .8 * (-1.0/80.0) * angleDifference; //REMEMBER TO TUNE THIS LATER

        double leftspeed = (left+turn);
        double rightspeed = (right-turn);

        SmartDashboard.putNumber("leftspeed",-leftspeed);
        SmartDashboard.putNumber("rightspeed",-rightspeed);

        SmartDashboard.putNumber("left encoder",drivetrain.leftBack.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("right encoder",drivetrain.rightFront.getSelectedSensorPosition(0));

//        System.out.println(followerLeft.getSegment());
//        System.out.println(followerRight.getSegment());

        //leftspeed = drive.processDriveChar(leftspeed,Constants.kLRobotVmax,Constants.kLVeloCharSlopeL,Constants.kLVeloCharInterceptL);
        //rightspeed = drive.processDriveChar(rightspeed,Constants.kLRobotVmax,Constants.kLVeloCharSlopeR,Constants.kLVeloCharInterceptR);

        Robot.drivetrain.drive(-leftspeed, -rightspeed);
    }

    @Override
    protected boolean isFinished() {

        return followerLeft.isFinished() && followerRight.isFinished();

    }

}

