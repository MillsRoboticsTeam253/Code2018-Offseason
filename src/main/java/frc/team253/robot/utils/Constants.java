package frc.team253.robot.utils;

public class Constants {

    public class POVConstants{
        public static final int
                NONE = -1,
                UP = 0,
                UP_RIGHT = 45,
                RIGHT = 90,
                DOWN_RIGHT = 115,
                DOWN = 180,
                DOWN_LEFT = 225,
                LEFT = 270,
                UP_LEFT = 315;
    }

    /**
     * which Talon on CANBus
     */
    public static final int kTalonID = 0;

    /**
     * How many sensor units per rotation. Using CTRE Magnetic Encoder.
     *
     * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
     */
    public static final double kSensorUnitsPerRotation = 4096;

    /**
     * Which PID slot to pull gains from. Starting 2018, you can choose from
     * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
     * configuration.
     */
    public static final int kSlotIdx = 0;

    /**
     * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
     * now we just want the primary one.
     */
    public static final int kPIDLoopIdx = 0;
    /**
     * set to zero to skip waiting for confirmation, set to nonzero to wait and
     * report to DS if action fails.
     */
    public static final int kTimeoutMs = 10;

    /**
     * Base trajectory period to add to each individual trajectory point's
     * unique duration. This can be set to any value within [0,255]ms.
     */
    public static final int kBaseTrajPeriodMs = 0;

    /**
     * Motor deadband, set to 4%.
     */
    public static final double kNeutralDeadband = 0.04;

    public static final double kWheelBaseWidthMeters = 0.59817;
    public static final double kWheelBaseWidthFeet = 1.9625;
    public static final double kWheelDiameterFeet = 0.5;
    public static final double kWheelDiameterMeters = 0.1524;
    public static final double kDriveTrainMaxVelMetersPSec = 4.18;
    public static final double kElevatorMaxVelMetersPSec = 0.9144;
    public static final double kElevatorSprocketDiameterMeters = 0.0381; //about 1.5 inches
    public static final double kGround = 0;
    public static final double kSwitchHeightFeet = 2.104;
    public static final double kScaleHeightFeet = 5.542;
    public static final double kPortalHeightFeet = 2.25; //bottom is 1 ft 8 in off the ground; portal is 1 ft 2 in high
    public static final double kSwitchHeightMeters = 0.41;
    public static final double kScaleHeightMeters = 1.1;
    public static final double kPortalHeightMeters = 0.6858;
    public static final double kTimeStepGlobal = 0.02;

    public static final double kHRobotVmax = 2686; //encoder units when 12V applied (take lower of left and right sides)
    public static final double kHVeloCharSlopeL = 257; //slope of LSRL of left drivetrain side (units: encoder units / volt)
    public static final double kHVeloCharInterceptL = 1.280; //minimum voltage required to overcome friction, x-int of LSRL of left side (volts)
    public static final double kHVeloCharSlopeR = 246; //slope of LSRL of right drivetrain side (units: encoder units / volt)
    public static final double kHVeloCharInterceptR = 1.081; //minimum voltage required to overcome friction, x-int of LSRL of right side (volts)

    public static final double kLRobotVmax = 2686;
    public static final double kLVeloCharSlopeL = 257;
    public static final double kLVeloCharInterceptL = 1.280;
    public static final double kLVeloCharSlopeR = 246;
    public static final double kLVeloCharInterceptR = 1.081;

    public static final double kDriveDeadband = 0.1;
}

