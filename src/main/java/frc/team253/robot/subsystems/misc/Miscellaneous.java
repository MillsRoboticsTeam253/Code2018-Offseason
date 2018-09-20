package frc.team253.robot.subsystems.misc;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;

import static frc.team253.robot.subsystems.misc.MiscConstants.*;

public class Miscellaneous {

    public static Compressor compressorA;

    public static AHRS navX;

    public static void init() {

        navX = new AHRS(SPI.Port.kMXP);

        compressorA = new Compressor(compressorNumber);

    }

    public double getAngle(){
        return navX.getAngle();
    }
}
