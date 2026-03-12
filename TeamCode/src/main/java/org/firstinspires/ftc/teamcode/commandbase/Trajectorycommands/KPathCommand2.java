package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

public class KPathCommand2 extends CommandBase {

    public static  boolean finished = false;
    private final Follower follower;
    private final PathChain path;

    private final boolean resetPose;
    private final boolean holdAtEnd;
    private final Double maxPower;

    private final String Side;

    private final double timeoutSeconds;
    private final ElapsedTime timer = new ElapsedTime();

    private boolean started = false;

    /* ===================== CONSTRUCTORS ===================== */

    // Basic (default)
    public KPathCommand2(Follower follower, PathChain path) {
        this(follower, path, 1.0, false, true, "null", Double.POSITIVE_INFINITY);
    }

    //With Timer
    public KPathCommand2(Follower follower, PathChain path, double timeoutSeconds) {
        this(follower, path, 1.0, true,true, "null", timeoutSeconds);
    }



    // With velocity cap
    public KPathCommand2(Follower follower, PathChain path, double maxPower,  String side) {
        this(follower, path, maxPower, false, false, side, Double.POSITIVE_INFINITY);
    }


    // Full control
    public KPathCommand2(Follower follower, PathChain path, Double maxPower, boolean resetPose, boolean holdAtEnde, String side, double timeoutSeconds) {
        this.follower = follower;
        this.path = path;
        this.maxPower = maxPower;
        this.resetPose = resetPose;
        this.holdAtEnd = holdAtEnde;
        this.Side = side;
        this.timeoutSeconds = timeoutSeconds;

        addRequirements(); // drivetrain owned by Pedro
    }


    /* ===================== LIFECYCLE ===================== */

    @Override
    public void initialize() {
        timer.reset();
        follower.followPath(path,maxPower, holdAtEnd);
        started = true;
    }

//    @Override
//    public void execute() {
//        // Pedro controls motion internally via follower.update()
//    }

    @Override
    public boolean isFinished() {
        finished = started &&
                (!follower.isBusy() || timer.seconds() >= timeoutSeconds);
        return finished /*started &&
                (!follower.isBusy() || timer.seconds() >= timeoutSeconds)*/;    }

    @Override
    public void end(boolean interrupted) {

        boolean timedOut = timer.seconds() >= timeoutSeconds;

        if (timedOut) {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
            return;
        }


        if (holdAtEnd && !interrupted && Side.equalsIgnoreCase("null")) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.75);

            // Lock current pose as target
            follower.getPose();
        }

        else if (holdAtEnd && !interrupted) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.75);

            // Lock current pose as target
            follower.getPose();
        }

        else {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
        }
    }


}
