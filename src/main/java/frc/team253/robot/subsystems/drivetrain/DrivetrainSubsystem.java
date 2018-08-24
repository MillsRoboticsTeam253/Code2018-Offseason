package frc.team253.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.subsystems.misc.Miscellaneous;
import frc.team253.robot.subsystems.drivetrain.commands.curvatureDrive;

import java.util.Arrays;

import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainConstants.*;

public class DrivetrainSubsystem extends Subsystem {

    //Sets up singleton class to make it impossible to create more than one drivetrain
    private static DrivetrainSubsystem instance = null;
    public static DrivetrainSubsystem getInstance() {
        if (instance == null)
            instance = new DrivetrainSubsystem();
        return instance;
    }

    //Declaring important stuff
    private static final AHRS gyro = Miscellaneous.navX;

    public static final TalonSRX
            leftMotorA = new TalonSRX(driveTrainLeftBack),
            leftMotorB = new TalonSRX(driveTrainLeftFront),
            rightMotorA = new TalonSRX(driveTrainRightFront),
            rightMotorB = new TalonSRX(driveTrainRightBack);

    private static final TalonSRX[] motors = {leftMotorA, leftMotorB, rightMotorB, rightMotorA};

    public static final DoubleSolenoid shifter = new DoubleSolenoid(shifterPCM, shifterA, shifterB);

    public void initDefaultCommand() { setDefaultCommand(new curvatureDrive()); }

    private DrivetrainSubsystem() {

        //Sets up A motors as masters and B motors as slaves
        leftMotorB.follow(leftMotorA);
        rightMotorB.follow(rightMotorA);

        //Sets common settings for all motors
        Arrays.stream(motors).forEach(motor -> {
            motor.configPeakCurrentLimit(45, 10);
            motor.configPeakCurrentDuration(500, 10);
            motor.configContinuousCurrentLimit(35, 10);
            motor.configVoltageCompSaturation(12, 10);
            motor.enableVoltageCompensation(true);
        });

        //Negates the right side of the drivetrain
        leftMotorA.setInverted(false);
        leftMotorB.setInverted(false);
        rightMotorA.setInverted(true);
        rightMotorB.setInverted(true);

        //Settings for Grayhill 63R256 encoder on left drivetrain side
        leftMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        leftMotorA.setSensorPhase(false);

        //Settings for Grayhill 63R256 encoder on right drivetrain side
        rightMotorA.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        rightMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        rightMotorA.setSensorPhase(false);

        //Settings for CTRE MagEncoder on elevator
        leftMotorB.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftMotorB.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        leftMotorB.setSensorPhase(false);

    }

    public void shiftGear(){
        switch(shifter.get()){
            case kForward:
                shifter.set(DoubleSolenoid.Value.kReverse);
                break;
            default:
                shifter.set(DoubleSolenoid.Value.kForward);
                break;
        }
    }

    public void shiftGear(DoubleSolenoid.Value shiftTo){
        shifter.set(shiftTo);
    }

    public void drive(double leftspeed, double rightspeed) { //Sets drivetrain sides to speed parameters

        leftMotorA.set(ControlMode.PercentOutput, leftspeed);
        rightMotorA.set(ControlMode.PercentOutput, rightspeed);

        SmartDashboard.putNumber("left back" ,leftMotorA.getMotorOutputVoltage());
        SmartDashboard.putNumber("left front" ,leftMotorB.getMotorOutputVoltage());
        SmartDashboard.putNumber("right back" ,rightMotorB.getMotorOutputVoltage());
        SmartDashboard.putNumber("right front" ,rightMotorA.getMotorOutputVoltage());

        SmartDashboard.putNumber("left encoder", leftMotorA.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("right encoder", rightMotorA.getSelectedSensorPosition(0));

    }

    public void resetEncoders(){
        drivetrain.leftMotorA.setSelectedSensorPosition(0,0,10);
        drivetrain.rightMotorA.setSelectedSensorPosition(0,0,10);
    }

    public void resetGyro(){
        gyro.reset();
    }

    public static void setBrakeMode(){
        Arrays.stream(motors).forEach(motor -> motor.setNeutralMode(NeutralMode.Brake));
    }

    public static void setCoastMode(){
        Arrays.stream(motors).forEach(motor -> motor.setNeutralMode(NeutralMode.Coast));
    }

}
