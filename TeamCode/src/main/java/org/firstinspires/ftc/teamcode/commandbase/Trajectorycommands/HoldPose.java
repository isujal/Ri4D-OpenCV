package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.Path;

public class HoldPose extends CommandBase {

    private final Follower follower;
    private final Path targetPath;
    private final boolean holdAtEnd;

    public HoldPose(Follower follower, Path path) {
        this.follower = follower;
        this.targetPath = path;
        this.holdAtEnd = true;
        addRequirements();
    }

    @Override
    public void initialize() {
        follower.followPath(targetPath, true);
    }

    @Override
    public void execute() {
        // follower.update() is called in your main loop
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        if (holdAtEnd && !interrupted) {

            // DO NOT breakFollowing()
            follower.setMaxPower(0.6);

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

//        follower.breakFollowing();
//        follower.startTeleOpDrive(true);
//        Global.autoDrive = false;
    }

}
