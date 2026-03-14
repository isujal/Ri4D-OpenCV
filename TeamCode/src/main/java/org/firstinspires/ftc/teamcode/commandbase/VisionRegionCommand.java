package org.firstinspires.ftc.teamcode.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.terravision.Vision;
import org.firstinspires.ftc.teamcode.terravision.Vision2;

public class VisionRegionCommand extends CommandBase {

    Vision2 vision;
    int region = -1;
    private int updateCount = 0;
    private static final int MAX_UPDATES = 60;
    public VisionRegionCommand(Vision2 vision){
        this.vision = vision;
    }

    @Override
    public void initialize(){
        vision.update();
    }

    @Override
    public void execute(){
        vision.update();
        region = vision.getRegion();
        updateCount++;

    }
    @Override
    public boolean isFinished() {
        if (region != -1) return true;
        if (updateCount >= MAX_UPDATES) {
            region = 1; // default CENTER if vision never commits
            return true;
        }
        return false;
    }

    public int getRegion(){
        return region;
    }
}