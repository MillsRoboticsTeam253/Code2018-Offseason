package frc.team253.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team253.bobabots.utilities.RunCommand;
import frc.team253.bobabots.oi.XBPovButton;
import frc.team253.robot.subsystems.elevator.ElevatorSubsystem;

import static edu.wpi.first.wpilibj.GenericHID.Hand;
import static frc.team253.bobabots.oi.XBPovConstants.*;
import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.Robot.elevator;
import static frc.team253.robot.Robot.oi;
import static frc.team253.robot.subsystems.drivetrain.DrivetrainSubsystem.leftMotorA;

public class OI {

    public XboxController xboxcontroller;
    public JoystickButton ButtonA;
    public JoystickButton ButtonB;
    public JoystickButton ButtonX;
    public JoystickButton ButtonY;
    public JoystickButton ButtonRB;
    public JoystickButton ButtonLB;
    public JoystickButton ButtonRT;
    public JoystickButton ButtonLT;
    public Joystick elevatorStick;

    public JoystickButton dpadUP;
    public JoystickButton dpadUP_RIGHT;
    public JoystickButton dpadRIGHT;
    public JoystickButton dpadDOWN_RIGHT;
    public JoystickButton dpadDOWN;
    public JoystickButton dpadDOWN_LEFT;
    public JoystickButton dpadLEFT;
    public JoystickButton dpadUP_LEFT;
    public JoystickButton dpadNONE;


    public OI() {
        elevatorStick = new Joystick(2);
        xboxcontroller = new XboxController(1);
        ButtonA = new JoystickButton(xboxcontroller, 1);
        ButtonB = new JoystickButton(xboxcontroller, 2);
        ButtonX = new JoystickButton(xboxcontroller, 3);
        ButtonY = new JoystickButton(xboxcontroller, 4);
        ButtonRB = new JoystickButton(xboxcontroller, 6);
        ButtonLB = new JoystickButton(xboxcontroller, 5);
        ButtonRT = new JoystickButton(xboxcontroller, 7);
        ButtonLT = new JoystickButton(xboxcontroller, 8);

        dpadUP = new XBPovButton(xboxcontroller, UP);
        dpadUP_RIGHT = new XBPovButton(xboxcontroller,UP_RIGHT);
        dpadRIGHT = new XBPovButton(xboxcontroller,RIGHT);
        dpadDOWN_RIGHT = new XBPovButton(xboxcontroller,DOWN_RIGHT);
        dpadDOWN = new XBPovButton(xboxcontroller,DOWN);
        dpadDOWN_LEFT = new XBPovButton(xboxcontroller,DOWN_LEFT);
        dpadLEFT = new XBPovButton(xboxcontroller,LEFT);
        dpadUP_LEFT = new XBPovButton(xboxcontroller,UP_LEFT);
        dpadNONE = new XBPovButton(xboxcontroller,NONE);

        //gear shifty bois
        dpadLEFT.whenPressed(new RunCommand( () -> drivetrain.shiftGear() ));
        //Elevator setpoint handling
        dpadUP.whenPressed(new RunCommand( () -> elevator.setElevator(ElevatorSubsystem.ElevatorHeight.SCALE) ));
        dpadRIGHT.whenPressed(new RunCommand( () -> elevator.setElevator(ElevatorSubsystem.ElevatorHeight.SWITCH) ));
        dpadDOWN.whenPressed(new RunCommand( () -> elevator.setElevator(ElevatorSubsystem.ElevatorHeight.GROUND) ));
        dpadNONE.whenPressed(new RunCommand( () -> elevator.setElevator(leftMotorA.getSelectedSensorPosition(0))));
    }

    public double throttleValue() {
        return xboxcontroller.getY(Hand.kLeft);
    }

    public double turnValue() {
        return xboxcontroller.getX(Hand.kRight);
    }

    public double elevateValue(){
        return elevatorStick.getY();
    }
}
