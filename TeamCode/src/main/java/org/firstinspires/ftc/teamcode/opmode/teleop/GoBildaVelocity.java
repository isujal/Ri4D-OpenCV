package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "GoBILDA Motor Velocity", group = "Test")
public class GoBildaVelocity extends LinearOpMode {

    // CHANGE THIS to match your motor
    static final double TICKS_PER_REV = 28; // Example: GoBILDA 19.2:1

    DcMotorEx motor;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotorEx.class, "rfm");

//        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        motor.setPower(1); // Run motor at 50% power

        while (opModeIsActive()) {

            double ticksPerSecond = motor.getVelocity(); // encoder ticks/sec


            double rpmm = ticksPerSecond / 28 * 60;
            telemetry.addData("Ticks / Sec", ticksPerSecond);
            telemetry.addData("RPM", rpmm);
//            telemetry.addData("RPM rad", motor.getVelocity(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}
