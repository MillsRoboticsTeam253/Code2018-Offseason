package frc.team253.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team253.robot.utilities.ElevatorEncoderSource;
import frc.team253.robot.subsystems.misc.Miscellaneous;
import frc.team253.robot.subsystems.elevator.commands.elevate;

import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorB;

public class Elevator extends Subsystem {
    private static Elevator instance = null;

    private final Spark Elevator1 = Miscellaneous.Elevator1;
    private final Spark Elevator2 = Miscellaneous.Elevator2;

    private final SpeedControllerGroup ElevatorSparks = new SpeedControllerGroup(Elevator1,Elevator2);
    private ElevatorEncoderSource ElevatorEncoderSource = new ElevatorEncoderSource();
    public PIDController elevatorControl = new PIDController(0,0,0,0,ElevatorEncoderSource,ElevatorSparks);

    private Elevator() {
        SmartDashboard.putNumber("Spark Output",ElevatorSparks.get());
        elevatorControl.setSetpoint(0);
        elevatorControl.setPercentTolerance(2);
    }
    public static Elevator getInstance()
    {
        if (instance == null)
            instance = new Elevator();

        return instance;
    }

    public void elevate(double speed){
        ElevatorSparks.set(speed);
    }

    public void resetEncoders(){
        leftMotorB.setSelectedSensorPosition(0,0,10);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new elevate());
    }

}
