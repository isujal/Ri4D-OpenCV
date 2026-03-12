package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.hardware.Globals.A_farVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarAutoBlue;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarAutoRed;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_hoodFar;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_hoodNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.A_nearVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.Extreme_nearVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.aprilTagHood;
import static org.firstinspires.ftc.teamcode.hardware.Globals.aprilTagTurret;
import static org.firstinspires.ftc.teamcode.hardware.Globals.cameraTurret;
import static org.firstinspires.ftc.teamcode.hardware.Globals.farVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.farVelocityHumanPlayer;
import static org.firstinspires.ftc.teamcode.hardware.Globals.hoodExtremeNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.hoodFar;
import static org.firstinspires.ftc.teamcode.hardware.Globals.hoodNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.hoodNearCenter;
import static org.firstinspires.ftc.teamcode.hardware.Globals.idealVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.nearVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.nearVelocitycenter;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderIn;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderMid;
import static org.firstinspires.ftc.teamcode.hardware.Globals.sliderOut;
import static org.firstinspires.ftc.teamcode.hardware.Globals.targetVelocityWithCamera;
import static org.firstinspires.ftc.teamcode.hardware.Globals.targetflyVelocity;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarBlue_tele;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarBlue_tele_human;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarRed_tele;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretFarRed_tele_human;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretNear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretNearAutoBlue;
import static org.firstinspires.ftc.teamcode.hardware.Globals.turretNearAutoRed;
import static org.firstinspires.ftc.teamcode.hardware.Globals.zeroVelocity;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class shootersubsystem {

    public static RobotHardware robot;

    public shootersubsystem(RobotHardware robot){
        this.robot = robot;
    }



    public enum SHOOTER_STATES{
        FAR_TELE,
        A_FAR,
        FAR_TELE_HUMANP,
        NEAR,
        NEAR_Center,
        A_NEAR,
        EXTREME_NEAR,
        ZERO,
        IDEAL,
        DYNAMIC_CAMERA

    }
    public enum TURRET_STATES {
        AprilTag_ANGLE,
        FAR_RED_TELE,
        FAR_RED_TELE_HUMANP,
        FAR_BLUE_TELE,
        FAR_BLUE_TELE_HUMANP,
        A_FAR_RED,
        A_FAR_BLUE,
        NEAR,
        NEAR_Auto,
        NEAR_AutoRed,
        NEAR_AutoBlue,
        Camera_ANGLE

    }public enum HOOD_STATES {
        AprilTag_ANGLE,
        FAR,
        A_FAR,
        NEAR,
        NEAR_Center,
        A_NEAR,
        EXTREME_NEAR,


    }public enum SLIDER_STATES {
        IN,
        OUT,
        MID,

    }

    public int a= 10;


    public SHOOTER_STATES shooterStates = SHOOTER_STATES.ZERO;
    public TURRET_STATES turretStates = TURRET_STATES.FAR_RED_TELE;
    public HOOD_STATES hoodStates = HOOD_STATES.FAR;
    public SLIDER_STATES sliderStates = SLIDER_STATES.IN;


    public void updateState(SHOOTER_STATES currentState){
        this.shooterStates = currentState;
        int current;
        switch (currentState){
            case FAR_TELE:
                current = farVelocity;
                break;
            case A_FAR:
                current = A_farVelocity;
                break;
            case FAR_TELE_HUMANP:
                current = farVelocityHumanPlayer;
                break;
            case NEAR:
                current = nearVelocity;
                break;

            case NEAR_Center:
                current = nearVelocitycenter;
                break;
            case A_NEAR:
                current = A_nearVelocity;
                break;
            case EXTREME_NEAR:
                current = Extreme_nearVelocity;
                break;
            case ZERO:
                current = zeroVelocity;
                break;
            case IDEAL:
                current = idealVelocity;
                break;
            case DYNAMIC_CAMERA:
                current = targetVelocityWithCamera;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        shooterCTRL(current);
    }
    public void updateState(TURRET_STATES currentState){
        this.turretStates = currentState;
        double current = turretFarRed_tele;
        switch (currentState){
            case FAR_RED_TELE:
                current = turretFarRed_tele;
                break;
            case FAR_RED_TELE_HUMANP:
                current = turretFarRed_tele_human;
                break;
            case FAR_BLUE_TELE:
                current = turretFarBlue_tele;
                break;
            case FAR_BLUE_TELE_HUMANP:
                current = turretFarBlue_tele_human;
                break;
            case A_FAR_RED:
                current = turretFarAutoRed;
                break;
            case A_FAR_BLUE:
                current = turretFarAutoBlue;
                break;
            case NEAR:
                current = turretNear;
                break;
//            case NEAR_Auto:
//                current = turretNearAuto;
//                break;
            case NEAR_AutoRed:
                current = turretNearAutoRed;
                break;
            case NEAR_AutoBlue:
                current = turretNearAutoBlue;
                break;
            case AprilTag_ANGLE:
                current = aprilTagTurret;
                break;
            case Camera_ANGLE:
                current = cameraTurret;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        robot.turret.setPosition(robot.hardangle(current));
    }public void updateState(HOOD_STATES currentState){
        this.hoodStates = currentState;
        double current = hoodFar;
        switch (currentState){
            case FAR:
                current = hoodFar;
                break;
            case A_FAR:
                current = A_hoodFar;
                break;
            case NEAR:
                current = hoodNear;
                break;
            case NEAR_Center:
                current = hoodNearCenter;
                break;
            case A_NEAR:
                current = A_hoodNear;
                break;
            case EXTREME_NEAR:
                current = hoodExtremeNear;
                break;
            case AprilTag_ANGLE:
                current = aprilTagHood;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        robot.hood.setPosition(current);
    }public void updateState(SLIDER_STATES currentState){
        this.sliderStates = currentState;
        double current = sliderIn;
        switch (currentState){
            case IN:
                current = sliderIn;
                break;
            case OUT:
                current = sliderOut;
                break;
            case MID:
                current = sliderMid;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        robot.slider.setPosition(current);
    }









    /// shooter control
    public static void shooterCTRL(int velocity){
        // set velocity for both motors in shooters
//        robot.shootLeft.setVelocity(velocity);
//        robot.shootRight.setVelocity(velocity);

        targetflyVelocity = velocity;

    }

}

