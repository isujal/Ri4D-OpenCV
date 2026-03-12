package org.firstinspires.ftc.teamcode.hardware;


import static org.firstinspires.ftc.teamcode.hardware.Globals.GLOBAL_TURRET_OFFSET;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class RobotHardware {


    public static  double prevHeading ;
    public static  double prevTime ;
    public static  double bearingVal ;
    public static  double SERVO_MIN = 0.15 ;
    public static  double SERVO_MAX = 0.8 ;
    public static  boolean turretFlag = false ;




    //Camera
    public static AprilTagProcessor aprilTag;
    public static VisionPortal visionPortal;
//    private final Position cameraPosition = new Position(
//            DistanceUnit.INCH, 0, 0, 0, 0
//    );

    private final YawPitchRollAngles cameraOrientation =
            new YawPitchRollAngles(AngleUnit.DEGREES, 0, -90, 0, 0);






    //TODO ENCODER PODS

    private final Object imuLock = new Object();


    /// mechanisms
    /// intake
    public static DcMotorEx frontIn;
    public static DcMotorEx rearIn;
    public static ServoImplEx deployer;

    /// shooter
    public static DcMotorEx shootLeft;
    public static DcMotorEx shootRight;
    public static ServoImplEx turret;
    public static ServoImplEx hood;
    public static ServoImplEx slider;

    /// end game
//    public static CRServo rackNpinion;
    public static Servo rackNpinion;
    public static ServoImplEx gateOpener;



    /// Digital beam breakers
    public static DigitalChannel F1; // front 1
    public static DigitalChannel F2; // front 2
    public static DigitalChannel F3; // front 3

    public static DigitalChannel R1; // rear 1
    public static DigitalChannel R2; // rear 2
    public static DigitalChannel R3; // rear 3







    //TODO IMU TO BE USE





//    public OpenCvCamera backCamera;



    private static RobotHardware instance = null;  // ref variable to use robot hardware

    public boolean enabled;                          //boolean to return instance if robot is enabled.

    private HardwareMap hardwareMap;

    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }




    public void init(HardwareMap hardwareMap, Pose2d initialPose) {
        this.hardwareMap = hardwareMap;



//        initAprilTag(hardwareMap);
//        turretCoefficients = new PedroPIDFCoefficients(kp_turret, ki_turret, kd_turret, kf_turret);
//        turretController = new PedroPIDFController(turretCoefficients);
//        turretCoefficients.setCoefficients(kp_turret, ki_turret, kd_turret, kf_turret);
//        turretController.setCoefficients(turretCoefficients);






        /// mechanisms
        /// intake
        frontIn = hardwareMap.get(DcMotorEx.class, "frontIn");
        frontIn.setDirection(DcMotorEx.Direction.FORWARD);
        rearIn = hardwareMap.get(DcMotorEx.class, "rearIn");
        rearIn.setDirection(DcMotorEx.Direction.REVERSE);
        deployer = hardwareMap.get(ServoImplEx.class, "deployer");

        /// shooter
        shootLeft = hardwareMap.get(DcMotorEx.class, "shooterLeft");
//        shootLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        shootLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shootLeft.setDirection(DcMotorEx.Direction.REVERSE);

        shootRight = hardwareMap.get(DcMotorEx.class, "shooterRight");
//        shootRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        shootRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shootRight.setDirection(DcMotorEx.Direction.FORWARD);

        turret = hardwareMap.get(ServoImplEx.class, "turret");
        hood = hardwareMap.get(ServoImplEx.class, "hood");
        slider = hardwareMap.get(ServoImplEx.class, "slider");
        /// end game
        rackNpinion = hardwareMap.get(Servo.class, "rNp");
        gateOpener = hardwareMap.get(ServoImplEx.class, "platform");


        /// Digital chanel
        F1 = hardwareMap.get(DigitalChannel.class, "F1");
        F2 = hardwareMap.get(DigitalChannel.class, "F2");
        F3 = hardwareMap.get(DigitalChannel.class, "F3");


        R1 = hardwareMap.get(DigitalChannel.class, "R1");
        R2 = hardwareMap.get(DigitalChannel.class, "R2");
        R3 = hardwareMap.get(DigitalChannel.class, "R3");


//        rackUp = hardwareMap.get(DigitalChannel.class, "rackUp");
//        rackDown = hardwareMap.get(DigitalChannel.class, "rackDown");





    }







    public static double normalizeToZeroOne(double x, double minTurret, double maxTurret) {

        // Calculate the difference between the maximum and minimum values
        double range = maxTurret - minTurret;

        // Perform the normalization: (x - minX) / (maxX - minX)
        double scaledX = (x - minTurret) / range;

        // Ensure the result is strictly within [0, 1] due to potential floating-point errors,
        // although mathematically it should be.
        return Math.max(0.0, Math.min(1.0, scaledX));
    }


    public static double[] rotateAroundZ(double x, double y, double theta) {
        double cosT = Math.cos(theta);
        double sinT = Math.sin(theta);

        double newX = x * cosT - y * sinT;
        double newY = x * sinT + y * cosT;

        return new double[] { newX, newY };
    }


    public static double mapRestrictedRange(double angle) {


        angle = angle + 258/2;
        return 0.1 + (angle / 258.0) * (0.9-0.1);
    }

    public static double angle(double targetAngle) {


        double minServoPose = 0.05;
        double maxServoPose = 0.8;

        // Max turret angle
        double maxAngle = 232.91;

        // Clamp angle to valid range (optional but recommended)
        targetAngle = Math.max(0.0, Math.min(maxAngle, targetAngle));

        // Linear mapping
        return minServoPose
                + (targetAngle / maxAngle) * (maxServoPose - minServoPose);
    }



    public static double hardangle(double targetAngle) {


        targetAngle += GLOBAL_TURRET_OFFSET;
//        double minServoPose = 0.05;
//        double maxServoPose = 0.8;
//
//        // Max turret angle
//        double maxAngle = 232.91;
//
        double minServoPose = 0;
        double maxServoPose = 1;

        // Max turret angle
//        double maxAngle = 224.6;
        double maxAngle = 320;

        // Clamp angle to valid range (optional but recommended)
        targetAngle = Math.max(0.0, Math.min(320, targetAngle));


        double servo_value = minServoPose
                + (targetAngle / maxAngle) * (maxServoPose - minServoPose);

        if(servo_value < 0.18){
            servo_value = 0.18;
        }if(servo_value > 0.88){
            servo_value = 0.88;
        }

        // Linear mapping
        return servo_value;
    }




    /// from pranav
