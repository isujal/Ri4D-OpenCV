package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.hardware.Globals.deployFront;
import static org.firstinspires.ftc.teamcode.hardware.Globals.deployIdeal;
import static org.firstinspires.ftc.teamcode.hardware.Globals.deployRear;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontAutoPush;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontInPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontOFF;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontOutPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontPushPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontReverse;
import static org.firstinspires.ftc.teamcode.hardware.Globals.frontslow;
import static org.firstinspires.ftc.teamcode.hardware.Globals.gateClose;
import static org.firstinspires.ftc.teamcode.hardware.Globals.gateOpen;
import static org.firstinspires.ftc.teamcode.hardware.Globals.platformUp;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearAutoPush;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearInPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearOFF;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearOutPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearPushPower;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearReverse;
import static org.firstinspires.ftc.teamcode.hardware.Globals.rearslow;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class intaksubsystem {

    public static RobotHardware robot;

    public intaksubsystem(RobotHardware robot){
        this.robot = robot;
    }


    public enum FRONT_INTAKE_STATES {
        IN,
        OUT,
        PUSH,
        OFF,
        SLOW,
        REVERSE,
        A_PUSH,
    }
    public enum REAR_INTAKE_STATES {
        IN,
        OUT,
        PUSH,
        OFF,
        SLOW,
        REVERSE,
        A_PUSH,
    }
    public enum DEPLOYER_STATES {
        FRONT_INTAKE,
        REAR_INTAKE,
        IDEAL
    }


    public FRONT_INTAKE_STATES frontIntakeStates =FRONT_INTAKE_STATES.OFF;
    public REAR_INTAKE_STATES rearIntakeStates =REAR_INTAKE_STATES.OFF;
    public DEPLOYER_STATES deployerStates = DEPLOYER_STATES.IDEAL;

   public void updateState(FRONT_INTAKE_STATES currentState){
        this.frontIntakeStates = currentState;
        double current = frontOFF;
        switch (currentState){
            case IN:
                current = frontInPower;
                break;
            case OUT:
                current = frontOutPower;
                break;
            case PUSH:
                current = frontPushPower;
                break;
            case OFF:
                current = frontOFF;
                break;
            case SLOW:
                current = frontslow;
                break;
            case REVERSE:
                current = frontReverse;
                break;
            case A_PUSH:
                current = frontAutoPush;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
       frontIntakeControl(current);

    }

    public void updateState(REAR_INTAKE_STATES currentState){
        this.rearIntakeStates = currentState;
        double current = rearOFF;
        switch (currentState){
            case IN:
                current = rearInPower;
                break;
            case OUT:
                current = rearOutPower;
                break;
            case PUSH:
                current = rearPushPower;
                break;
            case OFF:
                current = rearOFF;
                break;
            case SLOW:
                current = rearslow;
                break;
            case REVERSE:
                current = rearReverse;
                break;
            case A_PUSH:
                current = rearAutoPush;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        rearIntakeControl(current);
    }

    public  void updateState(DEPLOYER_STATES currentState){
       this.deployerStates = currentState;
       double current = deployFront;

       switch (currentState){
           case FRONT_INTAKE:
               current = deployFront;
               break;
           case REAR_INTAKE:
               current = deployRear;
               break;
           case IDEAL:
               current = deployIdeal;
               break;
       }
       robot.deployer.setPosition(current);
    }



    public enum GATE_OPENER_STATES {
        Gate_OPEN,
        GAte_Close,

    }
    public GATE_OPENER_STATES platformStates =GATE_OPENER_STATES.Gate_OPEN;
    public void updateState(GATE_OPENER_STATES currentState){
        this.platformStates = currentState;
        double current = platformUp;
        switch (currentState){
            case Gate_OPEN:
                current = gateOpen;
                break;
            case GAte_Close:
                current = gateClose;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
        robot.gateOpener.setPosition(current);
    }







    /// front intake ctrl
    public void frontIntakeControl(double power){
        robot.frontIn.setPower(power);

    }
    /// /// front intake ctrl
    public void rearIntakeControl(double power){
        robot.rearIn.setPower(power);

    }


}

