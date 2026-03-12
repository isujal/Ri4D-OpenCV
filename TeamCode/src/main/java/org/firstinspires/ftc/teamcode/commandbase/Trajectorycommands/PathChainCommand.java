package org.firstinspires.ftc.teamcode.commandbase.Trajectorycommands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.Subsystem;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import java.util.Collections;
import java.util.Set;

public class PathChainCommand implements Command {

    private final Follower follower;
    private final PathChain pathChain;
    private final boolean reversed;
    private boolean started = false;

    public PathChainCommand(Follower follower, PathChain pathChain, boolean reversed) {
        this.follower = follower;
        this.pathChain = pathChain;
        this.reversed = reversed;
    }

    public PathChainCommand(Follower follower, PathChain pathChain) {
        this(follower, pathChain, false);
    }

    @Override
    public void initialize() {
        follower.followPath(pathChain, reversed);
        started = true;
    }

    @Override
    public void execute() {
        follower.update();
    }

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
        // Optional: stop drivetrain or hold position
    }

//    public Pose lastPoint() {
        // Gets the last waypoint pose from the PathChain
//        return pathChain.get(pathChain.size() - 1).getLastControlPoint().toPose();
//    }
}