//    public static void initAprilTag(HardwareMap hardwareMap) {
//        // Create the AprilTag processor by using a builder.
//        aprilTag = new AprilTagProcessor.Builder()
////                .setCameraPose(cameraPosition, cameraOrientation)
//                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
//                .setLensIntrinsics(531.897, 531.897, 340.151,254.67)
//                .build();
//        // Create the WEBCAM vision portal by using a builder.
//        visionPortal = new VisionPortal.Builder()
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .addProcessor(aprilTag)
//                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
//                .build();
//    }


    public static double getHeadingRate(double angle) {
        double currentTime = System.nanoTime() * 1e-9;
        double heading = Math.toDegrees(angle);

        double headingRateOut = (heading - prevHeading) / (currentTime - prevTime);
        prevHeading = heading;
        prevTime = currentTime;
        return Math.toRadians(headingRateOut);
    }

    public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    /*public static double getTargetHeading(double servoPos, Localizer localizer){
        double servoMappedDegree = map(servoPos, 0.2075, 0.7999, -90, 90);
        double relativeHeading = Math.toDegrees(localizer.getPose().heading.toDouble()) + (servoMappedDegree);
        relativeHeading = Math.toRadians(relativeHeading) + (Math.toRadians(bearingVal)) - Math.toRadians(MAX_X_OFFSET);
        return relativeHeading;
    }*/




