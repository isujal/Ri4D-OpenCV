package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.hardware.Globals.ptoDisEngaged;
import static org.firstinspires.ftc.teamcode.hardware.Globals.ptoEngaged;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class endgamesubsystem {

    public static RobotHardware robot;

    public endgamesubsystem(RobotHardware robot){
        this.robot = robot;
    }



    public enum PTO_STATES {
        ENGAGED,
        DISENGAGED


    }





    public PTO_STATES ptoStates =PTO_STATES.DISENGAGED;

    public void updateState(PTO_STATES currentState){
        this.ptoStates = currentState;
        double current = ptoDisEngaged;
        switch (currentState){
            case ENGAGED:
                current = ptoEngaged;
                break;
            case DISENGAGED:
                current = ptoDisEngaged;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
//        robot.rackNpinion.setPosition(current);
    }



}

