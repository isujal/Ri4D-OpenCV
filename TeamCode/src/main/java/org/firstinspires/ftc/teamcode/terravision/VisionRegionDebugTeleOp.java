package org.firstinspires.ftc.teamcode.terravision;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.terravision.Vision;
import org.firstinspires.ftc.teamcode.commandbase.VisionRegionCommand;

@TeleOp(group = "A-Vision",name = "Vision Region Debug CB")
public class VisionRegionDebugTeleOp extends OpMode {

    Vision2 vision;
    VisionRegionCommand visionCommand;

    @Override
    public void init() {

        vision = new Vision2();
        vision.init(hardwareMap);

        visionCommand = new VisionRegionCommand(vision);

        CommandScheduler.getInstance().schedule(visionCommand);
    }

    @Override
    public void loop() {

        CommandScheduler.getInstance().run();
        CommandScheduler.getInstance().schedule(visionCommand);

        int region = visionCommand.getRegion();


        visionCommand.execute();
        vision.update();

//        telemetry.addLine("----- REGION AREA -----");
//        telemetry.addData("Left Area", leftArea);
//        telemetry.addData("Center Area", centerArea);
//        telemetry.addData("Right Area", rightArea);
//
//        telemetry.addLine("----- TARGET REGION -----");
//
//        if(region == 0) telemetry.addLine("LEFT");
//        if(region == 1) telemetry.addLine("CENTER");
//        if(region == 2) telemetry.addLine("RIGHT");

//        telemetry.update();

        telemetry.addLine("---- VISION DEBUG ----");

        if(region == 0){
            telemetry.addLine("TARGET REGION: LEFT");
        }
        else if(region == 1){
            telemetry.addLine("TARGET REGION: CENTER");
        }
        else if(region == 2){
            telemetry.addLine("TARGET REGION: RIGHT");
        }
        else{
            telemetry.addLine("NO REGION DETECTED");
        }

        telemetry.addData("Region Index", region);

        telemetry.update();
    }
}