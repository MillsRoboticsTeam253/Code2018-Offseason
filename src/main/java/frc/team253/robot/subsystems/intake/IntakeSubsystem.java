package frc.team253.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team253.robot.subsystems.intake.commands.intake;

import static frc.team253.robot.subsystems.intake.IntakeConstants.*;

public class IntakeSubsystem extends Subsystem {
    private static IntakeSubsystem instance = null;

    private static WPI_VictorSPX intakeMotorA = new WPI_VictorSPX(5);
    private static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(intakePCM, intakeA, intakeB);

    private static SpeedController[] intakeMotors = {intakeMotorA};

    private IntakeSubsystem() {

    }

    public static void clampIntake(){

        switch(intakeSolenoid.get()){
            case kForward:
                intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
                break;
            default:
                intakeSolenoid.set(DoubleSolenoid.Value.kForward);
                break;
        }

    }

    public static void runIntake(double speed) {
        for (SpeedController motors : intakeMotors){
            motors.set(speed);
        }
    }

    public static void clampIntake(DoubleSolenoid.Value intakeStatus){
        intakeSolenoid.set(intakeStatus);
    }





    public static IntakeSubsystem getInstance() {
        if (instance == null)
            instance = new IntakeSubsystem();

        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new intake(0));
    } //change this to the joystick axis

}
