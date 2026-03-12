package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

public class KPathCommand3 extends CommandBase {

    /* ===================== FIELDS ===================== */

    private final Follower follower;
    private final PathChain path;

    private final boolean resetPose;
    private final boolean holdAtEnd;
    private final double maxPower;
    private final String side;

    private final double timeoutSeconds;
    private final ElapsedTime timer = new ElapsedTime();

    private boolean started = false;

    /* ===================== CONSTRUCTORS ===================== */

    // 1️⃣ Absolute simplest
    public KPathCommand3(Follower follower, PathChain path) {
        this(follower, path, 1.0, false, true, "null", Double.POSITIVE_INFINITY);
    }

    // 2️⃣ With timeout only
    public KPathCommand3(Follower follower, PathChain path, double timeoutSeconds) {
        this(follower, path, 1.0, true, true, "null", timeoutSeconds);
    }

    // 3️⃣ With side only
    public KPathCommand3(Follower follower, PathChain path, String side) {
        this(follower, path, 1.0, false, true, side, Double.POSITIVE_INFINITY);
    }

    // 4️⃣ With max power
//    public KPathCommand2(Follower follower, PathChain path, double maxPower) {
//        this(follower, path, maxPower, false, true, "null", Double.POSITIVE_INFINITY);
//    }

    // 5️⃣ maxPower + side
    public KPathCommand3(Follower follower, PathChain path, double maxPower, String side) {
        this(follower, path, maxPower, false, false, side, Double.POSITIVE_INFINITY);
    }

    // 6️⃣ maxPower + timeout
    public KPathCommand3(Follower follower, PathChain path, double maxPower, double timeoutSeconds) {
        this(follower, path, maxPower, false, true, "null", timeoutSeconds);
    }

    // 7️⃣ maxPower + side + timeout
    public KPathCommand3(
            Follower follower,
            PathChain path,
            double maxPower,
            String side,
            double timeoutSeconds
    ) {
        this(follower, path, maxPower, false, false, side, timeoutSeconds);
    }

    // 8️⃣ FULL CONTROL (expert)
    public KPathCommand3(
            Follower follower,
            PathChain path,
            double maxPower,
            boolean resetPose,
            boolean holdAtEnd,
            String side,
            double timeoutSeconds
    ) {
        this.follower = follower;
        this.path = path;
        this.maxPower = maxPower;
        this.resetPose = resetPose;
        this.holdAtEnd = holdAtEnd;
        this.side = side;
        this.timeoutSeconds = timeoutSeconds;

        addRequirements(); // Pedro owns drivetrain
    }

    /* ===================== LIFECYCLE ===================== */

    @Override
    public void initialize() {
        timer.reset();
        follower.setMaxPower(maxPower);
        follower.followPath(path, 1.0, true);
        started = true;
    }

    @Override
    public boolean isFinished() {
        return started &&
                (!follower.isBusy() || timer.seconds() >= timeoutSeconds);
    }

    @Override
    public void end(boolean interrupted) {

        boolean timedOut = timer.seconds() >= timeoutSeconds;

        if (timedOut) {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
            return;
        }

        if (!holdAtEnd && !interrupted && side.equalsIgnoreCase("RED")) {
            follower.setMaxPower(0.45);
            PedroHold.holdRed(follower);
        }
        else if (!holdAtEnd && !interrupted && side.equalsIgnoreCase("BLUE")) {
            follower.setMaxPower(0.45);
            PedroHold.holdBlue(follower);
        }

        else if (holdAtEnd && !interrupted) {
            follower.setMaxPower(0.35);
            follower.getPose(); // lock pose
        }
        else {
            follower.breakFollowing();
            follower.setMaxPower(1.0);
        }
    }

    /* ===================== HOLD HELPERS ===================== */

    public static class PedroHold {

        public static void holdRed(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(new BezierLine(p, new Pose(135, 62)))
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }

        public static void holdBlue(Follower follower) {
            Pose p = follower.getPose();
            follower.followPath(
                    follower.pathBuilder()
                            .addPath(new BezierLine(p, new Pose(17.5, 60)))
                            .setConstantHeadingInterpolation(p.getHeading())
                            .build(),
                    true
            );
        }
    }
}
