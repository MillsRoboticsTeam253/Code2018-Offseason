package frc.team253.robot.subsystems.misc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import static frc.team253.robot.Robot.*;

public class vision extends Command {
    private int ledMode = 0;

    public vision(){
        requires(limelight);
    }

    protected void execute(){

        NetworkTableInstance.getDefault().getTable("Limelight").getEntry("stream").setNumber(0);


        boolean toggle = true;
        boolean isPressedTurnedOn = oi.xboxcontroller.getTriggerAxis(GenericHID.Hand.kRight) > 0.1;

        if (isPressedTurnedOn && toggle) {
            toggle = false;
            NetworkTableInstance.getDefault()
                    .getTable("Limelight")
                    .getEntry("ledMode")
                    .setNumber(incrementLEDMode());

        } else if (!isPressedTurnedOn) {
            toggle = true;
        }
    }

    protected int incrementLEDMode(){
        ledMode = ((ledMode+1)%3);

        return ledMode;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
