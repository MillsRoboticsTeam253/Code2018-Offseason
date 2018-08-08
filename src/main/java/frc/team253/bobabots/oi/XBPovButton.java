package frc.team253.bobabots.oi;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XBPovButton extends JoystickButton{
    private boolean toggle = true;

    private GenericHID joystickNumber;
    private int xboxPOV;

    public XBPovButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);

        joystickNumber = joystick;
        xboxPOV = buttonNumber;
    }

    public boolean get(){
        return joystickNumber.getPOV() == xboxPOV;
    }

    public boolean isPressed(){
        if(get() && toggle){
            toggle = false;
            return true;
        }else if(!get()){
            toggle = true;
        }
        return false;
    }
}
