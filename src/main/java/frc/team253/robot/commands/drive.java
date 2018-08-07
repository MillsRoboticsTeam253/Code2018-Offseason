package frc.team253.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team253.robot.utils.Constants;
import frc.team253.robot.RobotMap;

import static frc.team253.robot.utils.Constants.*;
import static frc.team253.robot.Robot.*;

public class drive extends Command {
    double kPAim = -0.1;
    double kPDistance = -0.1;
    double min_aim_command = 0.05;


    public drive() {
        requires(drivetrain);
    }


    protected void execute() {

        //OI.dpadLEFT.whileHeld(new pathFollow("Straight5ft"));

        double throttle = oi.throttleValue(),
                wheel = oi.turnValue();

        SmartDashboard.putNumber("xOffset", limelight.getxOffset());
        SmartDashboard.putNumber("yOffset", limelight.getyOffset());

        //Vision when B button is held

        double right = 0;
        double left = 0;
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
            if (Math.abs(throttle) < kDriveDeadband) { //quickturning if throttle stick is not moved past 5%
                left = -wheel;
                right = wheel;
            } else {
                left = throttle+throttle*wheel;
                right = throttle-throttle*wheel;
            }

            left = Math.copySign(Math.pow(left,3),left);
            right = Math.copySign(Math.pow(right,3),right);
        }

        //DRIVETRAIN CHARACTERIZATION NUMBER PROCESSING
        if (Math.abs(throttle) > kDriveDeadband || Math.abs(wheel) > kDriveDeadband || oi.xboxcontroller.getBButton()) {

            switch(RobotMap.solenoid1.get()){
                case kForward:
                    left = processDriveChar(left, Constants.kHRobotVmax, kHVeloCharSlopeL,kHVeloCharInterceptL);
                    right = processDriveChar(right, Constants.kHRobotVmax, kHVeloCharSlopeR,kHVeloCharInterceptR);
                    break;
                case kReverse:
                    left = processDriveChar(left, kLRobotVmax, kLVeloCharSlopeL,kLVeloCharInterceptL);
                    right = processDriveChar(right, kLRobotVmax, kLVeloCharSlopeR,kLVeloCharInterceptR);
                case kOff:
                    break;
            }
            drivetrain.leftBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.leftFront.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightFront.setNeutralMode(NeutralMode.Brake);

            drivetrain.drive(left, right);
        } else {
            drivetrain.leftBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.leftFront.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightFront.setNeutralMode(NeutralMode.Brake);
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