/*    public static void TurretPIDFManual(Telemetry telemetry,Localizer localizer, double target, boolean turretTrack){

        turretController.setTargetPosition(getTargetHeading(target, localizer));
        turretController.updatePosition(localizer.getPose().heading.toDouble());
        turretController.updateFeedForwardInput(getHeadingRate(localizer.getPose().heading.toDouble()));
        double corr = turretController.run();
        double turretFinal = Range.clip(turret.getPosition() + corr, SERVO_MIN, SERVO_MAX);
        if(turretFinal < SERVO_MIN){
            turretFinal = SERVO_MIN;
        }
        else if(turretFinal > SERVO_MAX){
            turretFinal = SERVO_MAX;
        }

        if((turretFinal > 0.15 && turretFinal < 0.8) && turretTrack ) {turret.setPosition(turretFinal);}

    }*/

/*

    public static void telemetryAprilTag(Telemetry telemetry, int tagId) {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        tagSize = currentDetections.size();

        if(currentDetections.size() > 0){
            turretFlag = true;
        }
        else{
            turretFlag = false;
        }
        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {

            if (detection.metadata != null && detection.id == tagId ) {

                bearingVal = -detection.ftcPose.bearing;
                range = detection.ftcPose.range;
                telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
//                telemetry.addData("distance from tag ", detection.ftcPose.range * Math.cos(Math.toRadians(Math.abs(detection.ftcPose.pitch))));
//                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
//                telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
//        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
//        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
//        telemetry.addLine("RBE = Range, Bearing & Elevation");


    }



    public static double[] getShooterSettingsNear(double currentRange,
                                                  double rangeNear, double rangeFar,
                                                  double velNear, double velFar,
                                                  double hoodNear, double hoodFar) {

        double clampedRange = Math.max(rangeNear, Math.min(rangeFar, (int)currentRange));
        double t = (clampedRange - rangeNear) / (rangeFar - rangeNear);

        double updatedVelocity = velNear +    ((Math.pow(t,dyna_exp_power_velo))* (velFar - velNear));
//        double updatedHood = hoodNear + t * (hoodFar - hoodNear);
        double updatedHood = hoodNear +    ((Math.pow(t,dyna_exp_power_hood) )* (hoodFar - hoodNear));

        return new double[]{updatedVelocity, updatedHood};
    }

    public static double[] getShooterSettingsFar(double currentRange,
                                                 double rangeNear, double rangeFar,
                                                 double velNear, double velFar,
                                                 double hoodNear, double hoodFar,
                                                 double turretNear, double turretFar) {

        double clampedRange = Math.max(rangeNear, Math.min(rangeFar, (int)currentRange));
        double t = (clampedRange - rangeNear) / (rangeFar - rangeNear);

        double updatedVelocity = velNear +    ((Math.pow(t,dyna_Long_exp_power_velo))* (velFar - velNear));
//        double updatedHood = hoodNear + t * (hoodFar - hoodNear);
        double updatedHood = hoodNear +    ((Math.pow(t,dyna_Long_exp_power_hood) )* (hoodFar - hoodNear));
        double updatedTurret = turretNear +    ((Math.pow(t,dyna_Long_exp_power_turret) )* (turretFar - turretNear));

        return new double[]{updatedVelocity, updatedHood, updatedTurret};
    }
*/


    public static double dynamicHood(double currentRange,
                                     double rangeNear, double rangeFar,
                                     double hoodNear, double hoodFar) {

        // 1. Constrain the range to stay within your known data points
        double clampedRange = Math.max(rangeNear, Math.min(rangeFar, currentRange));

        // 2. Calculate the interpolation factor (t)
        // 0.0 = at rangeNear, 1.0 = at rangeFar
        double t = (clampedRange - rangeNear) / (rangeFar - rangeNear);

        // 3. Exponentially interpolate the Hood angle
        double updatedHood = hoodNear+    ((t)* (hoodFar - hoodNear));

        return updatedHood;
    }



    public static void flywheelVelocity(PIDFController pidf, DcMotorEx leftM, DcMotorEx rightM, int CurrentVelo,int target, int tol){
        pidf.setTolerance(tol);
        double currentVelocity = CurrentVelo;
        double output = pidf.calculate(currentVelocity, target);
        leftM.setPower(output);
        rightM.setPower(output);
    }



}