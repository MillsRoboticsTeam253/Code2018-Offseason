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

        throttle = deadbandX(throttle, kJoystickDeadband);
        wheel = deadbandX(wheel, kJoystickDeadband);

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
                left = -wheel;
                right = wheel;
            } else { //curvature
                left =throttle+throttle*wheel;
                right =throttle-throttle*wheel;
            }

            left = exponentiate(left, 2);
            right = exponentiate(right, 2);

        }

        setBrakeMode();

        switch(DrivetrainSubsystem.shifter.get()){
            case kForward:
                left = deadbandY(left, kLinterceptHigh/12.0);
                right = deadbandY(right, kRinterceptHigh/12.0);
                break;
            case kReverse:
                left = deadbandY(left, kLinterceptLow/12.0);
                right = deadbandY(right, kRinterceptLow/12.0);
            case kOff:
                break;
        }

        drivetrain.drive(left, right);
    }

    public static double deadbandX(double input, double deadband){
        if(Math.abs(input) <= deadband){
            return 0;
        } else if(Math.abs(input)==1) {
            return input;
        } else {
            return (1/(1-deadband)*(input+Math.signum(-input)*deadband));
        }
    }

    public static double exponentiate(double input, double power){
        return Math.copySign(Math.pow(input, power),input);
    }

    public static double deadbandY(double input, double deadband){
        if(Math.abs(input)==0.0){
            return 0;
        } else if(Math.abs(input) ==1){
            return input;
        } else {
            return input*(1.0-deadband)+Math.signum(input)*deadband;
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
