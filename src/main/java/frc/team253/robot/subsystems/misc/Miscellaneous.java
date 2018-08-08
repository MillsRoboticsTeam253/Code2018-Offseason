package frc.team253.robot.subsystems.misc;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;

import static frc.team253.robot.subsystems.misc.MiscConstants.*;

public class Miscellaneous {

    public static Spark Elevator1, Elevator2;

    public static Compressor compressorA;

    public static AHRS navX;

    public static void init() {

        Elevator1 = new Spark(4);
        Elevator2 = new Spark (5);

        navX = new AHRS(SPI.Port.kMXP);

        compressorA = new Compressor(compressorNumber);

    }
}
