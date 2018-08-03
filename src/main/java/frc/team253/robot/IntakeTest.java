package frc.team253.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;

public class IntakeTest extends IterativeRobot {

    public static XboxController xbox1;
    public static TalonSRX driveTrainLeftFront;
    public static TalonSRX driveTrainLeftBack;

    @Override
    public void teleopPeriodic() {
        while(isEnabled()){
            double speed =xbox1.getY(GenericHID.Hand.kLeft);
            if(Math.abs(speed)>0.1){
                driveTrainLeftBack.set(ControlMode.PercentOutput, speed);
            } else{
                driveTrainLeftBack.set(ControlMode.PercentOutput, 0);
            }
        }
    }
    
    @Override
    public void robotInit() {
        driveTrainLeftFront = new TalonSRX(3);
        driveTrainLeftBack = new TalonSRX(4);

        driveTrainLeftFront.follow(driveTrainLeftBack);

        driveTrainLeftBack.configContinuousCurrentLimit(30,10);
        driveTrainLeftFront.configContinuousCurrentLimit(30,10);

        driveTrainLeftBack.configPeakCurrentLimit(30,10);
        driveTrainLeftFront.configPeakCurrentLimit(30,10);



        xbox1 = new XboxController(1);
    }
}
