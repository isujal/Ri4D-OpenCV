package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class KPathCommand extends CommandBase {

    private final Follower follower;
    private final PathChain path;

    private final boolean resetPose;
    private final boolean holdAtEnd;
    private final Double maxPower;

    private final String Side;

    private boolean started = false;

    /* ===================== CONSTRUCTORS ===================== */

    // Basic (default)
    public KPathCommand(Follower follower, PathChain path) {
        this(follower, path, 1.0, false, true,"black");
    }



    // With velocity cap
    public KPathCommand(Follower follower, PathChain path, double maxPower, boolean holdAtEnd,String side) {
        this(follower, path, maxPower, false, holdAtEnd,side);
    }


    // Full control
    public KPathCommand(Follower follower, PathChain path, Double maxPower, boolean resetPose, boolean holdAtEnd, String side) {
        this.follower = follower;
        this.path = path;
        this.maxPower = maxPower;
        this.resetPose = resetPose;
        this.holdAtEnd = holdAtEnd;
        this.Side = side;

        addRequirements(); // drivetrain owned by Pedro
    }


    /* ===================== LIFECYCLE ===================== */

    @Override
    public void initialize() {
        follower.followPath(path,maxPower, holdAtEnd);
        started = true;
    }

//    @Override
//    public void execute() {
//        // Pedro controls motion internally via follower.update()
//    }

    @Override
    public boolean isFinished() {
        return started && !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {

        if (!holdAtEnd && !interrupted && Side.equalsIgnoreCase("RED")) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.65);

            // Lock current pose as target
            PedroHold.holdRed(follower);
        }

        if (!holdAtEnd && !interrupted && Side.equalsIgnoreCase("holdRedStraight")) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.4);

            // Lock current pose as target
            PedroHold.holdRedStraight(follower);
        }

        else if (!holdAtEnd && !interrupted && Side.equalsIgnoreCase("BLUE")) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.4);

            // Lock current pose as target
            PedroHold.holdBlue(follower);
        }
        else if (holdAtEnd && !interrupted && Side.equalsIgnoreCase("black")) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.4);

            // Lock current pose as target
            follower.getPose();
        }
        else {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
        }
    }

    public static class PedroHold {

        public static void holdRed(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(
                                    new BezierLine(p, new Pose(133.5, 60))
                            )
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }

        public static void holdRedStraight(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(
                                    new BezierLine(p, new Pose(127.6, 62))
                            )
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }

        public static void holdBlue(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(
                                    new BezierLine(p, new Pose(8, 59))
                            )
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }
    }
}
