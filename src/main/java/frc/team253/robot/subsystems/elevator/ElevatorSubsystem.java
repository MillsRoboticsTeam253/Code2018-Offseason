package frc.team253.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.subsystems.drivetrain.DrivetrainConstants;
import frc.team253.robot.utilities.ElevatorEncoderSource;
import frc.team253.robot.subsystems.misc.Miscellaneous;
import frc.team253.robot.subsystems.elevator.commands.elevate;

import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorB;

public class ElevatorSubsystem extends Subsystem {
    private static ElevatorSubsystem instance = null;

    private final Spark ElevatorA = new Spark(ElevatorConstants.elevatorSparkA);
    private final Spark ElevatorB = new Spark(ElevatorConstants.elevatorSparkB);

    private final SpeedControllerGroup ElevatorSparks = new SpeedControllerGroup(ElevatorA, ElevatorB);
    private ElevatorEncoderSource ElevatorEncoderSource = new ElevatorEncoderSource();
    public PIDController elevatorControl = new PIDController(0,0,0,0, ElevatorEncoderSource, ElevatorSparks);

    private ElevatorSubsystem() {
        SmartDashboard.putNumber("Spark Output",ElevatorSparks.get());
        /*elevatorControl.setSetpoint(0);
        elevatorControl.setPercentTolerance(2);*/
    }
    public static ElevatorSubsystem getInstance()
    {
        if (instance == null)
            instance = new ElevatorSubsystem();

        return instance;
    }

    public void elevate(double speed){
        ElevatorSparks.set(speed);
    }

    public void resetEncoder(){
        leftMotorB.setSelectedSensorPosition(0,0,10);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new elevate());
    }

}
