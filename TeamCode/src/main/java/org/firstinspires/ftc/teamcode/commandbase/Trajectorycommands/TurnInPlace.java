package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;

public class TurnInPlace extends CommandBase {

    private final Follower follower;
    private final double targetHeadingRad;
    private final boolean holdAtEnd;

    public TurnInPlace(Follower follower, double targetHeadingRad) {
        this.follower = follower;
        this.targetHeadingRad = targetHeadingRad;
        this.holdAtEnd = true;
        addRequirements();
    }

    @Override
    public void initialize() {
        follower.turnTo(Math.toRadians(targetHeadingRad));
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        if (holdAtEnd && !interrupted) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.75);

            // Lock current pose as target
//            follower.getPose();
//            follower.getPose().getX();
//            follower.getPose().getY();
            follower.getPose().getHeading();
        }

        else {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
        }

//        follower.breakFollowing();
//        follower.startTeleOpDrive(true);
//        Global.autoDrive = false;
    }
}
