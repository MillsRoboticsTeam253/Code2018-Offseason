package frc.team253.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class limelight {
    NetworkTable table;
    double targetD;
    boolean hasTarget; //whether target is detected or tracked
    double xOffset; //horizontal offset from crosshair
    double yOffset;//vertical offset from crosshair
    double area; //target area(percentage)
    double skew;//rotation -90 to 0 degrees
    double LEDMode;
    double camMode;
    double pipeline;
    double snapshot;
    double angle;
    double Tdistance;

    public limelight(){
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public boolean gethasTarget() {
        targetD = table.getEntry("tv").getDouble(0);
        if(targetD == 0){
            hasTarget = false;
        } else if(targetD == 1){
            hasTarget = true;
        }
        return hasTarget;
    }

    public double getxOffset() {
        xOffset = table.getEntry("tx").getDouble(0);
        return xOffset;
    }

    public double getyOffset(){
        yOffset = table.getEntry("ty").getDouble(0);
        return yOffset;
    }

    public double getArea(){
        area = table.getEntry("ta").getDouble(0);
        return area;
    }

    public double getSkew(){
        skew = table.getEntry("ta").getDouble(0);
        return skew;
    }



    //Camera Mode Switching(0=VisionMode;1=DriverMode)
    public double getCamMode() {
        camMode = table.getEntry("camMode").getDouble(0);
        return camMode;
    }
    public void switchCamera() {
        if (getCamMode() == 0) {
            table.getEntry("camMode").setDouble(1);
            SmartDashboard.putString("Camera Mode", "Camera");
        } else if (getCamMode() == 1) {
            table.getEntry("camMode").setDouble(0);
            SmartDashboard.putString("Camera Mode", "Vision");
        }
    }

    //LED Switching(0=Off;1=On;2=Blink)
    public double getLEDMode() {
        LEDMode = table.getEntry("ledMode").getDouble(1);
        return LEDMode;
    }

    public void switchLED() {
        if (getLEDMode() == 0) {
            table.getEntry("ledMode").setDouble(1);
            SmartDashboard.putString("LED Mode", "Off");
        } else if (getLEDMode() == 1) {
            table.getEntry("ledMode").setDouble(0);
            SmartDashboard.putString("LED Mode", "On");
        } else if (getLEDMode() == 2) {
            table.getEntry("ledMode").setDouble(1);
            SmartDashboard.putString("LED Mode", "Off");
        }
    }




}
