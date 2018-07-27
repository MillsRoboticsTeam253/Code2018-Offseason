package frc.team253.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.utils.Constants;
import frc.team253.robot.RobotMap;
import frc.team253.robot.commands.drive;

import static frc.team253.robot.Robot.drivetrain;

public class DriveTrain extends Subsystem {

    private final AHRS gyro = RobotMap.gyro;

    private static DriveTrain instance = null;

    public static final TalonSRX leftBack = RobotMap.driveTrainLeftBack;
    public static final TalonSRX leftFront = RobotMap.driveTrainLeftFront;
    public static final TalonSRX rightFront = RobotMap.driveTrainRightFront;
    public final TalonSRX rightBack = RobotMap.driveTrainRightBack;

    private DriveTrain() {
        leftFront.follow(leftBack);
        rightBack.follow(rightFront);

        leftFront.configPeakCurrentLimit(0, 10);
        rightBack.configPeakCurrentLimit(0, 10);
        leftBack.configPeakCurrentLimit(0, 10);
        rightFront.configPeakCurrentLimit(0, 10);

        leftFront.configContinuousCurrentLimit(45, 10);
        rightBack.configContinuousCurrentLimit(45, 10);
        leftBack.configContinuousCurrentLimit(45, 10);
        rightFront.configContinuousCurrentLimit(45, 10);

        leftFront.setInverted(false);
        rightFront.setInverted(true);
        leftBack.setInverted(false);
        rightBack.setInverted(true);

        leftBack.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

        rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

        leftFront.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        leftBack.configVoltageCompSaturation(12, 10);
        rightFront.configVoltageCompSaturation(12, 10);

        leftBack.enableVoltageCompensation(true);
        rightFront.enableVoltageCompensation(true);

        leftBack.setSensorPhase(false);
        rightFront.setSensorPhase(false);
        leftFront.setSensorPhase(false);


    }

    public void initDefaultCommand() {
        setDefaultCommand(new drive());
    }

    public void drive(double leftspeed, double rightspeed) {// We are setting the speed controllers to speed

        leftBack.set(ControlMode.PercentOutput, leftspeed);
        rightFront.set(ControlMode.PercentOutput, rightspeed);

        SmartDashboard.putNumber("left back" ,leftBack.getMotorOutputVoltage());
        SmartDashboard.putNumber("left front" ,leftFront.getMotorOutputVoltage());
        SmartDashboard.putNumber("right back" ,rightBack.getMotorOutputVoltage());
        SmartDashboard.putNumber("right front" ,rightFront.getMotorOutputVoltage());

        SmartDashboard.putNumber("left encoder", leftBack.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("right encoder", rightFront.getSelectedSensorPosition(0));

    }

    public void resetEncoders(){
        leftFront.setSelectedSensorPosition(0,0,10);
        drivetrain.leftBack.setSelectedSensorPosition(0,0,10);
        drivetrain.rightFront.setSelectedSensorPosition(0,0,10);
    }

    public void resetGyro(){
        gyro.reset();
    }
    public double getYaw(){
        return gyro.getYaw();
    }

    public double getPitch(){
        return gyro.getPitch();
    }

    public double getRoll(){
        return gyro.getRoll();
    }

    public static DriveTrain getInstance()
    {
        if (instance == null)
            instance = new DriveTrain();

        return instance;
    }

}
