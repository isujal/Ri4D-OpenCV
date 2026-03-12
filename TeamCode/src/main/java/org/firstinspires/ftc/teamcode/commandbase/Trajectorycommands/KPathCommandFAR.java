package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.Subsystem;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

import java.util.Collections;
import java.util.Set;

public class KPathCommandFAR extends CommandBase {

    private final Follower follower;
    private final PathChain path;
    private final boolean Hold;
    private boolean started = false;

    public KPathCommandFAR(Follower follower, PathChain path, boolean holdEnd) {
        this.follower = follower;
        this.path = path;
        this.Hold = holdEnd;
    }

    public KPathCommandFAR(Follower follower, PathChain path) {
        this(follower, path,true);
    }

    @Override
    public void initialize() {
        follower.followPath(path, true);
         follower.followPath(path);
        started = true;
    }

//    @Override
//    public void execute() {
//        follower.update();
//    }

    @Override
    public boolean isFinished() {
        return started && !follower.isBusy();
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return Collections.emptySet();
    }

    @Override
    public void end(boolean interrupted) {
        if (Hold) {
            follower.followPath(path,Hold);
//            follower.holdPoint(follower.getPose());
        } else {
            follower.breakFollowing();
        }

//        follower.breakFollowing();   // HARD UP
        Pose p = follower.getPose();
        follower.setPose(new Pose(p.getX(), p.getY(), p.getHeading()));
    }


    public Pose lastPoint(Path path){
        Pose p = new Pose(1,3);
        return p;
    }
}
