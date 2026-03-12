package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;


@Config
@TeleOp(name = "Flywheel PIDF Control")
public class FlywheelControl extends LinearOpMode {
    private final RobotHardware robot = RobotHardware.getInstance();

    private PIDFController pidf;

    // Gains - These MUST be tuned for your specific motor/flywheel mass
    // kP: Proportional - pushes harder the further away you are from target
    // kI: Integral - pushes harder over time if target isn't reached
    // kD: Derivative - dampens the movement (usually 0 for flywheels)
    // kF: Feedforward - the "base" power needed to stay at a certain speed
    public static double flykP = 0.002;
    public static double flykI = 0.0;
    public static double flykD = 0.0;
    public static double flykF = 0.000335; // Often calculated as 1 / MaxVelocity

    // Target velocity in encoder ticks per second
    public static double targetVelocity = 2100;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap,new Pose2d(0,0,0));
                telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

//        // Initialize motor using FTCLib's MotorEx (provides velocity sensing)
//        flywheelMotor1 = robot.shootLeft;
//        flywheelMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        flywheelMotor2 = robot.shootRight;
//        flywheelMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Initialize the PIDF controller
        pidf = new PIDFController(flykP, flykI, flykD, flykF);
        
        // Optional: Set a tolerance (e.g., 20 ticks/sec) 
        // to check if the flywheel is "ready"
        pidf.setTolerance(20);

        waitForStart();

        while (opModeIsActive()) {
            pidf = new PIDFController(flykP, flykI, flykD, flykF);

            // Get current velocity from encoder (Ticks per second)
            double currentVelocity = robot.shootLeft.getVelocity();

            // Calculate PIDF output
            // calculate(measuredValue, setpoint)
            double output = pidf.calculate(currentVelocity, targetVelocity);

            // Apply output to motor (scaled -1.0 to 1.0)
            robot.shootLeft.setPower(output);
            robot.shootRight.setPower(output);

            // Telemetry for tuning
            telemetry.addData("Target Velocity", targetVelocity);
            telemetry.addData("Current Velocity", currentVelocity);
            telemetry.addData("Motor Output", output);
            telemetry.addData("At Setpoint", pidf.atSetPoint());
            telemetry.update();
        }
    }
}