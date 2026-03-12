package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.driveConstants;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


@TeleOp(name="KLocalizer",group = "System Check")
public class DriveCheck extends OpMode {
    private Mecanum drive = null;

    public static Follower follower;
    public Pose startingPose;    //TODO DEFINE START POSE HER

//    @IgnoreConfigurable
//    static TelemetryManager telemetryM;
//

    @Override
    public void init() {
        drive = new Mecanum(hardwareMap, driveConstants);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.startTeleopDrive(true);
        follower.update();

    }
    @Override
    public void init_loop() {
//        telemetryM.debug("This will print your robot's position to telemetry while "
//                + "allowing robot control through a basic mecanum drive on gamepad 1.");
//        telemetryM.update(telemetry);
        follower.update();
//        drawOnlyCurrent();
    }

    @Override
    public void loop() {
        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

//        telemetryM.debug("x:" + follower.getPose().getX());
//        telemetryM.debug("y:" + follower.getPose().getY());
//        telemetryM.debug("heading:" + follower.getPose().getHeading());
//        telemetryM.debug("total heading:" + follower.getTotalHeading());
//        driveCurrent(telemetryM,drive);
//        telemetryM.update(telemetry);
          driveCurrent(telemetry,drive);
        telemetry.update();

//        draw();
    }

    public void driveCurrent(TelemetryManager t,Mecanum drive){
        t.debug("leftFront", drive.getMotors().get(0).getCurrent(CurrentUnit.AMPS));
        t.debug("leftRear",   drive.getMotors().get(1).getCurrent(CurrentUnit.AMPS));
        t.debug("rightFront", drive.getMotors().get(2).getCurrent(CurrentUnit.AMPS));
        t.debug("rightRear",  drive.getMotors().get(3).getCurrent(CurrentUnit.AMPS));
    }
 public void driveCurrent(Telemetry t, Mecanum drive){
        t.addData("leftFront", drive.getMotors().get(0).getCurrent(CurrentUnit.AMPS));
        t.addData("leftRear",   drive.getMotors().get(1).getCurrent(CurrentUnit.AMPS));
        t.addData("rightFront", drive.getMotors().get(2).getCurrent(CurrentUnit.AMPS));
        t.addData("rightRear",  drive.getMotors().get(3).getCurrent(CurrentUnit.AMPS));
    }

    public static void resetYaw() {
        follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(), 0));
    }




}
