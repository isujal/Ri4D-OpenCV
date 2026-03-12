package org.firstinspires.ftc.teamcode.commandbase;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.terravision.Vision;
import org.firstinspires.ftc.teamcode.terravision.Vision2;

public class VisionRegionCommand extends CommandBase {

    Vision2 vision;
    int region = -1;

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
    }

    @Override
    public boolean isFinished(){
        return region != -1;
    }

    public int getRegion(){
        return region;
    }
}