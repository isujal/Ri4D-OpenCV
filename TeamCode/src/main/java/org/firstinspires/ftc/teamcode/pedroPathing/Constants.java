
package org.firstinspires.ftc.teamcode.pedroPathing;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Config
public class Constants {

    public static double pody = -5.7525;
    public static double podx = 0.745;
    public static double Distance = 72;
    public static int Turn = 1;      // Turn * Pi

    public static double velocityX = 77;
    public static double velocityY = 50;//54.50; //65;//OG72.652;
    public static double decelerationF =-48.5;//-49.48 //-51.58;
    public static double decelerationL =-85.42;// -86.78//-89.25//-80.23;;

    public static FollowerConstants followerConstants = new FollowerConstants()
            .forwardZeroPowerAcceleration(decelerationF)
            .lateralZeroPowerAcceleration(decelerationL)
            .useSecondaryTranslationalPIDF(false)
            .useSecondaryHeadingPIDF(false)
            .useSecondaryDrivePIDF(true)

            .translationalPIDFCoefficients(new PIDFCoefficients(0.2, 0, 0.025, 0.0175))//0.145, 0, 0.02, 0.0175
            .headingPIDFCoefficients(new PIDFCoefficients(4.5, 0, 0.25, 0.005))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.010, 0, 0.00045, 0.6, 0.015))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.0015, 0, 0.0005, 0.6, 0.015))
            .centripetalScaling(0.00035)//0.00005

            .mass(10.5);// 11.52//10.8


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .xVelocity(velocityX)// TODO: x velcoity from forward velocity tuner
            .yVelocity(velocityY)// TODO: y velcoity from lateral velocity tuner
            .rightFrontMotorName("rfm")
            .rightRearMotorName("rbm")
            .leftRearMotorName("lbm")
            .leftFrontMotorName("lfm")
            .useBrakeModeInTeleOp(true)

            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)// TODO: CHECK MOTOR REVERSE UF NEEDED ENABLE THIS
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .useBrakeModeInTeleOp(true);


    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(pody)
            .strafePodX(podx)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)//F
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);//1.2 1.3

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .pinpointLocalizer(localizerConstants)
                .mecanumDrivetrain(driveConstants)
                .build();
    }

}







//package org.firstinspires.ftc.teamcode.pedroPathing;
//
//import com.pedropathing.control.FilteredPIDFCoefficients;
//import com.pedropathing.control.PIDFCoefficients;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.follower.FollowerConstants;
//import com.pedropathing.ftc.FollowerBuilder;
//import com.pedropathing.ftc.drivetrains.MecanumConstants;
//import com.pedropathing.ftc.localization.constants.PinpointConstants;
//import com.pedropathing.paths.PathConstraints;
//import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//public class Constants {
//    public static FollowerConstants followerConstants = new FollowerConstants()
//            .mass(10) //9.5
//
//            /*
//            .forwardZeroPowerAcceleration(33.694)
//            .lateralZeroPowerAcceleration(70.096)
//            .translationalPIDFCoefficients(new PIDFCoefficients(0.145,0,0.02,0.015))
//            .headingPIDFCoefficients(new PIDFCoefficients(1.3,0,0.02,0.04))
//            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.12,0,0,0,0.005))
//            * */
//
//
//            .forwardZeroPowerAcceleration(-41.06) //old value -34.43
//            .lateralZeroPowerAcceleration(-71.107) //new Value -68.63
//            .holdPointTranslationalScaling(0.45)
//            .holdPointHeadingScaling(0.35)
////            .translationalPIDFCoefficients(new PIDFCoefficients(0.15,0,0.02,0.0175)) // old p 0.134
//            .translationalPIDFCoefficients(new PIDFCoefficients(0.175,0,0.03,0.0175)) // old p 0.134
//            .headingPIDFCoefficients(new PIDFCoefficients(1.85,0,0.15,0.005))
//            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.0135,0,0.00035,0,0.002))
//            .centripetalScaling(0.0005)
//            ;
//
//    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1)
//            ;
//
//
//    public static MecanumConstants driveConstants = new MecanumConstants()
//            .maxPower(1)
//            .rightFrontMotorName("rfm")
//            .rightRearMotorName("rbm")
//            .leftRearMotorName("lbm")
//            .leftFrontMotorName("lfm")
//            .useBrakeModeInTeleOp(true)
//
//            .rightFrontMotorDirection(DcMotorEx.Direction.FORWARD) // 0
//            .rightRearMotorDirection(DcMotorEx.Direction.FORWARD) //1
//            .leftFrontMotorDirection(DcMotorEx.Direction.REVERSE) //2
//            .leftRearMotorDirection(DcMotorEx.Direction.REVERSE) //3
//
//            .xVelocity(97.193)
//            .yVelocity(80.149)
//
////            .xVelocity(104.835)
////            .yVelocity(88.69)
//
//            ;
//
//    public static PinpointConstants localizerConstants = new PinpointConstants()
//            .forwardPodY(-5.7525) // 50.01mm
//            .strafePodX(0.745) //22mm
//            .distanceUnit(DistanceUnit.INCH)
//            .hardwareMapName("pinpoint")
//            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
//            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
//            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
//            ;
//
//    public static Follower createFollower(HardwareMap hardwareMap) {
//        return new FollowerBuilder(followerConstants, hardwareMap)
//                .pathConstraints(pathConstraints)
//                .mecanumDrivetrain(driveConstants)
//                .pinpointLocalizer(localizerConstants)
//                .build();
//    }
//
//}

