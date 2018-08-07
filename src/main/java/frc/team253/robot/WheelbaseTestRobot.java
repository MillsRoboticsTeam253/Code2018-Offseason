package frc.team253.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class WheelbaseTestRobot extends IterativeRobot {

    public static TalonSRX leftFront,leftBack,rightFront,rightBack;
    public static XboxController controller;
    public static AHRS gyro;
    public double gyroHeading;

    public static boolean active = false;

    //Declaring my constants

    public static double speedConstant = 0.075;
    public static double rotations = 1;


    @Override
    public void teleopPeriodic() {
        if(isEnabled()){ // Enable check for safety

            if(controller.getRawButtonPressed(1)){ //Resets encoder on the leading edge of press
                resetEncoders();
                active = true;
            }

            if(controller.getRawButtonReleased(1)){
                active = false;
            }

            if(active) {
                gyroHeading = gyro.getAngle();

                if(gyroHeading <= 360*rotations){
                    drive(-speedConstant, speedConstant); //Spins the robot rotation times

                } else { //Prints all important info to console once rotations are complete to minimize error from inertia

                    System.out.println("left encoder: " + -leftBack.getSelectedSensorPosition(0));
                    System.out.println("right encoder " + rightFront.getSelectedSensorPosition(0));

                    double leftDiameter = -leftBack.getSelectedSensorPosition(0)/rotations*Math.PI;
                    double rightDiameter = rightFront.getSelectedSensorPosition(0)/rotations*Math.PI;

                    System.out.println("avg left diameter: " + leftDiameter);
                    System.out.println("avg right diameter: " + rightDiameter);

                    System.out.println("avg overall diameter: " + (leftDiameter*rightDiameter)/2);

                    active = false;
                }
            } else {
                drive(0,0);
            }

        }
    }

    @Override
    public void teleopInit() {
        resetEncoders();
    }

    @Override
    public void robotInit() {

        leftBack = new TalonSRX(4);
        leftFront = new TalonSRX(3);
        rightBack = new TalonSRX(1);
        rightFront = new TalonSRX(2);

        leftFront.follow(leftBack);
        rightBack.follow(rightFront);

        rightBack.setInverted(true);
        rightFront.setInverted(true);

        leftBack.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

        rightFront.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature, 1, 10);
        rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

        controller = new XboxController(1);
        gyro = new AHRS(SPI.Port.kMXP);

    }

    //Resets encoders and gyro
    public void resetEncoders() {

        gyro.reset();
        leftBack.setSelectedSensorPosition(0,0,10);
        rightFront.setSelectedSensorPosition(0,0,10);

    }

    //Drives talons at left/right speed
    public void drive(double left, double right) {

        leftBack.set(ControlMode.PercentOutput, left);
        rightFront.set(ControlMode.PercentOutput, right);

    }
}