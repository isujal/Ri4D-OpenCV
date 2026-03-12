package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class KPathCommandBlue extends CommandBase {

    private final Follower follower;
    private final PathChain path;

    private final boolean resetPose;
    private final boolean holdAtEnd;
    private final Double maxPower;

    private boolean started = false;

    /* ===================== CONSTRUCTORS ===================== */

    // Basic (default)
    public KPathCommandBlue(Follower follower, PathChain path) {
        this(follower, path, 1.0, false, true);
    }



    // With velocity cap
    public KPathCommandBlue(Follower follower, PathChain path, double maxPower) {
        this(follower, path, maxPower, false, false);
    }


    // Full control
    public KPathCommandBlue(Follower follower, PathChain path, Double maxPower, boolean resetPose, boolean holdAtEnd) {
        this.follower = follower;
        this.path = path;
        this.maxPower = maxPower;
        this.resetPose = resetPose;
        this.holdAtEnd = holdAtEnd;

        addRequirements(); // drivetrain owned by Pedro
    }


    /* ===================== LIFECYCLE ===================== */

    @Override
    public void initialize() {
        follower.followPath(path,1.0, true);
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

        if (!holdAtEnd && !interrupted ) {

            // DO NOT breakFollowing()
            follower.setMaxPower(3);

            // Lock current pose as target
            PedroHold.holdBlue(follower);
        }
        else if (holdAtEnd && !interrupted) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.3);

            // Lock current pose as target
            follower.getPose();
//            follower.getPose().getX();
//            follower.getPose().getY();
//            follower.getPose().getHeading();
        }
        else {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
        }
    }

    public static class PedroHold {
        public static void holdBlue(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(
                                    new BezierLine(p, new Pose(9, 58))
                            )
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }
    }
}
