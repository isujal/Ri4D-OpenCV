package org.firstinspires.ftc.teamcode.commandbase;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.driveConstants;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.ftc.drivetrains.Mecanum;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystem.VisionSubsystem;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class VisionAlignCommand extends CommandBase {

    private VisionSubsystem vision;
    private RobotHardware robot;
    private Mecanum drive = null;

    public VisionAlignCommand(VisionSubsystem vision,
                              RobotHardware robot) {

        this.vision = vision;
        this.robot = robot;

        addRequirements(vision);
    }

    @Override
    public void execute() {
        drive = new Mecanum(hardwareMap, driveConstants);

        switch (vision.getPosition()) {

            case LEFT:
                drive.getMotors().get(0).setPower(-0.3);
                drive.getMotors().get(1).setPower(0.3);
                drive.getMotors().get(2).setPower(-0.3);
                drive.getMotors().get(3).setPower(0.3);
                break;

            case CENTER:
                drive.getMotors().get(0).setPower(0.3);
                drive.getMotors().get(1).setPower(0.3);
                drive.getMotors().get(2).setPower(0.3);
                drive.getMotors().get(3).setPower(0.3);
                break;

            case RIGHT:

                drive.getMotors().get(0).setPower(0.3);
                drive.getMotors().get(1).setPower(-0.3);
                drive.getMotors().get(2).setPower(0.3);
                drive.getMotors().get(3).setPower(-0.3);
                break;

            case NONE:
                drive.getMotors().get(0).setPower(0);
                drive.getMotors().get(1).setPower(0);
                drive.getMotors().get(2).setPower(0);
                drive.getMotors().get(3).setPower(0);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return false; // runs while scheduled
    }
}
