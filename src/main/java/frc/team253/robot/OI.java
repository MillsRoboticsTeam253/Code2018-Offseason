package frc.team253.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team253.robot.pathing.pathFollow;
import frc.team253.robot.utils.RunCommand;
import frc.team253.robot.utils.XBPovButton;
import jaci.pathfinder.Waypoint;

import static edu.wpi.first.wpilibj.GenericHID.Hand;
import static frc.team253.robot.Robot.drivetrain;
import static frc.team253.robot.Robot.elevator;
import static frc.team253.robot.utils.Constants.POVConstants.*;

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

    public JoystickButton
            dpadUP;
    public JoystickButton dpadUP_RIGHT;
    public JoystickButton dpadRIGHT;
    public JoystickButton dpadDOWN_RIGHT;
    public JoystickButton dpadDOWN;
    public JoystickButton dpadDOWN_LEFT;
    public static JoystickButton dpadLEFT;
    public JoystickButton dpadUP_LEFT;



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


        dpadUP.whenPressed(new RunCommand(()->{
            drivetrain.resetEncoders();
        }));

        dpadLEFT.whileHeld(new pathFollow("Straight5ft"));

        Waypoint[] runPoints = new Waypoint[]{ //TEMPORARY
                new Waypoint(0, 0, 0),
                new Waypoint(1.524, 0, 0)
        };
        Waypoint[] runPoints2 = new Waypoint[]{ //TEMPORARY
                new Waypoint(0, 0, 0),
                new Waypoint(30, 0, 0)
        };

        dpadRIGHT.whileHeld(new pathFollow(runPoints));

        dpadDOWN.whileHeld(new pathFollow(runPoints2));

        ButtonX.whenPressed(new RunCommand(()->{
            drivetrain.leftBack.setNeutralMode(NeutralMode.Coast);
            drivetrain.leftFront.setNeutralMode(NeutralMode.Coast);
            drivetrain.rightBack.setNeutralMode(NeutralMode.Coast);
            drivetrain.rightFront.setNeutralMode(NeutralMode.Coast);
        }));

        ButtonY.whenPressed(new RunCommand(()->{
            drivetrain.leftBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.leftFront.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightBack.setNeutralMode(NeutralMode.Brake);
            drivetrain.rightFront.setNeutralMode(NeutralMode.Brake);
        }));
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
