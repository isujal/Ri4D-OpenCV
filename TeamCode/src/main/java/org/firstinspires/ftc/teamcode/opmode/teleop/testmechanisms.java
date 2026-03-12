package org.firstinspires.ftc.teamcode.opmode.teleop;


import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.hardangle;
import static org.firstinspires.ftc.teamcode.hardware.RobotHardware.rackNpinion;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;


@TeleOp( name = "test mechanism", group = "test")
@Config
public class testmechanisms extends LinearOpMode {

    public static double total = 260;
    public static double P = 0;
    public static double I = 0;
    public static double D = 0;
    public static double F = 0;
    public static double turretPose = 0.5;

    public static int velocity = 500;

    public static int targetVelocity = 500;
    public static double intakePower = 0.5;

    RobotHardware robot = RobotHardware.getInstance();
    Pose2d startPose=new Pose2d(0,0,0);

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        robot.init(hardwareMap,startPose);



        while (opModeInInit()){
            robot.deployer.setPosition(0.5);
//            robot.slider.setPosition(0.5);
//            robot.platform.setPosition(0.5);
            robot.hood.setPosition(0.5);
            robot.turret.setPosition(0.84375);
//            robot.rackNpinion.setPosition(0);
        }


        waitForStart();
        while (opModeIsActive()){
            rackNpinion.setPosition(1);

            robot.shootRight.setVelocityPIDFCoefficients(P,I,D,F);
            robot.shootLeft.setVelocityPIDFCoefficients(P,I,D,F);


            /// intake
            if(gamepad2.a){
                robot.frontIn.setPower(intakePower);
                robot.rearIn.setPower(intakePower);
            }
            if(gamepad2.b){
                robot.frontIn.setPower(-intakePower);
                robot.rearIn.setPower(-intakePower);
            }


            if(gamepad2.dpad_up){

                robot.turret.setPosition(hardangle(turretPose));
            }

            telemetry.addData("maprd",(turretPose));

            /// shooter
            robot.shootLeft.setVelocity(targetVelocity);
            robot.shootRight.setVelocity(targetVelocity);

            if(gamepad2.left_bumper){
                targetVelocity = velocity;
            }
            if(gamepad2.left_bumper){
                targetVelocity = 0;
            }


            /// deployer
            if(gamepad1.dpad_up) {
                robot.deployer.setPosition(robot.deployer.getPosition()+ 0.001);
            }
            if(gamepad1.dpad_down) {
                robot.deployer.setPosition(robot.deployer.getPosition()- 0.001);
            }


            /// slider
            if(gamepad1.dpad_left) {
                robot.slider.setPosition(robot.slider.getPosition()+ 0.001);
            }

            if(gamepad1.dpad_right) {
                robot.slider.setPosition(robot.slider.getPosition()- 0.001);
            }

            /// platform
            if(gamepad1.a) {
                robot.gateOpener.setPosition(robot.gateOpener.getPosition()+ 0.001);
            }

            if(gamepad1.b) {
                robot.gateOpener.setPosition(robot.gateOpener.getPosition()- 0.001);
            }

            /// hood
            if(gamepad1.left_bumper) {
                robot.hood.setPosition(robot.hood.getPosition()+ 0.001);
            }

            if(gamepad1.right_bumper) {
                robot.hood.setPosition(robot.hood.getPosition()- 0.001);
            }

            /// turret
            if(gamepad1.left_trigger > 0.1) {
                robot.turret.setPosition(robot.turret.getPosition()+ 0.001);
            }

            if(gamepad1.right_trigger > 0.1) {
                robot.turret.setPosition(robot.turret.getPosition()- 0.001);
            }


            /// rack n pinion
            if(gamepad1.back ) {
                rackNpinion.setPosition(rackNpinion.getPosition()+0.01);
            }

            if(gamepad1.start ) {
                rackNpinion.setPosition(rackNpinion.getPosition()-0.01);
            }





            telemetry.addData("frontIntake current", robot.frontIn.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("rearIntake current", robot.rearIn.getCurrent(CurrentUnit.AMPS));

            telemetry.addData("left shooter current ", robot.shootLeft.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("right shooter current ", robot.shootRight.getCurrent(CurrentUnit.AMPS));


            telemetry.addData("right shooter velocity ", robot.shootRight.getVelocity());
            telemetry.addData("right shooter velocity ", robot.shootRight.getVelocity());


            telemetry.addData("slider", robot.slider.getPosition());
            telemetry.addData("turret", robot.turret.getPosition());
            telemetry.addData("hood", robot.hood.getPosition());
            telemetry.addData("deployer", robot.deployer.getPosition());
            telemetry.addData("rackNpinion", rackNpinion.getPosition());
            telemetry.addData("platform", robot.gateOpener.getPosition());
            telemetry.addData("target velocity", targetVelocity);

            telemetry.addLine();
            telemetry.addData("F1 : ", robot.F1.getState());
            telemetry.addData("F2 : ", robot.F2.getState());
            telemetry.addData("F3 : ", robot.F3.getState());
            telemetry.addData("F4 : ", robot.R1.getState());
            telemetry.addData("F5 : ", robot.R2.getState());
            telemetry.addData("F6 : ", robot.R3.getState());
            telemetry.addLine();
            telemetry.addData("left shoot velo", robot.shootLeft.getVelocity());
            telemetry.addData("right shoot velo", robot.shootRight.getVelocity());
            telemetry.addLine();



            telemetry.update();

        }

    }
//    public static double mapRestrictedRange(double angle) {
//
//
//        angle = angle + total/2;
//        angle = angle-3;
//        return 0.1 + (angle / total) * (0.9-0.1);
//    }
}
