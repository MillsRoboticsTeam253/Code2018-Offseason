package frc.team253.robot.subsystems.drivetrain.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem;

import static frc.team253.robot.Robot.*;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainConstants.*;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.setBrakeMode;

public class curvatureDrive extends Command {
    double kPAim = -0.1;
    double kPDistance = -0.1;
    double min_aim_command = 0.05;


    public curvatureDrive() {
        requires(drivetrain);
    }

    protected void execute() {

        double throttle = oi.throttleValue(),
                wheel = oi.turnValue();

        SmartDashboard.putNumber("xOffset", limelight.getxOffset());
        SmartDashboard.putNumber("yOffset", limelight.getyOffset());

        double right, left;


        if(oi.xboxcontroller.getBButton()) {
            double heading_error = -limelight.getxOffset();
            double distance_error = -limelight.getyOffset() / 1.5;
            double steering_adjust = 0.0;

            SmartDashboard.putNumber("Steering Adjust", steering_adjust);
            if (limelight.getxOffset() > 3.0) {
                steering_adjust = (kPAim * heading_error - min_aim_command) / 3;


            } else if (limelight.getxOffset() < 3.0) {
                steering_adjust = (kPAim * heading_error + min_aim_command) / 3;
            }
            double distance_adjust = kPDistance * distance_error;
            left = -((steering_adjust + distance_adjust) / 1.5);
            right = (steering_adjust - distance_adjust) / 1.5;

        } else { //curvature driving
            if (Math.abs(throttle) < kJoystickDeadband) {
                left = wheel;
                right = -wheel;
            } else { //curvature
                left =throttle+throttle*wheel;
                right =throttle-throttle*wheel;
            }

            //Squaring drive values to add sensitivity curve
            left = Math.copySign(Math.pow(left,2),left);
            right = Math.copySign(Math.pow(right,2),right);
        }

        setBrakeMode();

        //DRIVETRAIN CHARACTERIZATION NUMBER PROCESSING
        if (Math.abs(throttle) > kJoystickDeadband || Math.abs(wheel) > kJoystickDeadband || oi.xboxcontroller.getBButton()) {

            switch(DrivetrainSubsystem.shifter.get()){
                case kForward:
                    left = processDriveChar(left, kVmaxHigh, kLslopeHigh,kLinterceptHigh);
                    right = processDriveChar(right, kVmaxHigh, kRslopeHigh,kRinterceptHigh);
                    break;
                case kReverse:
                    left = processDriveChar(left, kVmaxLow, kLslopeLow,kLinterceptLow);
                    right = processDriveChar(right, kVmaxLow, kRslopeLow,kRinterceptLow);
                case kOff:
                    break;
            }
            drivetrain.drive(left, right);
        } else {
            drivetrain.drive(0, 0);
        }
    }

    public static double processDriveChar(double wantedSpeed, double Vmax, double slope, double intercept){

        return (((wantedSpeed*Vmax)
                / slope)
                + Math.copySign(intercept, wantedSpeed))
                / 12;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
